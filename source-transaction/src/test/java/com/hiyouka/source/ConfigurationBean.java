package com.hiyouka.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationBean {

    Logger logger = LoggerFactory.getLogger(ConfigurationBean.class);

    @Autowired
    void setSys(){
        logger.info("spring autowired method run ......");
    }

}