package com.hiyouka.source.aop;

import com.hiyouka.source.code.BaseUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Configuration
@ConditionalOnClass({AnnotationAwareAspectJAutoProxyCreator.class})
public class AopTest{

    Logger logger = BaseUtil.getLogger(getClass());

    @Aspect
    @Component
    class BeforeAspect implements Ordered{

//        @Before(value = "execution(com.hiyouka..*Test.class)")
        @Pointcut(value = "within(com.hiyouka.*..service.*)")
        private void point(){
//            System.out.println(">>>>>>>>>>>>>>>> " + joinPoint.toString());
        }


        @Before(value = "@annotation(com.hiyouka.source.annotation.BeforeAop)) || point()")
        private void before(JoinPoint joinPoint){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            logger.info("before method execute : " + methodSignature.getMethod().getName());
        }

        @Override
        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE - 3;
        }

    }

    @Aspect
    @Component
    class AroundAspect implements Ordered{

        @Around("@annotation(com.hiyouka.source.annotation.AroundAop))")
        public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            System.out.println("method around interceptor invoke before ........");
            Object[] args = {"123"};
            Object proceed = joinPoint.proceed();
            System.out.println("method around//            logger.info(\"before method execute : \" + methodSignature.getMethod().getName());\n interceptor invoke after ........");
//            proceed = "777";
            return proceed;
        }

        @Override
        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE - 2;
        }

    }


    @Aspect
    @Component
    class AfterAspect implements Ordered{

        @AfterReturning(value = "@annotation(com.hiyouka.source.annotation.AfterAop),", returning = "map")
        public Object after(JoinPoint joinPoint, Map<String,Object> map){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            logger.info("after method execute : " + methodSignature.getMethod().getName());
            Object returnVal = "666";
            return returnVal;
        }

        @AfterThrowing(value = "@annotation(com.hiyouka.source.annotation.AfterAop)" , throwing = "e")
        public void throwing(){
            logger.info("throwing do " );
        }

        @Override
        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE - 1;
        }
    }

}