package com.hiyouka.sources.core.processor;

import org.springframework.beans.BeansException;

/**
 * @author hiyouka
 * Date: 2019/1/29
 * @since JDK 1.8
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

}