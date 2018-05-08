package top.renote.util.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Joder_X on 2018/2/28.
 */
@Component
public class FilesUtil {

    @Autowired
    private RedisTemplate<String,String> template;

    /**
     *
     * @param noteId
     * @param text
     * @return
     * @throws IOException
     */
    public void writeFile(Long noteId,String text) throws IOException {
        template.opsForValue().set("note:tmp:"+noteId,text);
    }

    public void deleteFile(Long noteId){
        template.delete("note:tmp:"+noteId);
    }
}
