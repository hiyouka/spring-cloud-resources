package com.hiyouka.source.test;

import com.hiyouka.source.code.TestClass;
import com.hiyouka.source.template.method.InvocationCallback;
import com.hiyouka.source.template.method.MethodCallback;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class TransactionTest {

    public static void main(String[] args) throws Throwable {
//        new Thread(() -> System.out.println(Thread.currentThread().getId() + "thread run ...........")).start();
//        new Thread(() -> System.out.println(Thread.currentThread().getId() + "thread run ...........")).start();
//        new Thread(() -> System.out.println(Thread.currentThread().getId() + "thread run ...........")).start();
        new TransactionTest().invoke(new MethodInvocationImpl(TransactionTest.class.getMethod("test")));
//        System.out.println(new Date().getTime());

    }


    public void test(){

        new Thread(() -> System.out.println(123)).start();
//        TransactionFactory

    }

    public void invoke(MethodInvocationImpl methodInvocation) throws Throwable {
        Runnable runnable = methodInvocation::getClassName;
        runnable.run();
        MethodCallback methodCallback = methodInvocation::getMethod;
        InvocationCallback proceed = methodInvocation::proceed;
        methodInvocation.proceed();
        proceed.proceedWithInvocation();
        Method method = methodCallback.proceedWithInvocation();
        methodProcess();
    }

    public Object methodProcess() throws Throwable {
//        Method method = methodCallback.proceedWithInvocation();
        System.out.println("methodProcess invoke");
        return null;
    }


//    public Object invoke(MethodInvocation invocation) throws Throwable {
//        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
//        InvocationCallback proceed = invocation::proceed;
//        Runnable getThis = invocation::getThis;
//        Runnable getStaticPart = invocation::getStaticPart;
//        MethodCallback getMethod = invocation::getMethod;
//        Method method = getMethod.proceedWithInvocation();
//        return invokeWithinTransaction(invocation.getMethod(), targetClass, invocation::proceed);
//    }

    public Object invokeWithinTransaction(Method method, @Nullable Class<?> targetClass,
                                          final InvocationCallback invocation){

        return null;
    }

    protected static class MethodInvocationImpl implements MethodInvocation{

        private Method method;

        public MethodInvocationImpl(Method method){
            this.method = method;
        }

        @Override
        public String getClassName() {
            System.out.println("getClassName invoke");
            return getClass().getName();
        }

        @Override
        public Method getMethod() {
            System.out.println("get method invoke");
            return this.method;
        }

        @Override
        public TestClass proceed() throws Throwable {
            System.out.println("process : " + method.getName());
            return null;
        }

        @Override
        public Object getThis() {
            return null;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return null;
        }
    }

    protected interface MethodInvocation extends Joinpoint{

        String getClassName();

        Method getMethod();
    }

    protected interface Joinpoint {

        TestClass proceed() throws Throwable;

        Object getThis();

        AccessibleObject getStaticPart();

    }


}