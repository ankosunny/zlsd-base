/**
 * Software License Declaration.
 * <p>
 * zhilingsd.com, Co,. Ltd.
 * Copyright © 2018 All Rights Reserved.
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
 * the third party without the agreement of zhilingsd. If Any problem cannot be solved in the
 * procedure of programming should be feedback to zhilingsd Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.base.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存接口
 *
 * @author zhangrong
 * @version Id: Cache.java, v 0.1 2018/11/7 14:30 zhangrong Exp $$
 */
public interface Cache<K extends Serializable, V extends Serializable> {
    /**
     * 存,默认过期时间300秒，即5分钟.
     * 默认过期时间可通过：zhilingsd.base.redis.expire.seconds 进行配置
     *
     * @param key   键
     * @param value 值
     */
    void set(K key, V value);

    /**
     * 存,带过期时间.
     *
     * @param key
     * @param value
     * @param time  过期时间，单位时间：秒
     */
    void set(K key, V value, int time);

    /**
     * 取.
     *
     * @param key
     * @return
     * @throws Exception
     */
    V get(K key) throws Exception;

    /**
     * 删除.
     *
     * @param key
     */
    void delete(K key);

    /**
     * 判断键是否存在.
     *
     * @param key
     * @return
     */
    Boolean isKeyExist(K key);

    /**
     * 如果键不存在则存.set if not exist.
     *
     * @param key
     * @param value
     * @return
     */
    Boolean setnx(K key, V value);

    /**
     * 如果键不存在则存.set if not exist.
     * 默认过期时间300秒，即5分钟.
     * 默认过期时间可通过：zhilingsd.base.redis.expire.seconds 进行配置
     *
     * @param key
     * @param value
     * @param time  过期时间
     * @return
     */
    Boolean setnx(K key, V value, int time);

    /**
     * 设置过期时间.
     *
     * @param key
     * @param time 过期时间，单位时间：秒
     * @return
     */
    Boolean expire(K key, int time);

    /**
     * list key左侧加值.
     *
     * @param key
     * @param value
     * @return list长度
     */
    Long lpush(K key, V value);

    /**
     * list key左侧加值.
     *
     * @param key
     * @param value 默认过期时间300秒，即5分钟.
     *              默认过期时间可通过：zhilingsd.base.redis.expire.seconds 进行配置
     * @return list长度
     */
    Long lpush(K key, V value, int time);

    /**
     * list key右侧加值.
     *
     * @param key
     * @param value
     */
    Long rpush(K key, V value);

    /**
     * list key右侧加值.
     * 默认过期时间300秒，即5分钟.
     * 默认过期时间可通过：zhilingsd.base.redis.expire.seconds 进行配置
     *
     * @param key
     * @param value
     */
    Long rpush(K key, V value, int time);

    /**
     * list key左侧取值.
     *
     * @param key
     * @return
     * @throws Exception
     */
    V lpop(K key) throws Exception;

    /**
     * list key右侧取值.
     *
     * @param key
     * @return
     * @throws Exception
     */
    V rpop(K key) throws Exception;

    /**
     * 获取全量list key数据.
     *
     * @param key
     * @return
     */
    List<Object> lget(K key);

    /**
     * 获取list key的长度.
     *
     * @param key
     * @return
     */
    Long lLength(K key);

    /**
     * 移除list key中指定value的所有项.
     *
     * @param key
     * @param value
     * @return 被移除的元素的数量。
     */
    Long lremove(K key, V value);

    /**
     * 将哈希表 key 中的域 field 的值设为 value.
     * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
     * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
     * 默认过期时间300秒，即5分钟.
     * 默认过期时间可通过：zhilingsd.base.redis.expire.seconds 进行配置
     *
     * @param key
     * @param field
     * @param value
     */
    void hset(K key, String field, V value);

    /**
     * 将哈希表 key 中的域 field 的值设为 value.
     * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
     * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
     *
     * @param key
     * @param field
     * @param value
     * @param expireTime 过期时间
     */
    void hset(K key, String field, V value, int expireTime);

    /**
     * 返回哈希表 key 中给定域 field 的值.
     *
     * @param key
     * @param field
     * @return
     */
    Serializable hget(K key, String field);

    /**
     * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在.
     * 若域 field 已经存在，该操作无效。
     * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
     * 默认过期时间300秒，即5分钟.
     * 默认过期时间可通过：zhilingsd.base.redis.expire.seconds 进行配置
     *
     * @param key
     * @param field
     * @param value
     * @return 设置成功，返回 true,否则 false.
     */
    Boolean hsetnx(K key, String field, V value);

    /**
     * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在.
     * 若域 field 已经存在，该操作无效。
     * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
     *
     * @param key
     * @param field
     * @param value
     * @param expireSeconds 超时时间
     * @return 设置成功，返回 true,否则 false.
     */
    Boolean hsetnx(K key, String field, V value, int expireSeconds);

    /**
     * @param key
     * @return
     */
    Set<Object> hkeys(K key);

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中.
     * 此命令会覆盖哈希表中已存在的域。
     * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。
     * 默认过期时间300秒，即5分钟.
     * 默认过期时间可通过：zhilingsd.base.redis.expire.seconds 进行配置
     *
     * @param key
     * @param fieldValues
     */
    void hmset(final K key, final Map<String, V> fieldValues);

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中.
     * 此命令会覆盖哈希表中已存在的域。
     * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。
     *
     * @param key
     * @param fieldValues
     * @param expireSeconds
     */
    void hmset(final K key, final Map<String, V> fieldValues, int expireSeconds);

    /**
     * 返回哈希表 key 中，一个或多个给定域的值.
     *
     * @param key
     * @param fields
     * @return 如果给定的域不存在于哈希表，那么返回一个 nil 值.
     * 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。
     */
    List<Serializable> hmget(K key, String... fields);

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略.
     *
     * @param key
     * @param fields
     * @return 被成功移除的域的数量，不包括被忽略的域。
     */
    Long hdel(K key, String... fields);
}
