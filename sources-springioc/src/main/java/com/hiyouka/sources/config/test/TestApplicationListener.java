package com.hiyouka.sources.config.test;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * Date: 2019/2/19
 * @since JDK 1.8
 */
@Component
public class TestApplicationListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("TestApplicationListener..............");
    }
}