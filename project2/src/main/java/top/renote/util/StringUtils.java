package top.renote.util;

/**
 * Created by Joder_X on 2018/3/21.
 */
public class StringUtils {

    public static String cutString(String s,int size){
        if (s!=null)
            s = cuthtml(s);
        if (s!=null&&s.length()>size)
            return s.substring(0,size)+"...";
        return s;
    }
    public static String cuthtml(String s){
        return s.replaceAll("<[^>]+>","");
    }
}
