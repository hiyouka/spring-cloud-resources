package com.hiyouka.sources.demo;


import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Test {

    /**
     *
     * 注解小知识点: 当自定义注解的时候 加入 @Inherited 注解则 当父类上标有该注解时
     * 他的子类都会继承这个注解。
     *  getDeclaredAnnotations 方法不会获取到该类的继承注解
     *  getAnnotations 方法可以获取到来自父类的注解
     *
     */

}
