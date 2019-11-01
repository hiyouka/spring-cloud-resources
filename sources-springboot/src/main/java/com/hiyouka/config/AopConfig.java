package com.hiyouka.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Aspect
@Component
public class AopConfig {

    private Logger logger = LoggerFactory.getLogger(AopConfig.class);

    @Pointcut("execution(public * com.hiyouka..*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint points){
        logger.info("before aop start");
        System.out.println(points.toString());
        logger.info("before aop end");
    }

    @AfterReturning(value = "pointCut()", returning = "o")
    public void after(Object o){
        logger.info("after aop start");
        logger.info("after aop end");
    }


}