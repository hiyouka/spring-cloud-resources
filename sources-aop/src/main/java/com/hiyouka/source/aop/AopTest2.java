package com.hiyouka.source.aop;

import com.hiyouka.source.code.BaseUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Component
public class AopTest2 {

    private Logger logger = BaseUtil.getLogger(getClass());

    public void testIntercept(){
        logger.info("testIntercept execute");
    }

}