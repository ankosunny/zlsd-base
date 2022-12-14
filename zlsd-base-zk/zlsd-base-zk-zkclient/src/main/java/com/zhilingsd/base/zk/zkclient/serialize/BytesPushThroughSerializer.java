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
package com.zhilingsd.base.zk.zkclient.serialize;

import com.zhilingsd.base.zk.zkclient.exception.ZkMarshallingError;

/**
 * A {@link ZkSerializer} which simply passes byte arrays to zk and back.
 */
public class BytesPushThroughSerializer implements ZkSerializer {

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        return bytes;
    }

    @Override
    public byte[] serialize(Object bytes) throws ZkMarshallingError {
        return (byte[]) bytes;
    }

}
