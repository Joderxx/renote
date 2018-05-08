package top.renote.util.digestUtil;

import com.github.pagehelper.util.StringUtil;

/**
 * Created by Joder_X on 2018/2/9.
 */
public class MyStringUtil {
    public static String arrayToString(String[] s){
        if (s==null||s.length==0)return null;
        StringBuffer sb = new StringBuffer();
        for (String e:s){
            sb.append(e);
            sb.append(",");
        }
        return sb.toString();
    }

    public static String[] stringToArray(String s){
        if (StringUtil.isEmpty(s))return null;
        return s.trim().split(",");
    }

    /**
     * 截取前size个字符
     * @param s
     * @param size
     * @return
     */
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
