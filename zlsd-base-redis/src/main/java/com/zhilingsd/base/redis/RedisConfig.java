package com.zhilingsd.base.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${spring.redis.cluster.max}")
    private String clusterMax;
    @Value("${spring.redis.password}")
    private String password;

//    spring.redis.database = 0
//    spring.redis.cluster.nodes = 192.168.14.221:19001,192.168.14.221:19002,192.168.14.221:19003
//    spring.redis.cluster.max-redirects = 5
//    spring.redis.password=zlsd2019


    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //poolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        JedisConnectionFactory jedisConnectionFactory =
                new JedisConnectionFactory(redisClusterConfiguration,jedisPoolConfig);

        //设置默认使用的数据库
        jedisConnectionFactory.setDatabase(database);
        //设置密码
        jedisConnectionFactory.setPassword(password);
        return jedisConnectionFactory;
    }

//    @Bean(name = "redisTemplate1")
//    public RedisTemplate<String, Object> redisTemplateObject() throws Exception {
//        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();
//        redisTemplateObject.setConnectionFactory(redisConnectionFactory());
//        setSerializer(redisTemplateObject);
//        redisTemplateObject.afterPropertiesSet();
//        return redisTemplateObject;
//    }





    @Bean(name = "StringRedisSerializerRedisTemplate")
    public RedisTemplate<String, Object> stringRedisSerializerRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

//    /**
//     * jedis连接工厂
//     * @return
//     */
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        JedisConnectionFactory jedisConnectionFactory =
//                new JedisConnectionFactory(redisClusterConfiguration,jedisPoolConfig());
//
//        //设置默认使用的数据库
//        jedisConnectionFactory.setDatabase(database);
//        //设置密码
//        jedisConnectionFactory.setPassword(password);
//
//        return jedisConnectionFactory;
//    }

//    /**
//     * 连接池配置信息
//     * @return
//     */
//    @Bean
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(maxActive);
//        jedisPoolConfig.setMinIdle(minIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWait);
//        return jedisPoolConfig;
//    }

}
