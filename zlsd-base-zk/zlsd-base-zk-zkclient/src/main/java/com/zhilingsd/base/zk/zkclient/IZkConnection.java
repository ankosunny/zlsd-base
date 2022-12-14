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

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Map;

public interface IZkConnection {

    void connect(Watcher watcher);

    void close() throws InterruptedException;

    String create(String path, byte[] data, CreateMode mode) throws KeeperException, InterruptedException;

    String create(String path, byte[] data, List<ACL> acl, CreateMode mode) throws KeeperException, InterruptedException;

    void delete(String path) throws InterruptedException, KeeperException;

    boolean exists(final String path, final boolean watch) throws KeeperException, InterruptedException;

    List<String> getChildren(final String path, final boolean watch) throws KeeperException, InterruptedException;

    byte[] readData(String path, Stat stat, boolean watch) throws KeeperException, InterruptedException;

    void writeData(String path, byte[] data, int expectedVersion) throws KeeperException, InterruptedException;

    Stat writeDataReturnStat(String path, byte[] data, int expectedVersion) throws KeeperException, InterruptedException;

    States getZookeeperState();

    long getCreateTime(String path) throws KeeperException, InterruptedException;

    String getServers();

    List<OpResult> multi(Iterable<Op> ops) throws KeeperException, InterruptedException;

    void addAuthInfo(String scheme, byte[] auth);

    void setAcl(final String path, List<ACL> acl, int version) throws KeeperException, InterruptedException;

    Map.Entry<List<ACL>, Stat> getAcl(final String path) throws KeeperException, InterruptedException;
}