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
public interface Cache<K, V> {
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
     */
    V get(K key);

    /**
     * 自增
     * */
    Double increment(Object key, double delta);

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
     * 获取过期时间
     * @param key
     * @return 过期时间，单位时间：秒
     */
    long getExpire(Object key);

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
     */
    V lpop(K key);

    /**
     * list key右侧取值.
     *
     * @param key
     * @return
     */
    V rpop(K key);

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
    Object hget(K key, String field);

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

    /**
     * 添加一个指定的member元素到集合的key中，使用默认超时时间.
     * 指定的一个元素member 如果已经在集合key中存在则忽略.
     * 如果集合key 不存在，则新建集合key,并添加member元素到集合key中.
     * 如果key 的类型不是集合则抛出异常.
     *
     * @param key
     * @param value
     */
    void sAdd(K key, V value);

    /**
     * 添加一个指定的member元素到集合的 key中，并指定时间.
     * 指定的一个元素member 如果已经在集合key中存在则忽略.
     * 如果集合key 不存在，则新建集合key,并添加member元素到集合key中.
     * 如果key 的类型不是集合则抛出异常.
     *
     * @param key
     * @param value
     * @param time
     */
    void sAdd(K key, V value, int time);

    /**
     * 从存储在key的集合中移除并返回一个随机元素.
     *
     * @param key
     * @return 被删除的元素，或者当key不存在时返回null。
     */
    V sPop(K key);

    /**
     * 返回key集合所有的元素.
     *
     * @param key
     * @return 返回key集合所有的元素.
     */
    Set<V> sMembers(K key);

    /**
     * 确定一个给定的值是否为集合的成员.
     *
     * @param key
     * @param value
     * @return 如果member元素是集合key的成员，则返回true; 不是集合key的成员，或者集合key不存在，则返回false
     */
    Boolean sIsMember(K key, V value);

    /**
     * 在key集合中移除指定的元素.
     * 如果指定的元素不是key集合中的元素则忽略 如果key集合不存在则被视为一个空的集合，该命令返回0.
     * 如果key的类型不是一个集合,则返回错误.
     *
     * @param key
     * @param value
     * @return 从集合中移除元素的个数，不包括不存在的成员.
     */
    Long sRem(K key, V value);

    /**
     * 添加一个指定的(value, score)二元组到相应key的zset中
     * @param key
     * @param value
     * @param score
     * @return
     */
    Boolean zAdd(K key, V value, Long score);

    /**
     * 添加一个指定的(value, score)二元组到相应key的zset中，并设置过期时间
     * @param key
     * @param value
     * @param score
     * @param time
     * @return
     */
    Boolean zAdd(K key, V value, Long score, int time);

    /**
     * 获取相应key的zset总长度
     * @param key
     * @return
     */
    Integer zLenCount(K key);

    /**
     * 获取相应key的zset中score在start-end区间内的长度
     * @param key
     * @param start
     * @param end
     * @return
     */
    Integer zLenCountBetween(K key, Long start, Long end);

    /**
     * 获取范围内的集合
     * */
    Set zGet(K key, double start, double end);

    /**
     * 删除zset的key
     * @param key
     * @param value
     * @return
     */
    Long zRem(Object key, Object value);

    Double zIncrScore(K key, V value, double delta);
}
