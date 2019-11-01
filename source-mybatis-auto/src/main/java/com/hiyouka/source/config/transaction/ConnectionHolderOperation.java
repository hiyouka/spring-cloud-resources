package com.hiyouka.source.config.transaction;

import java.lang.annotation.*;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ConnectionHolderOperation {

    HolderAction value() default HolderAction.CACHE;

    enum  HolderAction{
        CACHE,BIND
    }

}