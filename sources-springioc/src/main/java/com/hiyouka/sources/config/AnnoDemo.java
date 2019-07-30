package com.hiyouka.sources.config;

import com.hiyouka.sources.config.test.PClass;
import hiyouka.seedframework.beans.annotation.Component;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Component
public class AnnoDemo extends PClass{

    @Value("${token.encrypt.salt}")
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}