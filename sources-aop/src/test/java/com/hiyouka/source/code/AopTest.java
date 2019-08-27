package com.hiyouka.source.code;

import com.hiyouka.source.annotation.AfterAop;
import com.hiyouka.source.annotation.BeforeAop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Component("test_aop")
public class AopTest {

    @Autowired
    private AsyncTest asyncTest;

    private Logger logger = LoggerFactory.getLogger(AopTest.class);

    @BeforeAop
    @AfterAop
    @Transactional
//    @ConnectionHolderOperation
    public void testBefore(){
        logger.info(Thread.currentThread().getName());
//        asyncTest.exceptionAsync();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Future<Object> objectFuture = asyncTest.exceptionAsync();
//        objectFuture.get();
//        this.getClass().cast(AopContext.currentProxy()).innerTestBefore();
//        innerTestBefore();
    }

//    @AfterAop
    public void testExpose(){

    }

//    @BeforeAop
    @Async
    public void innerTestBefore(){
        logger.info(Thread.currentThread().getName());
        throw new RuntimeException(Thread.currentThread().getName() + " throw a exception");
    }

}