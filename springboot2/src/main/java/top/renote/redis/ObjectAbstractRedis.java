package top.renote.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by Joder_X on 2018/3/11.
 */
public abstract class ObjectAbstractRedis<T> implements ObjectInterfaceRedis<T> {
    @Autowired
    protected RedisTemplate<String,Object> redisTemplate;

    @Override
    public void add(T object) {

    }

    @Override
    public void add(T object, int expire) {

    }

    @Override
    public T get(String s) {
        return null;
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void delete(T obj) {

    }

    @Override
    public boolean isExist(String key, T obj) {
        return false;
    }

    @Override
    public void add(T object, int expire, TimeUnit timeUnit) {

    }
}
