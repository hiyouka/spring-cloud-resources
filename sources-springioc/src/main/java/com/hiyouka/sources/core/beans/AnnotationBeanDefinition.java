package com.hiyouka.sources.core.beans;

import java.util.Set;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
public interface AnnotationBeanDefinition  {


    Set<String> getAnnotationTypes();


    boolean hasAnnotation(String annotationName);


    void addAnnotionType(String name);

}