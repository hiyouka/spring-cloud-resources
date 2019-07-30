package com.hiyouka.sources.config.test;

import com.hiyouka.sources.core.annotation.Bean;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class PClass {

    @Bean
    public BeanClass beanClass(){
        return new BeanClass();
    }

}