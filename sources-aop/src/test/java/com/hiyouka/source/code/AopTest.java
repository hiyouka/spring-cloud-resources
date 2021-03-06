package com.hiyouka.source.code;

import com.hiyouka.source.annotation.AfterAop;
import com.hiyouka.source.annotation.AroundAop;
import com.hiyouka.source.annotation.BeforeAop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Component("test_aop")
public class AopTest {

//    @Autowired
    private AsyncTest asyncTest;

    private Logger logger = LoggerFactory.getLogger(AopTest.class);

    public AopTest(AsyncTest asyncTest){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> AopTest");
        this.asyncTest = asyncTest;
    }

    public AsyncTest getAsyncTest() {
        return asyncTest;
    }

    @BeforeAop
    @AfterAop
    @AroundAop
//    @Transactional
//    @ConnectionHolderOperation
    public Map<String,Object> testBefore(){
        String result = "213";
//        if(true){
//            throw new RuntimeException("e");
//        }
        logger.info(Thread.currentThread().getName());
//        asyncTest.exceptionAsync();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
//        return result;
//        Future<Object> objectFuture = asyncTest.exceptionAsync();
//        objectFuture.get();
//        this.getClass().cast(AopContext.currentProxy()).innerTestBefore();
//        innerTestBefore();
    }

//    @AfterAop
    public void testExpose(){

    }

//    @BeforeAop
    @Async
    public void innerTestBefore(){
        logger.info(Thread.currentThread().getName());
        throw new RuntimeException(Thread.currentThread().getName() + " throw a exception");
    }



}