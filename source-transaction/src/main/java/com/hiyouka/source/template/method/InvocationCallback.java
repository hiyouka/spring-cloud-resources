package com.hiyouka.source.template.method;

import com.hiyouka.source.code.TestClass;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@FunctionalInterface
public interface InvocationCallback {

    TestClass proceedWithInvocation() throws Throwable;

}