package com.hiyouka.sources.core.factory;


import com.hiyouka.sources.exception.BeansException;
import com.hiyouka.sources.exception.NoSuchBeanDefinitionException;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author hiyouka
 * Date: 2019/1/29
 * @since JDK 1.8
 */
public interface ListableBeanFactory extends BeanFactory {

    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(Class<?> type);

    /**
     * @param type
     * @param includeNonSingletons  is single Object
     * @param allowEagerInit    is already create
     * @return:java.lang.String[]
     * @Date: 2019/1/29
     */
    String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit);


    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType);


    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException;


    <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException;

}