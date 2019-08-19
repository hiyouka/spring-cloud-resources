package com.hiyouka.source.code;

import com.hiyouka.source.annotation.BeforeAop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Component("test_aop_2")
public class AopTest2 {

    Logger logger = LoggerFactory.getLogger(AopTest2.class);

    @BeforeAop
    public void testBefore(){
        logger.info(Thread.currentThread().getName());
        this.getClass().cast(AopContext.currentProxy()).innerTestBefore();
//        innerTestBefore();
    }

    public void innerTestBefore(){

    }

}