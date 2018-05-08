package top.renote.util.python;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by Joder_X on 2018/2/21.
 */
@Component
public class TextClassification {

    private final Runtime runtime = Runtime.getRuntime();
    @Value("${python.env}")
    private String python;
    @Value("${python.path}")
    private String path;
    @Value("${python.classification}")
    private String classifypy;

    private String getTag(Long noteId) throws IOException, InterruptedException {
        Process pc = runtime.exec(new String[]{python,path+classifypy,
                "--clazz=classification","--noteId="+noteId});
        pc.waitFor();
        BufferedReader br = new BufferedReader(new InputStreamReader(pc.getInputStream(),"utf-8")); ///todo  utf-8?gbk
        String s = br.readLine();
        br.close();
        pc.destroy();
        return s;
    }

    /**
     *
     * @param noteId 笔记ID
     *
     * @return
     */
    public String tag(Long noteId) throws IOException, InterruptedException {
        return getTag(noteId);
    }

}
