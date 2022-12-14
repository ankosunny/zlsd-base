/**
 * Copyright 2010 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhilingsd.base.zk.zkclient;

import com.zhilingsd.base.zk.zkclient.exception.*;
import com.zhilingsd.base.zk.zkclient.serialize.SerializableSerializer;
import com.zhilingsd.base.zk.zkclient.serialize.ZkSerializer;
import com.zhilingsd.base.zk.zkclient.util.ZkPathUtil;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.KeeperException.ConnectionLossException;
import org.apache.zookeeper.KeeperException.SessionExpiredException;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/**
 * Abstracts the interaction with zookeeper and allows permanent (not just one time) watches on nodes in ZooKeeper
 */
public class ZkClient implements Watcher {

    private final static Logger LOG = Logger.getLogger(ZkClient.class);

    protected final IZkConnection _connection;
    protected final long operationRetryTimeoutInMillis;
    private final Map<String, Set<IZkChildListener>> _childListener = new ConcurrentHashMap<String, Set<IZkChildListener>>();
    private final ConcurrentHashMap<String, Set<IZkDataListener>> _dataListener = new ConcurrentHashMap<String, Set<IZkDataListener>>();
    private final Set<IZkStateListener> _stateListener = new CopyOnWriteArraySet<IZkStateListener>();
    private final ZkLock _zkEventLock = new ZkLock();
    private KeeperState _currentState;
    private boolean _shutdownTriggered;
    private ZkEventThread _eventThread;
    // TODO PVo remove this later
    private Thread _zookeeperEventThread;
    private ZkSerializer _zkSerializer;
    private volatile boolean _closed;

    public ZkClient(String serverstring) {
        this(serverstring, Integer.MAX_VALUE);
    }

    public ZkClient(String zkServers, int connectionTimeout) {
        this(new ZkConnection(zkServers), connectionTimeout);
    }

    public ZkClient(String zkServers, int connectionTimeout, ZkSerializer serializer) {
        this(new ZkConnection(zkServers), connectionTimeout, serializer);
    }

    public ZkClient(String zkServers, int sessionTimeout, int connectionTimeout) {
        this(new ZkConnection(zkServers, sessionTimeout), connectionTimeout);
    }

    public ZkClient(String zkServers, int sessionTimeout, int connectionTimeout, ZkSerializer zkSerializer) {
        this(new ZkConnection(zkServers, sessionTimeout), connectionTimeout, zkSerializer);
    }

    /**
     *
     * @param zkServers
     *            The Zookeeper servers
     * @param sessionTimeout
     *            The session timeout in milli seconds
     * @param connectionTimeout
     *            The connection timeout in milli seconds
     * @param zkSerializer
     *            The Zookeeper data serializer
     * @param operationRetryTimeout
     *            Most operations done through this {@link ZkClient} are retried in cases like
     *            connection loss with the Zookeeper servers. During such failures, this
     *            <code>operationRetryTimeout</code> decides the maximum amount of time, in milli seconds, each
     *            operation is retried. A value lesser than 0 is considered as
     *            "retry forever until a connection has been reestablished".
     */
    public ZkClient(final String zkServers, final int sessionTimeout, final int connectionTimeout, final ZkSerializer zkSerializer, final long operationRetryTimeout) {
        this(new ZkConnection(zkServers, sessionTimeout), connectionTimeout, zkSerializer, operationRetryTimeout);
    }

    public ZkClient(IZkConnection connection) {
        this(connection, Integer.MAX_VALUE);
    }

    public ZkClient(IZkConnection connection, int connectionTimeout) {
        this(connection, connectionTimeout, new SerializableSerializer());
    }

    public ZkClient(IZkConnection zkConnection, int connectionTimeout, ZkSerializer zkSerializer) {
        this(zkConnection, connectionTimeout, zkSerializer, -1);
    }

