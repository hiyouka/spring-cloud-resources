package com.hiyouka.sources.core.registry;


import com.hiyouka.sources.core.beans.definition.BeanDefinition;
import com.hiyouka.sources.exception.BeanDefinitionStoreCoreException;

/**
 * @author hiyouka
 * Date: 2019/1/29
 * @since JDK 1.8
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
            throws BeanDefinitionStoreCoreException;

}