package com.hiyouka.sources.core.annocation;

import com.hiyouka.sources.core.beans.definition.BeanDefinition;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default BeanDefinition.SCOPE_SINGLETON;

}
