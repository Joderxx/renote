package top.renote.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.renote.model.User;

import java.util.concurrent.TimeUnit;

/**
 * Created by Joder_X on 2018/3/11.
 */
@Service
@Slf4j
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
        Object obj = redisTemplate.boundValueOps("user:"+account).get();
        return obj==null?null:(User)obj;
    }

    @Override
    public void delete(String account) {
        redisTemplate.delete("user:"+account);
    }
}