    /**
     *
     * @param zkConnection
     *            The Zookeeper servers
     * @param connectionTimeout
     *            The connection timeout in milli seconds
     * @param zkSerializer
     *            The Zookeeper data serializer
     * @param operationRetryTimeout
     *            Most operations done through this {@link ZkClient} are retried in cases like
     *            connection loss with the Zookeeper servers. During such failures, this
     *            <code>operationRetryTimeout</code> decides the maximum amount of time, in milli seconds, each
     *            operation is retried. A value lesser than 0 is considered as
     *            "retry forever until a connection has been reestablished".
     */
    public ZkClient(final IZkConnection zkConnection, final int connectionTimeout, final ZkSerializer zkSerializer, final long operationRetryTimeout) {
        if (zkConnection == null) {
            throw new NullPointerException("Zookeeper connection is null!");
        }
        _connection = zkConnection;
        _zkSerializer = zkSerializer;
        this.operationRetryTimeoutInMillis = operationRetryTimeout;
        connect(connectionTimeout, this);
    }

    public void setZkSerializer(ZkSerializer zkSerializer) {
        _zkSerializer = zkSerializer;
    }

    public List<String> subscribeChildChanges(String path, IZkChildListener listener) {
        synchronized (_childListener) {
            Set<IZkChildListener> listeners = _childListener.get(path);
            if (listeners == null) {
                listeners = new CopyOnWriteArraySet<IZkChildListener>();
                _childListener.put(path, listeners);
            }
            listeners.add(listener);
        }
        return watchForChilds(path);
    }

    public void unsubscribeChildChanges(String path, IZkChildListener childListener) {
        synchronized (_childListener) {
            final Set<IZkChildListener> listeners = _childListener.get(path);
            if (listeners != null) {
                listeners.remove(childListener);
            }
        }
    }

    public void subscribeDataChanges(String path, IZkDataListener listener) {
        Set<IZkDataListener> listeners;
        synchronized (_dataListener) {
            listeners = _dataListener.get(path);
            if (listeners == null) {
                listeners = new CopyOnWriteArraySet<IZkDataListener>();
                _dataListener.put(path, listeners);
            }
            listeners.add(listener);
        }
        watchForData(path);
        LOG.debug("Subscribed data changes for " + path);
    }

    public void unsubscribeDataChanges(String path, IZkDataListener dataListener) {
        synchronized (_dataListener) {
            final Set<IZkDataListener> listeners = _dataListener.get(path);
            if (listeners != null) {
                listeners.remove(dataListener);
            }
            if (listeners == null || listeners.isEmpty()) {
                _dataListener.remove(path);
            }
        }
    }

    public void subscribeStateChanges(final IZkStateListener listener) {
        synchronized (_stateListener) {
            _stateListener.add(listener);
        }
    }

    public void unsubscribeStateChanges(IZkStateListener stateListener) {
        synchronized (_stateListener) {
            _stateListener.remove(stateListener);
        }
    }

    public void unsubscribeAll() {
        synchronized (_childListener) {
            _childListener.clear();
        }
        synchronized (_dataListener) {
            _dataListener.clear();
        }
        synchronized (_stateListener) {
            _stateListener.clear();
        }
    }

    // </listeners>

