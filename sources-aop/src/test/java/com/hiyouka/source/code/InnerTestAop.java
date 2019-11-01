package com.hiyouka.source.code;

import com.hiyouka.source.annotation.AfterAop;
import com.hiyouka.source.annotation.AroundAop;
import com.hiyouka.source.annotation.BeforeAop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Component
public class InnerTestAop implements InnerTestAopInterface{

    @Autowired
    private AopTest aopTest;

    @BeforeAop
    @AfterAop
    @AroundAop
    public Object testBefore(){
        String result = "213";
//        asyncTest.exceptionAsync();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
//        Future<Object> objectFuture = asyncTest.exceptionAsync();
//        objectFuture.get();
//        this.getClass().cast(AopContext.currentProxy()).innerTestBefore();
//        innerTestBefore();
    }

//    @Component
//    public static class InnerClass{
//
//    }
//
//    @Component
//    private class InnerClassNotStatic{
//
//    }

}