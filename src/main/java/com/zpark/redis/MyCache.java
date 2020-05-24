package com.zpark.redis;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;
//重写mybatis二级缓存
public class MyCache implements Cache {
    private String id;

    public MyCache(String id){
        this.id = id;
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object o, Object o1) {
        RedisTemplate redisTemplate = (RedisTemplate) MyApplicationContext.getBeanByName("redisTemplate");
        redisTemplate.opsForHash().put(id,o,o1);
    }

    @Override
    public Object getObject(Object o) {
        RedisTemplate redisTemplate = (RedisTemplate) MyApplicationContext.getBeanByName("redisTemplate");
        Object o1 = redisTemplate.opsForHash().get(id, o);
        return o1;
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    public void clear() {
        RedisTemplate redisTemplate = (RedisTemplate) MyApplicationContext.getBeanByName("redisTemplate");
        redisTemplate.delete(id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
