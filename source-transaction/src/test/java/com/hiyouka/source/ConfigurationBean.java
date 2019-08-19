package com.hiyouka.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class ConfigurationBean {

    public static void main(String[] args) throws NoSuchMethodException {
        Method setSys = Configuration.class.getDeclaredMethod("setSys");
        System.out.println(setSys.getDeclaringClass());
    }

    Logger logger = LoggerFactory.getLogger(ConfigurationBean.class);

    @Autowired
    void setSys(){
        logger.info("spring autowired method run ......");
    }

}