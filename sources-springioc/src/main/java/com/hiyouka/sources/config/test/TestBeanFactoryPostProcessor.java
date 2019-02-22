package com.hiyouka.sources.config.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */

@Component
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory .................");
        beanFactory.getBeanDefinitionCount();
        beanFactory.getBeanDefinitionNames();
    }
}