package top.renote.redis;

import java.util.concurrent.TimeUnit;

/**
 * Created by Joder_X on 2018/3/11.
 */
public interface ObjectInterfaceRedis<T> {

    void add(T object);

    void add(T object,int expire);

    void add(T object, int expire, TimeUnit timeUnit);

    T get(String s);

    void delete(String s);

    void delete(T obj);

    boolean isExist(String key,T obj);


}
