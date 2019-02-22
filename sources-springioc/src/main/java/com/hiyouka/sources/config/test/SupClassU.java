package com.hiyouka.sources.config.test;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * Date: 2019/1/28
 * @since JDK 1.8
 */
@Component
public class SupClassU implements InitializingBean{

    @Autowired
    private TeAuClass teAuClass;

    @Value("${token.encrypt.salt}")
    private String value;


    private String regst;

    public String getRegst() {
        return regst;
    }

    public void setRegst(String regst) {
        this.regst = regst;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("SupClassU afterPropertiesSet ......");
    }

}