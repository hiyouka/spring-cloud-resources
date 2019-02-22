package com.hiyouka.sources.core.annotation;

import com.hiyouka.sources.core.beans.definition.BeanDefinition;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default BeanDefinition.SCOPE_SINGLETON;

}
