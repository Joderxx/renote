package top.renote.util.digestUtil;

import java.util.UUID;

/**
 * Created by Joder_X on 2017/10/12.
 * 产生12为随机数字
 */
public class RandomCode {

    //100000--999999
    public static String randNumber(){
        int i= (int) (Math.random()*900000+100000);
        int j = (int) (Math.random()*9000+1000);
        return i+""+j;
    }

    public static String randString(){
        String s = UUID.randomUUID().toString();
        s = s.replaceAll("-","");
        return s;
    }

}
