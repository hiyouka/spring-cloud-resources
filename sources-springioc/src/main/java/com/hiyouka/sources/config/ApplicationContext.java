package com.hiyouka.sources.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * Date: 2019/1/28
 * @since JDK 1.8
 */
@Component
public class ApplicationContext implements ApplicationContextAware {

    private static org.springframework.context.ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext .......");
        this.applicationContext = applicationContext;
    }

    public  static org.springframework.context.ApplicationContext getApplication(){
        return applicationContext;
    }


}