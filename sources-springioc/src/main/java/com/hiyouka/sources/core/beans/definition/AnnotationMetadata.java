package com.hiyouka.sources.core.beans.definition;

import java.util.Map;
import java.util.Set;

/**
 * @author hiyouka
 * Date: 2019/1/28
 * @since JDK 1.8
 */
public interface AnnotationMetadata {

    /**
     * @param
     * @return:java.util.Set<java.lang.String> all annotation full class name
     * @Date: 2019/1/28
     */
    Set<String> getAnnotationTypes();

    /**
     * @param annotationName full annotation class name
     * @return:boolean  if a matching annotation is present
     * @Date: 2019/1/28
     */
    boolean hasAnnotation(String annotationName);

    /**
     * @param annotationName  full annotation class name
     * @return:java.util.Map<java.lang.String,java.lang.Object> the annotation all attributes
     * @Date: 2019/1/28
     */
    Map<String, Object> getAllAnnotationAttributes(String annotationName);


}