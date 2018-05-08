package top.renote.train;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Joder_X on 2018/3/15.
 */
@Component
@Slf4j
public class TrainModel {
    @Value("${python.vm}")
    private String python;
    @Value("${python.dir}")
    private String dir;
    @Value("${python.trainfile}")
    private String pythonfile;

    public void train(){
        Process process = null;
        try {
            log.info("模型开始训练....");
            process = Runtime.getRuntime().exec(new String[]{python,dir+pythonfile});
        } catch (IOException e) {
            log.info("模型训练异常...");
        } finally {
            process.destroy();
        }
    }

}
