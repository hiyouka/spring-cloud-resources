package com.hiyouka.source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@SpringBootApplication
//TransactionAutoConfiguration 自动配置类已经加入了该注解，所以这边可以不配置 AopAutoConfiguration 除非想exposeProxy = true（）
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableAsync
public class AopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class);
    }

}