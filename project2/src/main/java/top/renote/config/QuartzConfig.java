package top.renote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import top.renote.train.TrainModel;

/**
 * Created by Joder_X on 2018/3/15.
 */
@Configuration
@Component
@EnableScheduling
public class QuartzConfig {

    @Autowired
    private TrainModel trainModel;

    @Scheduled(cron = "0 0 1 * * ?")//每日1点训练
    public void scheduled(){
        trainModel.train();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        return bean;
    }
}


