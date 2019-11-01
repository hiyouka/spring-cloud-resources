package com.hiyouka.code;

import hiyouka.seedframework.beans.annotation.Component;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
//@Service
//@Primary
// for seed framework
@Component
public class ServiceImpl2<T,D> extends SuperClass<T,D> implements NoGenericInterface{


    @Override
    public void test(T test2) {
        System.out.println("test1");
    }

}