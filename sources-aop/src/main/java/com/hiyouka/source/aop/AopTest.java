package com.hiyouka.source.aop;

import com.hiyouka.source.code.BaseUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Configuration
@ConditionalOnClass({AnnotationAwareAspectJAutoProxyCreator.class})
public class AopTest{

    Logger logger = BaseUtil.getLogger(getClass());

    @Configuration
    @Aspect
    class BeforeAspect implements Ordered{

        @Before("@annotation(com.hiyouka.source.annotation.BeforeAop)")
        public void before(JoinPoint joinPoint){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            logger.info("before method execute : " + methodSignature.getMethod().getName());
        }

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE + 1;
        }
    }

    @Configuration
    @Aspect
    class AfterAspect implements Ordered{

        @After("@annotation(com.hiyouka.source.annotation.AfterAop)")
        public void after(JoinPoint joinPoint){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            logger.info("before method execute : " + methodSignature.getMethod().getName());
        }

        @Override
        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE;
        }
    }

}