    /**
     * Create a persistent node.
     *
     * @param path
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public void createPersistent(String path) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        createPersistent(path, false);
    }

    /**
     * Create a persistent node and set its ACLs.
     *
     * @param path
     * @param createParents
     *            if true all parent dirs are created as well and no {@link ZkNodeExistsException} is thrown in case the
     *            path already exists
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public void createPersistent(String path, boolean createParents) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        createPersistent(path, createParents, ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    /**
     * Create a persistent node and set its ACLs.
     *
     * @param path
     * @param acl
     *            List of ACL permissions to assign to the node
     * @param createParents
     *            if true all parent dirs are created as well and no {@link ZkNodeExistsException} is thrown in case the
     *            path already exists
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public void createPersistent(String path, boolean createParents, List<ACL> acl) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        try {
            create(path, null, acl, CreateMode.PERSISTENT);
        } catch (ZkNodeExistsException e) {
            if (!createParents) {
                throw e;
            }
        } catch (ZkNoNodeException e) {
            if (!createParents) {
                throw e;
            }
            String parentDir = path.substring(0, path.lastIndexOf('/'));
            createPersistent(parentDir, createParents, acl);
            createPersistent(path, createParents, acl);
        }
    }

    /**
     * Sets the acl on path
     *
     * @param path
     * @param acl
     *            List of ACL permissions to assign to the path.
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public void setAcl(final String path, final List<ACL> acl) throws ZkException {
        if (path == null) {
            throw new NullPointerException("Missing value for path");
        }

        if (acl == null || acl.size() == 0) {
            throw new NullPointerException("Missing value for ACL");
        }

        if (!exists(path)) {
            throw new RuntimeException("trying to set acls on non existing node " + path);
        }

        retryUntilConnected(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Stat stat = new Stat();
                _connection.readData(path, stat, false);
                _connection.setAcl(path, acl, stat.getAversion());
                return null;
            }
        });
    }

    /**
     * Gets the acl on path
     *
     * @param path
     * @return an entry instance with key = list of acls on node and value = stats.
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public Entry<List<ACL>, Stat> getAcl(final String path) throws ZkException {
        if (path == null) {
            throw new NullPointerException("Missing value for path");
        }

        if (!exists(path)) {
            throw new RuntimeException("trying to get acls on non existing node " + path);
        }

        return retryUntilConnected(new Callable<Entry<List<ACL>, Stat>>() {
            @Override
            public Entry<List<ACL>, Stat> call() throws Exception {
                return _connection.getAcl(path);
            }
        });
    }


    /**
     * Create a persistent node.
     *
     * @param path
     * @param data
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public void createPersistent(String path, Object data) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        create(path, data, CreateMode.PERSISTENT);
    }

    /**
     * Create a persistent node.
     *
     * @param path
     * @param data
     * @param acl
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public void createPersistent(String path, Object data, List<ACL> acl) {
        create(path, data, acl, CreateMode.PERSISTENT);
    }

    /**
     * Create a persistent, sequental node.
     *
     * @param path
     * @param data
     * @return create node's path
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public String createPersistentSequential(String path, Object data) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        return create(path, data, CreateMode.PERSISTENT_SEQUENTIAL);
    }

    /**
     * Create a persistent, sequential node and set its ACL.
     *
     * @param path
     * @param acl
     * @param data
     * @return create node's path
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public String createPersistentSequential(String path, Object data, List<ACL> acl) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        return create(path, data, acl, CreateMode.PERSISTENT_SEQUENTIAL);
    }

    /**
     * Create an ephemeral node.
     *
     * @param path
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public void createEphemeral(final String path) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        create(path, null, CreateMode.EPHEMERAL);
    }

    /**
     * Create an ephemeral node and set its ACL.
     *
     * @param path
     * @param acl
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public void createEphemeral(final String path, final List<ACL> acl) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        create(path, null, acl, CreateMode.EPHEMERAL);
    }

    /**
     * Create a node.
     *
     * @param path
     * @param data
     * @param mode
     * @return create node's path
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public String create(final String path, Object data, final CreateMode mode) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        return create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
    }

    /**
     * Create a node with ACL.
     *
     * @param path
     * @param data
     * @param acl
     * @param mode
     * @return create node's path
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public String create(final String path, Object data, final List<ACL> acl, final CreateMode mode) {
        if (path == null) {
            throw new NullPointerException("Missing value for path");
        }
        if (acl == null || acl.size() == 0) {
            throw new NullPointerException("Missing value for ACL");
        }
        final byte[] bytes = data == null ? null : serialize(data);

        return retryUntilConnected(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return _connection.create(path, bytes, acl, mode);
            }
        });

    }

    /**
     * Create an ephemeral node.
     *
     * @param path
     * @param data
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public void createEphemeral(final String path, final Object data) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        create(path, data, CreateMode.EPHEMERAL);
    }

    /**
     * Create an ephemeral node.
     *
     * @param path
     * @param data
     * @param acl
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public void createEphemeral(final String path, final Object data, final List<ACL> acl) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        create(path, data, acl, CreateMode.EPHEMERAL);
    }

    /**
     * Create an ephemeral, sequential node.
     *
     * @param path
     * @param data
     * @return created path
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public String createEphemeralSequential(final String path, final Object data) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        return create(path, data, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    /**
     * Create an ephemeral, sequential node with ACL.
     *
     * @param path
     * @param data
     * @param acl
     * @return created path
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs
     */
    public String createEphemeralSequential(final String path, final Object data, final List<ACL> acl) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        return create(path, data, acl, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    @Override
    public void process(WatchedEvent event) {
        LOG.debug("Received event: " + event);
        _zookeeperEventThread = Thread.currentThread();

        boolean stateChanged = event.getPath() == null;
        boolean znodeChanged = event.getPath() != null;
        boolean dataChanged = event.getType() == EventType.NodeDataChanged || event.getType() == EventType.NodeDeleted || event.getType() == EventType.NodeCreated
                || event.getType() == EventType.NodeChildrenChanged;

        getEventLock().lock();
        try {

            // We might have to install child change event listener if a new node was created
            if (getShutdownTrigger()) {
                LOG.debug("ignoring event '{" + event.getType() + " | " + event.getPath() + "}' since shutdown triggered");
                return;
            }
            if (stateChanged) {
                processStateChanged(event);
            }
            if (dataChanged) {
                processDataOrChildChange(event);
            }
        } finally {
            if (stateChanged) {
                getEventLock().getStateChangedCondition().signalAll();

                // If the session expired we have to signal all conditions, because watches might have been removed and
                // there is no guarantee that those
                // conditions will be signaled at all after an Expired event
                // TODO PVo write a test for this
                if (event.getState() == KeeperState.Expired) {
                    getEventLock().getZNodeEventCondition().signalAll();
                    getEventLock().getDataChangedCondition().signalAll();
                    // We also have to notify all listeners that something might have changed
                    fireAllEvents();
                }
            }
            if (znodeChanged) {
                getEventLock().getZNodeEventCondition().signalAll();
            }
            if (dataChanged) {
                getEventLock().getDataChangedCondition().signalAll();
            }
            getEventLock().unlock();
            LOG.debug("Leaving process event");
        }
    }

    private void fireAllEvents() {
        for (Entry<String, Set<IZkChildListener>> entry : _childListener.entrySet()) {
            fireChildChangedEvents(entry.getKey(), entry.getValue());
        }
        for (Entry<String, Set<IZkDataListener>> entry : _dataListener.entrySet()) {
            fireDataChangedEvents(entry.getKey(), entry.getValue());
        }
    }

    public List<String> getChildren(String path) {
        return getChildren(path, hasListeners(path));
    }

    protected List<String> getChildren(final String path, final boolean watch) {
        return retryUntilConnected(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return _connection.getChildren(path, watch);
            }
        });
    }

    /**
     * Counts number of children for the given path.
     *
     * @param path
     * @return number of children or 0 if path does not exist.
     */
    public int countChildren(String path) {
        try {
            return getChildren(path).size();
        } catch (ZkNoNodeException e) {
            return 0;
        }
    }

    protected boolean exists(final String path, final boolean watch) {
        return retryUntilConnected(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return _connection.exists(path, watch);
            }
        });
    }

    public boolean exists(final String path) {
        return exists(path, hasListeners(path));
    }

    private void processStateChanged(WatchedEvent event) {
        LOG.info("zookeeper state changed (" + event.getState() + ")");
        setCurrentState(event.getState());
        if (getShutdownTrigger()) {
            return;
        }
        fireStateChangedEvent(event.getState());
        if (event.getState() == KeeperState.Expired) {
            try {
                reconnect();
                fireNewSessionEvents();
            } catch (final Exception e) {
                LOG.info("Unable to re-establish connection. Notifying consumer of the following exception: ", e);
                fireSessionEstablishmentError(e);
            }
        }
    }

    private void fireNewSessionEvents() {
        for (final IZkStateListener stateListener : _stateListener) {
            _eventThread.send(new ZkEventThread.ZkEvent("New session event sent to " + stateListener) {

                @Override
                public void run() throws Exception {
                    stateListener.handleNewSession();
                }
            });
        }
    }

    private void fireStateChangedEvent(final KeeperState state) {
        for (final IZkStateListener stateListener : _stateListener) {
            _eventThread.send(new ZkEventThread.ZkEvent("State changed to " + state + " sent to " + stateListener) {

                @Override
                public void run() throws Exception {
                    stateListener.handleStateChanged(state);
                }
            });
        }
    }

    private void fireSessionEstablishmentError(final Throwable error) {
        for (final IZkStateListener stateListener : _stateListener) {
            _eventThread.send(new ZkEventThread.ZkEvent("Session establishment error(" + error + ") sent to " + stateListener) {

                @Override
                public void run() throws Exception {
                    stateListener.handleSessionEstablishmentError(error);
                }
            });
        }
    }

    private boolean hasListeners(String path) {
        Set<IZkDataListener> dataListeners = _dataListener.get(path);
        if (dataListeners != null && dataListeners.size() > 0) {
            return true;
        }
        Set<IZkChildListener> childListeners = _childListener.get(path);
        return childListeners != null && childListeners.size() > 0;
    }

    public boolean deleteRecursive(String path) {
        List<String> children;
        try {
            children = getChildren(path, false);
        } catch (ZkNoNodeException e) {
            return true;
        }

        for (String subPath : children) {
            if (!deleteRecursive(path + "/" + subPath)) {
                return false;
            }
        }

        return delete(path);
    }

    /**
     * modfiy by zhangcheng 20161206 ??????????????????????????????????????????????????????
     * @param path
     * @return
     */
    public boolean deleteChildren(String path) {
        List<String> children;
        try {
            children = getChildren(path, false);
        } catch (ZkNoNodeException e) {
            return true;
        }

        for (String subPath : children) {
            if (!deleteRecursive(path + "/" + subPath)) {
                return false;
            }
        }
        return true;
    }

    private void processDataOrChildChange(WatchedEvent event) {
        final String path = event.getPath();

        if (event.getType() == EventType.NodeChildrenChanged || event.getType() == EventType.NodeCreated || event.getType() == EventType.NodeDeleted) {
            Set<IZkChildListener> childListeners = _childListener.get(path);
            if (childListeners != null && !childListeners.isEmpty()) {
                fireChildChangedEvents(path, childListeners);
            }
        }

        if (event.getType() == EventType.NodeDataChanged || event.getType() == EventType.NodeDeleted || event.getType() == EventType.NodeCreated) {
            Set<IZkDataListener> listeners = _dataListener.get(path);
            if (listeners != null && !listeners.isEmpty()) {
                fireDataChangedEvents(event.getPath(), listeners);
            }
        }
    }

    private void fireDataChangedEvents(final String path, Set<IZkDataListener> listeners) {
        for (final IZkDataListener listener : listeners) {
            _eventThread.send(new ZkEventThread.ZkEvent("Data of " + path + " changed sent to " + listener) {

                @Override
                public void run() throws Exception {
                    // reinstall watch
                    exists(path, true);
                    try {
                        Object data = readData(path, null, true);
                        listener.handleDataChange(path, data);
                    } catch (ZkNoNodeException e) {
                        listener.handleDataDeleted(path);
                    }
                }
            });
        }
    }

    private void fireChildChangedEvents(final String path, Set<IZkChildListener> childListeners) {
        try {
            // reinstall the watch
            for (final IZkChildListener listener : childListeners) {
                _eventThread.send(new ZkEventThread.ZkEvent("Children of " + path + " changed sent to " + listener) {

                    @Override
                    public void run() throws Exception {
                        try {
                            // if the node doesn't exist we should listen for the root node to reappear
                            exists(path);
                            List<String> children = getChildren(path);
                            listener.handleChildChange(path, children);
                        } catch (ZkNoNodeException e) {
                            listener.handleChildChange(path, null);
                        }
                    }
                });
            }
        } catch (Exception e) {
            LOG.error("Failed to fire child changed event. Unable to getChildren.  ", e);
        }
    }

    public boolean waitUntilExists(String path, TimeUnit timeUnit, long time) throws ZkInterruptedException {
        Date timeout = new Date(System.currentTimeMillis() + timeUnit.toMillis(time));
        LOG.debug("Waiting until znode '" + path + "' becomes available.");
        if (exists(path)) {
            return true;
        }
        acquireEventLock();
        try {
            while (!exists(path, true)) {
                boolean gotSignal = getEventLock().getZNodeEventCondition().awaitUntil(timeout);
                if (!gotSignal) {
                    return false;
                }
            }
            return true;
        } catch (InterruptedException e) {
            throw new ZkInterruptedException(e);
        } finally {
            getEventLock().unlock();
        }
    }

    protected Set<IZkDataListener> getDataListener(String path) {
        return _dataListener.get(path);
    }

    public void showFolders(OutputStream output) {
        try {
            output.write(ZkPathUtil.toString(this).getBytes());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void waitUntilConnected() throws ZkInterruptedException {
        waitUntilConnected(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    public boolean waitUntilConnected(long time, TimeUnit timeUnit) throws ZkInterruptedException {
        return waitForKeeperState(KeeperState.SyncConnected, time, timeUnit);
    }

    public boolean waitForKeeperState(KeeperState keeperState, long time, TimeUnit timeUnit) throws ZkInterruptedException {
        if (_zookeeperEventThread != null && Thread.currentThread() == _zookeeperEventThread) {
            throw new IllegalArgumentException("Must not be done in the zookeeper event thread.");
        }
        Date timeout = new Date(System.currentTimeMillis() + timeUnit.toMillis(time));

        LOG.debug("Waiting for keeper state " + keeperState);
        acquireEventLock();
        try {
            boolean stillWaiting = true;
            while (_currentState != keeperState) {
                if (!stillWaiting) {
                    return false;
                }
                stillWaiting = getEventLock().getStateChangedCondition().awaitUntil(timeout);
            }
            LOG.debug("State is " + _currentState);
            return true;
        } catch (InterruptedException e) {
            throw new ZkInterruptedException(e);
        } finally {
            getEventLock().unlock();
        }
    }

    private void acquireEventLock() {
        try {
            getEventLock().lockInterruptibly();
        } catch (InterruptedException e) {
            throw new ZkInterruptedException(e);
        }
    }

    /**
     *
     * @param <T>
     * @param callable
     * @return result of Callable
     * @throws ZkInterruptedException
     *             if operation was interrupted, or a required reconnection got interrupted
     * @throws IllegalArgumentException
     *             if called from anything except the ZooKeeper event thread
     * @throws ZkException
     *             if any ZooKeeper exception occurred
     * @throws RuntimeException
     *             if any other exception occurs from invoking the Callable
     */
    public <T> T retryUntilConnected(Callable<T> callable) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
        if (_zookeeperEventThread != null && Thread.currentThread() == _zookeeperEventThread) {
            throw new IllegalArgumentException("Must not be done in the zookeeper event thread.");
        }
        final long operationStartTime = System.currentTimeMillis();
        while (true) {
            if (_closed) {
                throw new IllegalStateException("ZkClient already closed!");
            }
            try {
                return callable.call();
            } catch (ConnectionLossException e) {
                // we give the event thread some time to update the status to 'Disconnected'
                Thread.yield();
                waitForRetry();
            } catch (SessionExpiredException e) {
                // we give the event thread some time to update the status to 'Expired'
                Thread.yield();
                waitForRetry();
            } catch (KeeperException e) {
                // TODO modfiy by zhangcheng ????????????????????????????????????????????????????????????????????????????????????
                //e.printStackTrace();
                throw ZkException.create(e);
            } catch (InterruptedException e) {
                throw new ZkInterruptedException(e);
            } catch (Exception e) {
                //e.printStackTrace();
                throw ExceptionUtil.convertToRuntimeException(e);
            }
            // before attempting a retry, check whether retry timeout has elapsed
            if (this.operationRetryTimeoutInMillis > -1 && (System.currentTimeMillis() - operationStartTime) >= this.operationRetryTimeoutInMillis) {
                throw new ZkTimeoutException("Operation cannot be retried because of retry timeout (" + this.operationRetryTimeoutInMillis + " milli seconds)");
            }
        }
    }

    private void waitForRetry() {
        if (this.operationRetryTimeoutInMillis < 0) {
            this.waitUntilConnected();
            return;
        }
        this.waitUntilConnected(this.operationRetryTimeoutInMillis, TimeUnit.MILLISECONDS);
    }

    public void setCurrentState(KeeperState currentState) {
        getEventLock().lock();
        try {
            _currentState = currentState;
        } finally {
            getEventLock().unlock();
        }
    }

    /**
     * Returns a mutex all zookeeper events are synchronized aginst. So in case you need to do something without getting
     * any zookeeper event interruption synchronize against this mutex. Also all threads waiting on this mutex object
     * will be notified on an event.
     *
     * @return the mutex.
     */
    public ZkLock getEventLock() {
        return _zkEventLock;
    }

    public boolean delete(final String path) {
        try {
            retryUntilConnected(new Callable<Object>() {

                @Override
                public Object call() throws Exception {
                    _connection.delete(path);
                    return null;
                }
            });

            return true;
        } catch (ZkNoNodeException e) {
            return false;
        }
    }

    private byte[] serialize(Object data) {
        return _zkSerializer.serialize(data);
    }

    @SuppressWarnings("unchecked")
    private <T extends Object> T derializable(byte[] data) {
        if (data == null) {
            return null;
        }
        return (T) _zkSerializer.deserialize(data);
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T readData(String path) {
        return (T) readData(path, false);
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T readData(String path, boolean returnNullIfPathNotExists) {
        T data = null;
        try {
            data = readData(path, null);
        } catch (ZkNoNodeException e) {
            if (!returnNullIfPathNotExists) {
                throw e;
            }
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T readData(String path, Stat stat) {
        return (T) readData(path, stat, hasListeners(path));
    }

    @SuppressWarnings("unchecked")
    protected <T extends Object> T readData(final String path, final Stat stat, final boolean watch) {
        byte[] data = retryUntilConnected(new Callable<byte[]>() {

            @Override
            public byte[] call() throws Exception {
                return _connection.readData(path, stat, watch);
            }
        });
        return (T) derializable(data);
    }

    public void writeData(String path, Object object) {
        writeData(path, object, -1);
    }

    /**
     * Updates data of an existing znode. The current content of the znode is passed to the {@link DataUpdater} that is
     * passed into this method, which returns the new content. The new content is only written back to ZooKeeper if
     * nobody has modified the given znode in between. If a concurrent change has been detected the new data of the
     * znode is passed to the updater once again until the new contents can be successfully written back to ZooKeeper.
     *
     * @param <T>
     * @param path
     *            The path of the znode.
     * @param updater
     *            Updater that creates the new contents.
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> void updateDataSerialized(String path, DataUpdater<T> updater) {
        Stat stat = new Stat();
        boolean retry;
        do {
            retry = false;
            try {
                T oldData = readData(path, stat);
                T newData = updater.update(oldData);
                writeData(path, newData, stat.getVersion());
            } catch (ZkBadVersionException e) {
                retry = true;
            }
        } while (retry);
    }

    public void writeData(final String path, Object datat, final int expectedVersion) {
        writeDataReturnStat(path, datat, expectedVersion);
    }

    public Stat writeDataReturnStat(final String path, Object datat, final int expectedVersion) {
        final byte[] data = serialize(datat);
        return (Stat) retryUntilConnected(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                Stat stat = _connection.writeDataReturnStat(path, data, expectedVersion);
                return stat;
            }
        });
    }

    public void watchForData(final String path) {
        retryUntilConnected(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                _connection.exists(path, true);
                return null;
            }
        });
    }

    /**
     * Installs a child watch for the given path.
     *
     * @param path
     * @return the current children of the path or null if the zk node with the given path doesn't exist.
     */
    public List<String> watchForChilds(final String path) {
        if (_zookeeperEventThread != null && Thread.currentThread() == _zookeeperEventThread) {
            throw new IllegalArgumentException("Must not be done in the zookeeper event thread.");
        }
        return retryUntilConnected(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                exists(path, true);
                try {
                    return getChildren(path, true);
                } catch (ZkNoNodeException e) {
                    // ignore, the "exists" watch will listen for the parent node to appear
                }
                return null;
            }
        });
    }

    /**
     * Add authentication information to the connection. This will be used to identify the user and check access to
     * nodes protected by ACLs
     *
     * @param scheme
     * @param auth
     */
    public void addAuthInfo(final String scheme, final byte[] auth) {
        retryUntilConnected(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                _connection.addAuthInfo(scheme, auth);
                return null;
            }
        });
    }

    /**
     * Connect to ZooKeeper.
     *
     * @param maxMsToWaitUntilConnected
     * @param watcher
     * @throws ZkInterruptedException
     *             if the connection timed out due to thread interruption
     * @throws ZkTimeoutException
     *             if the connection timed out
     * @throws IllegalStateException
     *             if the connection timed out due to thread interruption
     */
    public void connect(final long maxMsToWaitUntilConnected, Watcher watcher) throws ZkInterruptedException, ZkTimeoutException, IllegalStateException {
        boolean started = false;
        acquireEventLock();
        try {
            setShutdownTrigger(false);
            _eventThread = new ZkEventThread(_connection.getServers());
            _eventThread.start();
            _connection.connect(watcher);

            LOG.debug("Awaiting connection to Zookeeper server");
            if (!waitUntilConnected(maxMsToWaitUntilConnected, TimeUnit.MILLISECONDS)) {
                throw new ZkTimeoutException("Unable to connect to zookeeper server within timeout: " + maxMsToWaitUntilConnected);
            }
            started = true;
        } finally {
            getEventLock().unlock();

            // we should close the zookeeper instance, otherwise it would keep
            // on trying to connect
            if (!started) {
                close();
            }
        }
    }

    public long getCreationTime(String path) {
        acquireEventLock();
        try {
            return _connection.getCreateTime(path);
        } catch (KeeperException e) {
            throw ZkException.create(e);
        } catch (InterruptedException e) {
            throw new ZkInterruptedException(e);
        } finally {
            getEventLock().unlock();
        }
    }

    /**
     * Close the client.
     *
     * @throws ZkInterruptedException
     */
    public void close() throws ZkInterruptedException {
        if (_closed) {
            return;
        }
        LOG.debug("Closing ZkClient...");
        getEventLock().lock();
        try {
            setShutdownTrigger(true);
            _eventThread.interrupt();
            _eventThread.join(2000);
            _connection.close();
            _closed = true;
        } catch (InterruptedException e) {
            throw new ZkInterruptedException(e);
        } finally {
            getEventLock().unlock();
        }
        LOG.debug("Closing ZkClient...done");
    }

    private void reconnect() {
        getEventLock().lock();
        try {
            _connection.close();
            _connection.connect(this);
        } catch (InterruptedException e) {
            throw new ZkInterruptedException(e);
        } finally {
            getEventLock().unlock();
        }
    }

    public boolean getShutdownTrigger() {
        return _shutdownTriggered;
    }

    public void setShutdownTrigger(boolean triggerState) {
        _shutdownTriggered = triggerState;
    }

    public int numberOfListeners() {
        int listeners = 0;
        for (Set<IZkChildListener> childListeners : _childListener.values()) {
            listeners += childListeners.size();
        }
        for (Set<IZkDataListener> dataListeners : _dataListener.values()) {
            listeners += dataListeners.size();
        }
        listeners += _stateListener.size();

        return listeners;
    }

    public List<OpResult> multi(final Iterable<Op> ops) throws ZkException {
        if (ops == null) {
            throw new NullPointerException("ops must not be null.");
        }

        return retryUntilConnected(new Callable<List<OpResult>>() {

            @Override
            public List<OpResult> call() throws Exception {
                return _connection.multi(ops);
            }
        });
    }

    public boolean isConnected() {
        return _currentState == KeeperState.SyncConnected;
    }
}
