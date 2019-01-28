package com.hiyouka.sources.core.annocation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Primary {
    boolean value() default true;
}
