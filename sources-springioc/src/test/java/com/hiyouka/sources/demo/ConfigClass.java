package com.hiyouka.sources.demo;

import com.hiyouka.sources.core.annotation.Bean;
import hiyouka.seedframework.context.annotation.ComponentScan;
import hiyouka.seedframework.context.annotation.Configuration;
import org.springframework.beans.BeanUtils;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Configuration
@ComponentScan("com.hiyouka.sources.demo")
public class ConfigClass {


    @Bean
    public BeanUtils beanUtils(){
        return new BeanUtils() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }

}