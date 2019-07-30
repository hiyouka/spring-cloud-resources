package com.hiyouka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
public class SpringBootMain {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class);
    }

}