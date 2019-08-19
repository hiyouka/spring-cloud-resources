package com.hiyouka.source.test;

import com.hiyouka.source.AopApplication;
import com.hiyouka.source.aop.AopTest2;
import com.hiyouka.source.code.AopTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@SpringBootTest(classes = AopApplication.class)
@RunWith(SpringRunner.class)
public class TestApplication {

    @Autowired
    private AopTest aopTest;

    @Autowired
    private AopTest2 aopTest2;

    @Autowired
    private com.hiyouka.source.code.AopTest2 aopTestT;

    @Test
    public void test(){
//        aopTest2.testIntercept();
        aopTest.testBefore();
        aopTestT.testBefore();
//        AopTest cast = AopTest.class.cast(AopContext.currentProxy());
//        cast.testBefore();
    }


}