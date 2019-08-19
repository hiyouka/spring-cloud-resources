package com.hiyouka.source.template.method;

import java.lang.reflect.Method;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@FunctionalInterface
public interface MethodCallback {

    Method proceedWithInvocation() throws Throwable;

}