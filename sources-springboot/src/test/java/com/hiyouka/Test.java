package com.hiyouka;

import com.hiyouka.code.TestAutowired;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@SpringBootTest(classes = SpringBootMain.class)
@RunWith(SpringRunner.class)
public class Test {

    /* cache test start */


//    @Autowired
//    private CacheTest cacheTest;
//
//    private Integer tryTimes = 4;
//
//    private ExecutorService pool = Executors.newFixedThreadPool(2);
//
//
//    @org.junit.Test
//    public void cacheTest() throws ExecutionException, InterruptedException {
//        Future<?> test1 = pool.submit(() -> {
//            setCacheTest("test1",5L);
//        });
//        Future<?> test2 = pool.submit(() -> {
//            setCacheTest("test2",2L);
//        });
//        test1.get();
//        test2.get();
//    }
//
//    private void setCacheTest(String cacheName, Long sleepTime) {
//        for (int i=0; i<=tryTimes; i++){
//            try {
//                Thread.sleep(sleepTime);
//            } catch (InterruptedException e) {
//                System.out.println("thread error " + cacheName);
//            }
//            if(i == tryTimes){
//                cacheTest.cachePutTest(cacheName);
//            }
//            System.out.println(cacheTest.cacheGetTest(cacheName));
//        }
//    }

    /* cache test end */

    @Autowired
    private TestAutowired testAutowired;

    @org.junit.Test
    public void autowiredTest(){
        testAutowired.test();
    }

}