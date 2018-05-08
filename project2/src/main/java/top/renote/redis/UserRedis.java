package top.renote.redis;

import org.springframework.stereotype.Service;
import top.renote.model.User;

import java.util.concurrent.TimeUnit;

/**
 * Created by Joder_X on 2018/3/11.
 */
@Service
public class UserRedis extends ObjectAbstractRedis<User> {

    @Override
    public void add(User user) {
        redisTemplate.opsForValue().set("user:"+user.getAccount(),user);
    }

    /**
     *
     * @param user
     * @param expire hour
     */
    @Override
    public void add(User user, int expire) {
        redisTemplate.opsForValue().set("user:"+user.getAccount(),user,expire, TimeUnit.HOURS);
    }

    @Override
    public void add(User user, int expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set("user:"+user.getAccount(),user,expire, timeUnit);
    }

    @Override
    public User get(String account) {
        return (User) redisTemplate.boundValueOps("user:"+account).get();
    }

    @Override
    public void delete(String account) {
        redisTemplate.delete("user:"+account);
    }
}
