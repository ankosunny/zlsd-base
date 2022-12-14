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

public class Gateway {

    private final int _port;
    private final int _destinationPort;
    private GatewayThread _thread;

    public Gateway(int port, int destinationPort) {
        _port = port;
        _destinationPort = destinationPort;
    }

    public synchronized void start() {
        if (_thread != null) {
            throw new IllegalStateException("Gateway already running");
        }
        _thread = new GatewayThread(_port, _destinationPort);
        _thread.start();
        _thread.awaitUp();
    }

    public synchronized void stop() {
        if (_thread != null) {
            try {
                _thread.interruptAndJoin();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            _thread = null;
        }
    }
}
