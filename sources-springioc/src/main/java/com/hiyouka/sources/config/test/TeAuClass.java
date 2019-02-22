package com.hiyouka.sources.config.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * Date: 2019/2/20
 * @since JDK 1.8
 */
@Component
public class TeAuClass {

    @Autowired
    private SupClassU supClassU;

    private int geed;

    public int getGeed() {
        return geed;
    }

    public void setGeed(int geed) {
        this.geed = geed;
    }
}