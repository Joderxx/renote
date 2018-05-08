package top.renote.util.randomImage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Joder_X on 2017/10/13.
 *
 * ImageFactory根据每个sessionId产生对应的验证码，保存在map中
 */
public class ImageFactory {

    private Map<String,String> codeFactory = new ConcurrentHashMap<String, String>();;
    private static ImageFactory imageFactory = new ImageFactory();;
    private ImageFactory(){}

    public static ImageFactory getInstance(){
        return imageFactory;
    }

    public Map<String, String> getCodeFactory() {
        return codeFactory;
    }

}
