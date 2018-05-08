package top.renote.redis;

import org.springframework.stereotype.Service;
import top.renote.model.RegisterUser;

import java.util.concurrent.TimeUnit;

/**
 * Created by Joder_X on 2018/3/11.
 */
@Service
public class RegisterRedis extends ObjectAbstractRedis<RegisterUser> {


    @Override
    public void  add(RegisterUser object) {
        add(object,30,TimeUnit.MINUTES);
    }

    @Override
    public RegisterUser get(String s) {
        Object obj = redisTemplate.boundValueOps("reg:"+s).get();
        return obj==null?null:(RegisterUser)obj;
    }

    @Override
    public void delete(RegisterUser s) {
        redisTemplate.delete("reg:"+s.getAccount());
    }

    @Override
    public void add(RegisterUser object, int expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set("reg:"+object.getAccount(),object,expire,timeUnit);
    }
}
