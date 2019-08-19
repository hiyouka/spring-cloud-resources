package com.hiyouka.source.code;

import com.hiyouka.source.annotation.AfterAop;
import com.hiyouka.source.annotation.BeforeAop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Component("test_aop")
@Transactional
public class AopTest {

    Logger logger = LoggerFactory.getLogger(AopTest.class);

    @BeforeAop
    public void testBefore(){
        logger.info(Thread.currentThread().getName());
        this.getClass().cast(AopContext.currentProxy()).innerTestBefore();
//        innerTestBefore();
    }

    @AfterAop
    public void testExpose(){

    }

//    @BeforeAop
    @Async
    public void innerTestBefore(){
        logger.info(Thread.currentThread().getName());
    }

}