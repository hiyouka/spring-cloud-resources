package com.hiyouka.sources.demo.seedframework.context;

import hiyouka.seedframework.beans.annotation.Bean;
import hiyouka.seedframework.beans.annotation.Lazy;
import hiyouka.seedframework.beans.annotation.Primary;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class BeanConfig {

    @Bean("innerBeanClass")
    @Lazy
    @Primary
    public BeanClass beanClass(){
        return new BeanClass();
    }


}