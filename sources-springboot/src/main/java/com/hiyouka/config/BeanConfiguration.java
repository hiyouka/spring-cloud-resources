package com.hiyouka.config;

import com.hiyouka.code.ServiceImpl2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Configuration
@hiyouka.seedframework.context.annotation.Configuration
public class BeanConfiguration {



    @Bean
    @hiyouka.seedframework.beans.annotation.Bean
    public ServiceImpl2<String,Object> serviceImpl2ofString(){
        return new ServiceImpl2<>();
    }



}