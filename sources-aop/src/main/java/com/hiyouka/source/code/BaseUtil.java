package com.hiyouka.source.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class BaseUtil {

    public static Logger getLogger(Class<?> clazz){
        return LoggerFactory.getLogger(clazz);
    }

}