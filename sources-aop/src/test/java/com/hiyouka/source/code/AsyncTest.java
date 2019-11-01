package com.hiyouka.source.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
//@Async
@Component
public class AsyncTest {

    private Logger logger = LoggerFactory.getLogger(AsyncTest.class);

//    @ConnectionHolderOperation(value = ConnectionHolderOperation.HolderAction.BIND)
    @Transactional
    public void exceptionAsync(){
        logger.info(Thread.currentThread().getName());
//        throw new RuntimeException(Thread.currentThread().getName() + " throw a exception");
    }

}