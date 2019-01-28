package com.hiyouka.sources.core.beans.annocation;

import com.hiyouka.sources.core.beans.definition.BeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author hiyouka
 * Date: 2019/1/28
 * @since JDK 1.8
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {

    AnnotationMetadata getMetadata();

}