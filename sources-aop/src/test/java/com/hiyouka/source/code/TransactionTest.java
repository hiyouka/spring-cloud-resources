package com.hiyouka.source.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Component
public class TransactionTest {
    private static Logger logger = LoggerFactory.getLogger(TransactionTest.class);

    @Transactional
    public void testTransaction(){
        logger.info("testTransaction");
    }

}