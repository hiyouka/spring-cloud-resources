package com.hiyouka.sources.config;

import com.hiyouka.sources.util.ClassUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author hiyouka
 * Date: 2019/1/23
 * @since JDK 1.8
 */
@Configuration
public class BeanConfig {

//    @Bean
//    public TestBeanDefinitionRegistryPostProcessor testBeanDefinitionRegistryPostProcessor(){
//        return new TestBeanDefinitionRegistryPostProcessor();
//    }

    @Bean
    @Qualifier
    @Primary
    public ClassUtils classUtils(){
        return new ClassUtils();
    }

}