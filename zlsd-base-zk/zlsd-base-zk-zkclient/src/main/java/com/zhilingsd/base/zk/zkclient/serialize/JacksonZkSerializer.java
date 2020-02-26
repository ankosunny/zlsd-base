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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.zhilingsd.base.zk.zkclient.exception.ZkMarshallingError;

import java.nio.charset.Charset;

public class JacksonZkSerializer<T> implements ZkSerializer {


    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private final JavaType javaType;

    private ObjectMapper objectMapper = new ObjectMapper();

    public JacksonZkSerializer(Class<T> type) {
        this.javaType = getJavaType(type);
    }

    public JacksonZkSerializer(JavaType javaType) {
        this.javaType = javaType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(byte[] bytes) throws ZkMarshallingError {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return (T) this.objectMapper.readValue(bytes, 0, bytes.length, javaType);
        } catch (Exception ex) {
            throw new ZkMarshallingError("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] serialize(Object t) throws ZkMarshallingError {
        if (t == null) {
            return new byte[0];
        }
        try {
            return this.objectMapper.writeValueAsBytes(t);
        } catch (Exception ex) {
            throw new ZkMarshallingError("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }

}
