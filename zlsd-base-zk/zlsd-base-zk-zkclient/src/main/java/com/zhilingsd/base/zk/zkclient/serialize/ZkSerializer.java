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
 * Zookeeper is able to store data in form of byte arrays. This interfacte is a bridge between those byte-array format
 * and higher level objects.
 *
 * @see BytesPushThroughSerializer
 * @see SerializableSerializer
 */
public interface ZkSerializer {

    byte[] serialize(Object data) throws ZkMarshallingError;

    Object deserialize(byte[] bytes) throws ZkMarshallingError;
}
