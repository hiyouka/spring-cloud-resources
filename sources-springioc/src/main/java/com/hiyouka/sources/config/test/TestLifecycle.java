package com.hiyouka.sources.config.test;

import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * Date: 2019/2/20
 * @since JDK 1.8
 */
@Component
public class TestLifecycle implements Lifecycle {

    @Override
    public void start() {
        System.out.println("TestLifecycle start ......");
    }

    @Override
    public void stop() {
        System.out.println("TestLifecycle stop ......");
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}