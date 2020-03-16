//package com.hiyouka.seed.aspect;
//
//import com.hiyouka.seed.bean.Test2;
//import com.hiyouka.seed.bean.TestFather1;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import seed.seedframework.beans.annotation.Component;
//import seed.seedframework.core.annotation.Order;
//
///**
// * @author hiyouka
// * @since JDK 1.8
// */
//@Component
//@Aspect
//public class PointTest {
//
////    @Pointcut(value = "execution(* com.hiyouka.seed.bean..*(..))")
////    private void point(){};
//
//
//    @Order(5)
//    @Before(value = "com.hiyouka.seed.aspect.PointcutAssemble.point() || @annotation(com.hiyouka.seed.aspect.AopAnnotation)",argNames = "join")
//    public void before(JoinPoint join){
//        System.out.println(">>>>>>>>>>>>>>>>before ");
//    }
//
//    @Order(4)
//    @AfterThrowing(value = "com.hiyouka.seed.aspect.PointcutAssemble.point() || @annotation(com.hiyouka.seed.aspect.AopAnnotation)" , throwing = "e",argNames = "e,joinPoint")
//    public void afterThrow(Throwable e, JoinPoint joinPoint){
//        System.out.println(">>>>>>>>>>>>>afterThrow" + e);
//    }
//
//    @Order(3)
//    @After("com.hiyouka.seed.aspect.PointcutAssemble.point() || @annotation(com.hiyouka.seed.aspect.AopAnnotation)")
//    public void after(JoinPoint joinPoint){
//        System.out.println(">>>>>>>>>>>>>>>after");
//    }
//
//    @Order(2)
//    @AfterReturning(value = "@annotation(com.hiyouka.seed.aspect.AopAnnotation)", returning = "ret")
//    public void afterReturn(TestFather1<Test2,Test2> ret, JoinPoint joinPoint){
//        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//        Object[] args = joinPoint.getArgs();
//        System.out.println(">>>>>>>>>>>>>after Return");
//    }
//
//    @Order(1)
//    @Around(value = "com.hiyouka.seed.aspect.PointcutAssemble.point() || @annotation(com.hiyouka.seed.aspect.AopAnnotation)")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println(">>>>>>>>>>>>>>around before");
//        Object process = joinPoint.proceed();
//        System.out.println(">>>>>>>>>>>>>>>> around after");
//        return process;
//    }
//
//
//}