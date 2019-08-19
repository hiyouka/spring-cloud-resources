package com.hiyouka.source.aop;

import com.hiyouka.source.code.BaseUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Aspect
@Component
public class AopTest {

    Logger logger = BaseUtil.getLogger(getClass());

    @Before("@annotation(com.hiyouka.source.annotation.BeforeAop)")
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        logger.info("before method execute : " + methodSignature.getMethod().getName());
    }

    @After("@annotation(com.hiyouka.source.annotation.AfterAop)")
    public void after(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        logger.info("before method execute : " + methodSignature.getMethod().getName());
    }


}