package com.hiyouka.code;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class BaseService <G> {

    public void test(G t){
        System.out.println("base service : " + t);
    };

}