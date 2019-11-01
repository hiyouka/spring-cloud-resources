package com.hiyouka.source.code;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Configuration
public class BeanConfig {

    @Bean("aopTest_A")
    public AopTest aopTest(){
        return new AopTest(new AsyncTest());
    }


}