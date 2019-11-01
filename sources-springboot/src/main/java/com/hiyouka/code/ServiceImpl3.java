package com.hiyouka.code;

import hiyouka.seedframework.beans.annotation.Component;
import org.springframework.stereotype.Service;

/**
 * @author hiyouka
 * @since JDK 1.8
 */

@Service
//@Priority(Ordered.HIGHEST_PRECEDENCE)
// for seed framework
@Component
public class ServiceImpl3 extends SuperClass{

    @Override
    public void test(Object o) {
        System.out.println("test3 execute .......");
    }
}