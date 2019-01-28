package com.hiyouka.sources.core.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    <T> T getBean(String beanName, Class<T> beanType) throws BeansException;

    Class<?> getType(String beanName) throws BeansException;

    boolean isTypeMatch(String name, Class<?> typeToMatch)  throws NoSuchBeanDefinitionException;

    boolean containsBean(String beanName);

    boolean isSingleton(String beanName) throws NoSuchBeanDefinitionException;
    boolean isPrototype(String beanName) throws NoSuchBeanDefinitionException;
    boolean isLazyInit(String beanName) throws NoSuchBeanDefinitionException;

}