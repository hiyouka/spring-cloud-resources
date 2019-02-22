package com.hiyouka.sources.config.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
@Component
public class TestBeanPostProcessor implements BeanPostProcessor {


    @Autowired
    private AutowiredAnnotationBeanPostProcessor processor;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ClassUtils){
            System.out.println(((ClassUtils) bean).getTestBeanPostProcessor() + ">>>");
        }
        System.out.println("postProcessBeforeInitialization ......" + bean.getClass());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization ......" + bean.getClass());
        return bean;
    }
}