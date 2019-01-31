package com.hiyouka.sources.util;

import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * Date: 2019/1/28
 * @since JDK 1.8
 */
@Component
public class TeClassU extends ClassUtils{

    private String old;

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }
}