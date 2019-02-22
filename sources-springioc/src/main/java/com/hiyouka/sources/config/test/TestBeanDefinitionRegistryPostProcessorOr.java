package com.hiyouka.sources.config.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * Date: 2019/2/19
 * @since JDK 1.8
 */

@Component
public class TestBeanDefinitionRegistryPostProcessorOr implements BeanDefinitionRegistryPostProcessor,Ordered {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("TestBeanDefinitionRegistryPostProcessorOr ..................");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("TestBeanDefinitionRegistryPostProcessorOr ..................");
    }


    @Override
    public int getOrder() {
        return 8;
    }
}