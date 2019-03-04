package com.hiyouka.sources.config.test;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * Date: 2019/2/20
 * @since JDK 1.8
 */
@Component
public class TestSmartLifecycle implements SmartLifecycle {

    public TestSmartLifecycle(){}

    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable callback) {
        System.out.println("TestSmartLifecycle callback stop .....");
    }

    @Override
    public void start() {
        System.out.println("TestSmartLifecycle start .....");
    }

    @Override
    public void stop() {
        System.out.println("TestSmartLifecycle stop .....");
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 0;
    }

    public class innerClass{
        public innerClass(){
        }

    }
}