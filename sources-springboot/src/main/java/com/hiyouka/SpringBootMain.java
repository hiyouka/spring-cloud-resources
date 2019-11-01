package com.hiyouka;

import com.hiyouka.config.AopConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@EnableFeignClients
@Import(AopConfig.class)
@ComponentScan({"com.hiyouka.code","com.hiyouka.config"})

// for seed framework
@hiyouka.seedframework.context.annotation.ComponentScan({"com.hiyouka.code","com.hiyouka.config"})
public class SpringBootMain {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class);
    }

}