package top.renote.util.python;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joder_X on 2018/4/14.
 */
@Component
@Slf4j
public class Search {
    private final Runtime runtime = Runtime.getRuntime();
    @Value("${python.env}")
    private String python;
    @Value("${python.path}")
    private String path;
    @Value("${python.search}")
    private String searchpy;

    public Long[] search(String keyword)  {
        Process pc = null;
        try {
            pc = runtime.exec(new String[]{python,path+searchpy,
                    "--keyword="+keyword});
            pc.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(pc.getInputStream(),"gbk"));
            String s = br.readLine();
            br.close();
            pc.destroy();
            String[] ss = s.split(" ");
            Long[] arr = new Long[ss.length];
            for (int i=0;i<ss.length;i++){
                arr[i] = Long.parseLong(ss[i]);
            }
            return arr;
        } catch (IOException e) {
            log.info("search IOException,keyword:{}",keyword);
        } catch (InterruptedException e) {
            log.info("search InterruptedException,keyword:{}",keyword);
        }
        return null;
    }
}
