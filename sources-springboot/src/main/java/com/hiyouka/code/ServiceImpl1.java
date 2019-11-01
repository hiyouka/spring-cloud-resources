package com.hiyouka.code;

import hiyouka.seedframework.beans.annotation.Component;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
//@Service
// for seed framework
@Component
public class ServiceImpl1 extends SuperClass<Test1,Test1>{


    @Override
    public void test(Test1 test1) {
        System.out.println("test1");
    }
}