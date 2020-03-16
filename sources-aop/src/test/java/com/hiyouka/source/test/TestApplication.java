package com.hiyouka.source.test;

import com.hiyouka.source.AopApplication;
import com.hiyouka.source.aop.AopTest2;
import com.hiyouka.source.code.AopTest;
import com.hiyouka.source.code.AsyncTest;
import com.hiyouka.source.code.InnerTestAopInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

/**
 * @author hiyouka
 * @since JDK 1.8
 */

@SpringBootTest(classes = AopApplication.class)
@RunWith(SpringRunner.class)
public class TestApplication {

    private static Logger logger = LoggerFactory.getLogger(TestApplication.class);

    @Autowired
    @Qualifier("test_aop")
    private AopTest aopTest;

    @Autowired
    private AopTest2 aopTest2;

    @Autowired
    private com.hiyouka.source.code.AopTest2 aopTestT;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private InnerTestAopInterface innerTestAop;

    @Autowired
    private AsyncTest asyncTest;

//    @Autowired
//    private InnerTestAop.InnerClass innerClass;


    @Test
    public void test() throws InterruptedException, ExecutionException {

//        aopTest2.testIntercep();
//        runTransaction();
//        Object bean = beanFactory.getBean(AopConfigUtils.AUTO_PROXY_CREATOR_BEAN_NAME);
//        logger.info(">>>>>>>>>>>>>>>>>"+bean.getClass().getName());
        Object taskExecutor = this.beanFactory.getBean(TaskExecutor.class);

        asyncTest.testAsync();
        Thread.sleep(200000);
//        System.out.println(future.get());
//        AsyncTest asyncTest = aopTest.getAsyncTest();
//        aopTest.testBefore();

//        System.out.println(o);
//        innerTestAop.testBefore();
//        System.out.println();
//        aopTestT.testBefore();
//        AopTest cast = AopTest.class.cast(AopContext.currentProxy());
//        cast.testBefore();
    }

    @Transactional
    public void runTransaction(){

        logger.info("run transaction ....");
        new Thread(TestApplication::runThread).start();
}

    private static void runThread() {
        logger.info("new thread start .....");
        throw new RuntimeException(Thread.currentThread().getName() + " throw a exception");
    }



}