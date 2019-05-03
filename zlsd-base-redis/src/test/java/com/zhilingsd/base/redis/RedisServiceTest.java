package com.zhilingsd.base.redis;

import com.zhilingsd.base.cache.Cache;
import com.zhilingsd.base.common.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class RedisServiceTest {

    private final static Logger log = LoggerFactory.getLogger(RedisServiceTest.class);

    private final static String KEY = "redis_test_key";
    private final static String VALUE = "test_test_test_测试_测试_测试";
    private final static String KEY1 = "redis_test_key1";
    private final static String VALUE1 = "test_test_test_测试_测试_测试_2_2_2_2";
    private final static String FIELD = "field";
    private final static String FIELD1 = "field1";
    private final static int EXPIRE_TIME = 60;

    @Autowired
    Cache<String,Serializable> redisService;

    @Test
    public void set() {
        doPrint("set", "begin");
        redisService.set(KEY, VALUE);
        doPrint("set", "end");
    }

    @Test
    public void set1() {
        doPrint("set1", "begin");
        redisService.set(KEY1, VALUE1, -1);
        doPrint("set1", "end");
    }

    @Test
    public void get() throws Exception {
        doPrint("get", "begin");
        log.info("{} = {}", KEY, redisService.get(KEY));
        log.info("{} = {}", KEY1, redisService.get(KEY1));
        doPrint("get", "end");
    }

    @Test
    public void delete() {
        doPrint("delete", "begin");
        redisService.delete(KEY);
        redisService.delete(KEY1);
        doPrint("delete", "end");
    }

    @Test
    public void isKeyExist() {
        doPrint("isKeyExist", "begin");
        log.info("key = {}, isKeyExist = {}", KEY, redisService.isKeyExist(KEY));
        log.info("key = {}, isKeyExist = {}", KEY1, redisService.isKeyExist(KEY1));
        doPrint("isKeyExist", "end");
    }

    private static String setnxKey = "setnxKey";

    @Test
    public void setnx() {
        doPrint("setnx", "begin");
        Boolean bool = redisService.setnx(setnxKey, VALUE);
        log.info("key = {}, return = {}", setnxKey, bool.booleanValue());
        doPrint("setnx", "end");
    }

    private static String setnxKey1 = "setnxKey1";

    @Test
    public void setnx1() {
        doPrint("setnx1", "begin");
        Boolean bool = redisService.setnx(setnxKey1, VALUE1, EXPIRE_TIME);
        log.info("key = {}, return = {}", setnxKey1, bool.booleanValue());
        doPrint("setnx1", "end");
    }

    @Test
    public void expire() {
        doPrint("expire", "begin");
        Boolean bool = redisService.expire(KEY, 10);
        log.info("key = {}, return = {}", KEY, bool.booleanValue());
        doPrint("expire", "end");
    }

    private static String lpushKey = "lpushKey";

    @Test
    public void lpush() {
        doPrint("lpush", "begin");
        Long longObj = redisService.lpush(lpushKey, VALUE);
        log.info("key = {}, return = {}", lpushKey, longObj.longValue());
        doPrint("lpush", "end");
    }

    private static String lpushKey1 = "lpushKey1";

    @Test
    public void lpush1() {
        doPrint("lpush1", "begin");
        Long longObj = redisService.lpush(lpushKey1, VALUE1, EXPIRE_TIME);
        log.info("key = {}, return = {}", lpushKey1, longObj.longValue());
        doPrint("lpush1", "end");
    }

    private static String rpushKey = "rpushKey";
    private static String rpushKey1 = "rpushKey1";

    @Test
    public void rpush() {
        doPrint("rpush", "begin");
        Long longObj = redisService.rpush(rpushKey, VALUE);
        log.info("key = {}, return = {}", rpushKey, longObj.longValue());
        doPrint("rpush", "end");
    }

    @Test
    public void rpush1() {
        doPrint("rpush1", "begin");
        Long longObj = redisService.rpush(rpushKey1, VALUE1, EXPIRE_TIME);
        log.info("key = {}, return = {}", rpushKey1, longObj.longValue());
        doPrint("rpush1", "end");
    }

    @Test
    public void lpop() throws Exception {
        doPrint("lpop", "begin");
        String value = (String)redisService.lpop(lpushKey);
        log.info("key = {}, return = {}", lpushKey, value);
        doPrint("lpop", "end");
    }

    @Test
    public void rpop() throws Exception {
        doPrint("rpop", "begin");
        String value = (String)redisService.rpop(rpushKey);
        log.info("key = {}, return = {}", rpushKey, value);
        doPrint("rpop", "end");
    }

    @Test
    public void lget() {
        doPrint("lget", "begin");
        List<Object> list = redisService.lget(lpushKey1);
        log.info("key = {}, return = {}", lpushKey1, list.toString());
        doPrint("lget", "end");
    }

    @Test
    public void lLength() {
        doPrint("lLength", "begin");
        Long longObj = redisService.lLength(rpushKey1);
        log.info("key = {}, return = {}", rpushKey1, longObj.longValue());
        doPrint("lLength", "end");
    }

    @Test
    public void lremove() {
        doPrint("lremove", "begin");
        Long longObj = redisService.lremove(lpushKey, VALUE);
        log.info("key = {}, return = {}", lpushKey, longObj.longValue());
        doPrint("lremove", "end");
    }

    @Test
    public void hset() {
        doPrint("hset", "begin");
        redisService.hset("hsetTest", FIELD, VALUE);
        doPrint("hset", "end");
    }

    @Test
    public void hset1() {
        doPrint("hset1", "begin");
        redisService.hset("hsetTest1", FIELD1, VALUE1, EXPIRE_TIME);
        doPrint("hset1", "end");
    }

    private static String hsetTest = "hsetTest";

    @Test
    public void hget() {
        doPrint("hget", "begin");
        String value= (String)redisService.hget(hsetTest, FIELD);
        log.info("key = {}, field = {}, value = {}", hsetTest, FIELD, value);
        doPrint("hget", "end");
    }

    @Test
    public void hsetnx() {
        doPrint("hsetnx", "begin");
        Boolean bool = redisService.hsetnx(hsetTest, FIELD, VALUE);
        log.info("key = {}, field = {}, value = {}, return = {}", hsetTest, FIELD, VALUE, bool.booleanValue());
        doPrint("hsetnx", "end");
    }

    @Test
    public void hsetnx1() {
        doPrint("hsetnx1", "begin");
        Boolean bool = redisService.hsetnx(hsetTest, FIELD1, VALUE1, EXPIRE_TIME);
        log.info("key = {}, field = {}, value = {}, return = {}", hsetTest, FIELD1, VALUE1, bool.booleanValue());
        doPrint("hsetnx1", "end");
    }

    @Test
    public void hkeys() {
        doPrint("hkeys", "begin");
        Set<Object> set = redisService.hkeys(hsetTest);
        log.info("key = {}, return = {}", hsetTest, set.toString());
        doPrint("hkeys", "end");
    }

//    @Test
//    public void hmset() {
//        doPrint("hmset", "begin");
//        Map<String, String> map = new HashMap<>();
//        map.put(FIELD, VALUE);
//        map.put(FIELD1, VALUE1);
//        redisService.hmset(hsetTest, map);
//        doPrint("hmset", "end");
//    }

//    @Test
//    public void hmset1() {
//        doPrint("hmset1", "begin");
//        Map<String, String> map = new HashMap<>();
//        map.put(FIELD, VALUE);
//        map.put(FIELD1, VALUE1);
//        redisService.hmset(hsetTest, map, EXPIRE_TIME);
//        doPrint("hmset1", "end");
//    }

    @Test
    public void hmget() {
        doPrint("hmget", "begin");
        List<Serializable> list = redisService.hmget(hsetTest, FIELD, FIELD1);
        log.info("key = {}, field = {}, field1 = {}, return = {}", hsetTest, FIELD, FIELD1, list.toString());
        doPrint("hmget", "end");
    }

    @Test
    public void hdel() {
        doPrint("hdel", "begin");
        Long longObj = redisService.hdel(hsetTest, FIELD, FIELD1);
        log.info("key = {}, field = {}, field1 = {}, return = {}", hsetTest, FIELD, FIELD1, longObj.longValue());
        doPrint("hdel", "end");
    }

    @Test
    public void testSetGetObject() throws Exception {
        Pojo p = new Pojo();
        p.setAge(18);
        p.setName("chen");
        List<String> list = new ArrayList<>();
        p.setAddrs(list);
        list.add("地址1");
        list.add("地址2");
        list.add("地址3");

        Certification c = new Certification();
        c.setCertiCode("certificationCode");
        c.setCertiName("certificationName");
        p.setCertification(c);

        doPrint("testSetObject", "begin");
        log.info("入参：{}", JsonUtils.toJsonString(p));
        redisService.set("p", p);
        Pojo p2 = (Pojo)redisService.get("p");
        log.info("出参：{}", JsonUtils.toJsonString(p2));
        doPrint("testGetObject", "end");

    }


    private void doPrint(String operate, String status) {
        if("begin".equals(status)) {
            System.out.println("--------- " + operate + " begin ---------");
        } else {
            System.out.println("--------- " + operate + " end ---------");
        }
    }


}

class Pojo implements Serializable{
    private String name;
    private int age;
    private List<String> addrs;
    private Certification certification;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getAddrs() {
        return addrs;
    }

    public void setAddrs(List<String> addrs) {
        this.addrs = addrs;
    }

    public Certification getCertification() {
        return certification;
    }

    public void setCertification(Certification certification) {
        this.certification = certification;
    }
}

class Certification implements Serializable{
    private String certiName;
    private String certiCode;

    public String getCertiName() {
        return certiName;
    }

    public void setCertiName(String certiName) {
        this.certiName = certiName;
    }

    public String getCertiCode() {
        return certiCode;
    }

    public void setCertiCode(String certiCode) {
        this.certiCode = certiCode;
    }
}