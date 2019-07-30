package com.hiyouka.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public interface BaseTestInterface {

    default Log getLogger(){
        return LogFactory.getLog(getClass());
    }

}