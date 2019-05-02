/**
 * Software License Declaration.
 * <p>
 * zhilingsd.com, Co,. Ltd.
 * Copyright © 2019 All Rights Reserved.
 * <p>
 * Copyright Notice
 * This documents is provided to zhilingsd contracting agent or authorized programmer only.
 * This source code is written and edited by zhilingsd Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 * <p>
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to zhilingsd Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise zhilingsd will charge the fee according to the programme itself.
 * <p>
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of wandaph. If Any problem cannot be solved in the
 * procedure of programming should be feedback to zhilingsd Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.collection.utils;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author ZhangRong
 * @version Id: com.zhilingsd.collection.utils.SerializeUtil, v 0.1 2019/5/2 15:21 ZhangRong Exp $$
 */
public class SerializeUtil {
//    private final static Log LOG = LogFactory.getLog(SerializeUtil.class);

    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream os = null;
        HessianOutput ho = null;
        try {
            os = new ByteArrayOutputStream();
            ho = new HessianOutput(os);
            ho.writeObject(object);
            byte[] bytes = os.toByteArray();
            return bytes;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            LOG.error(e.getMessage());
        } finally {
            if (ho != null) {
                try {
                    ho.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] serializeKey(String string) {
        return string != null ? string.getBytes(StandardCharsets.UTF_8) : null;
    }

    public static byte[][] serializes(Object... values) {
        byte[][] strs = new byte[values.length][];
        for (int i = 0; i < values.length; i++) {
            strs[i] = serialize(values[i]);
        }
        return strs;
    }

    public static byte[][] serializeKeys(String... values) {
        byte[][] strs = new byte[values.length][];
        for (int i = 0; i < values.length; i++) {
            strs[i] = serializeKey(values[i]);
        }
        return strs;
    }

    /**
     * 序列化Map对象，java的主类型，不序列化
     *
     * @param map
     * @return
     */
    public static Map<byte[], byte[]> serializeMap(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<byte[], byte[]> result = new HashMap<byte[], byte[]>();

        for (Iterator<String> it = map.keySet().iterator(); it.hasNext(); ) {
            String field = it.next();
            Object value = map.get(field);

            byte[] byte_field = serialize(field);
            byte[] storeValue = serialize(value);
            result.put(byte_field, storeValue);
        }

        return result;
    }

    /**
     * 序列化String的List对象.
     *
     * @param list
     * @return
     */
    public static List<byte[]> serializeStringList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        List<byte[]> result = new ArrayList<byte[]>();

        for (String s : list) {
            byte[] storeValue = serializeKey(s);
            result.add(storeValue);
        }

        return result;
    }

    /**
     * 序列化Object的List对象.
     *
     * @param list
     * @return
     */
    public static List<byte[]> serializeObjectList(List<Object> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        List<byte[]> result = new ArrayList<byte[]>();

        for (Object o : list) {
            byte[] storeValue = serialize(o);
            result.add(storeValue);
        }

        return result;
    }

    public static Object unSerialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        ByteArrayInputStream is = null;
        HessianInput hi = null;
        try {
            // 反序列化
            is = new ByteArrayInputStream(bytes);
            hi = new HessianInput(is);
            Object obj = hi.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
//            LOG.error(e.getMessage());
        } finally {
            if (hi != null) {
                hi.close();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String unSerializeKey(byte[] bytes) {
        return bytes != null ? new String(bytes, StandardCharsets.UTF_8) : null;
    }

    public static List<Object> unSerializeList(List<byte[]> list) {
        if (list == null | list.isEmpty()) {
            return null;
        }
        List<Object> result = new ArrayList<Object>();
        for (byte[] jsonstr : list) {
            result.add(unSerialize(jsonstr));
        }
        return result;
    }

    public static Set<Object> unSerializeSet(Set<byte[]> set) {
        if (set == null | set.isEmpty()) {
            return null;
        }
        Set<Object> result = new HashSet<Object>();
        for (byte[] jsonstr : set) {
            result.add(unSerialize(jsonstr));
        }
        return result;
    }

    public static Set<String> unSerializeKeySet(Set<byte[]> set) {
        if (set == null | set.isEmpty()) {
            return null;
        }
        Set<String> result = new HashSet<String>();
        for (byte[] jsonstr : set) {
            result.add(unSerializeKey(jsonstr));
        }
        return result;
    }

    public static Map<String, Object> unSerializeMap(Map<byte[], byte[]> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> result = new HashMap<String, Object>();

        for (Iterator<byte[]> it = map.keySet().iterator(); it.hasNext(); ) {
            byte[] byte_field = it.next();
            byte[] value = map.get(byte_field);

            String field = (String) unSerialize(byte_field);
            Object storeValue = unSerialize(value);
            result.put(field, storeValue);
        }

        return result;
    }

    /**
     * 计算一个对象所占字节数
     *
     * @param o 对象，该对象必须继承Serializable接口即可序列化
     * @return
     * @throws IOException
     */
    public static int size(final Object o) throws IOException {
        if (o == null) {
            return 0;
        }
        ByteArrayOutputStream buf = new ByteArrayOutputStream(4096);
        ObjectOutputStream out = new ObjectOutputStream(buf);
        out.writeObject(o);
        out.flush();
        buf.close();

        return buf.size();
    }
}

