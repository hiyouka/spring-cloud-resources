package com.hiyouka.chapterone;

import java.io.Serializable;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class StaticFactory implements Serializable{

    private static final StaticFactory instance = new StaticFactory();

    private StaticFactory(){
        throw new RuntimeException(" this class not can be instance again");
    }

    public static StaticFactory getInstance(){
        return instance;
    }



}