package com.migration.demo.redis;

import cn.hutool.core.collection.CollectionUtil;
import com.shaoming.entity.User;
import com.shaoming.util.JacksonUtil;
import com.shaoming.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest // springboot的单元测试类的注解
public class TestRedis {
    @Autowired
    private Jedis jedis;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * jedis的测试
     */
    // 把数据存入redis
    @Test
    public void testSaveDataByRedis() {
        jedis.set("a", "aaa");
    }

    //从redis中取出数据
    @Test
    public void testGetDataByRedis() {
        String string = jedis.get("a");
        System.out.println(string);
    }

    // 把json数据存入redis
    @Test
    public void testSaveJsonDataToRedis() {
        User user = new User();
        user.setAge(18);
        user.setName("测试的name的值");
        ArrayList<User> userList = CollectionUtil.newArrayList(user, user, user);
        String userListJson = JacksonUtil.obj2String(userList);
        jedis.set("userList", userListJson);
    }

    // 从redis数据库获取json数据
    @Test
    public void testGetJsonDataFromRedis() {
        String json = jedis.get("userList");
        System.out.println(json);
        List<User> userList = JacksonUtil.string2Obj(json, ArrayList.class, User.class);
        System.out.println(userList);
    }

    @Test
    public void testReisPing() {
        String ping = jedis.ping();
        System.out.println(ping);
    }

    /**
     * 操作StringRedisTemplate
     */
    // 操作string
    @Test
    public void testStringRedisTemplateSetString() {
        User user = new User();
        user.setAge(18);
        user.setName("测试的name的值");
        ArrayList<User> userList = CollectionUtil.newArrayList(user, user, user);
        String userListJson = JacksonUtil.obj2String(userList);
        stringRedisTemplate.opsForValue().set("userList", userListJson);
    }

    @Test
    public void testStringRedisTemplateGetString() {
        String json = stringRedisTemplate.opsForValue().get("userList");
        System.out.println(json);
        List<User> userList = JacksonUtil.string2Obj(json, ArrayList.class, User.class);
        System.out.println(userList);
    }

    // 操作hash
    @Test
    public void testStringRedisTemplateSetHash() {
        User user = new User();
        user.setAge(18);
        user.setName("测试的name的值");
        ArrayList<User> userList = CollectionUtil.newArrayList(user, user, user);
        String userListJson = JacksonUtil.obj2String(userList);
        stringRedisTemplate.opsForHash().put("userListKey", "userList", userListJson);
    }

    @Test
    public void testStringRedisTemplateGetHash() {
        String json = (String) stringRedisTemplate.opsForHash().get("userListKey", "userList");
        System.out.println(json);
        List<User> userList = JacksonUtil.string2Obj(json, ArrayList.class, User.class);
        System.out.println(userList);
    }

    /**
     * 操作RedisTemplate
     */
    // 使用的是Jackson2JsonredisSerializer
    @Test
    public void testRedisTemplateStringSet() {
        User user = new User();
        user.setAge(18);
        user.setName("测试的name的值");
        ArrayList<User> userList = CollectionUtil.newArrayList(user, user, user);
//		String userListJson = JacksonUtil.obj2String(userList);
        /**
         * RedisTemplate的k-v都是存放的对象
         */
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.opsForValue().set("userList", userList);
    }

    @Test
    public void testRedisTemplateStringGet() {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        Object userList = redisTemplate.opsForValue().get("userList");
        System.out.println(userList);
    }

    // 使用JdkSerializationRedisSerializer
    @Test
    public void testRedisTemplateStringSetByJdkSerializationRedisSerializer() {
        User user = new User();
        user.setAge(18);
        user.setName("测试的name的值");
        ArrayList<User> userList = CollectionUtil.newArrayList(user, user, user);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.opsForValue().set("userList", userList);
    }

    @Test
    public void testRedisTemplateStringGetByJdkSerializationRedisSerializer() {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        Object userList = redisTemplate.opsForValue().get("userList");
        System.out.println(userList);
    }

    /**
     * 测试redis的工具类RedisUtil
     */
    @Test
    public void testRedisUtilSet() {
        User user = new User();
        user.setAge(18);
        user.setName("测试的name的值");
        ArrayList<User> userList = CollectionUtil.newArrayList(user, user, user);
        redisUtil.set("userList", userList, 10);
    }
}


