package com.hiyouka.source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@SpringBootApplication
// TransactionAutoConfiguration 自动配置类已经加入了该注解，所以这边可以不配置 除非想改变 mode
//@EnableTransactionManagement(mode = AdviceMode.PROXY)
public class TransactionMain {

    public static void main(String[] args) {
        SpringApplication.run(TransactionMain.class);
    }

}