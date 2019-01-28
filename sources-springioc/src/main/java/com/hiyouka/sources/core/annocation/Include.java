package com.hiyouka.sources.core.annocation;

import java.lang.annotation.*;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Include {

    String value() default "";

}