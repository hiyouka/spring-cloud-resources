package com.hiyouka.sources.config;

import com.hiyouka.sources.util.ClassUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
//    @Conditional(OnBean.class)
    public ClassUtils classUtils(){
        return new ClassUtils();
    }

}