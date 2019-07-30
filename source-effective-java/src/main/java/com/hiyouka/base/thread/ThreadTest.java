package com.hiyouka.base.thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class ThreadTest {

    @Test
    public void threadTest(){
        ExecutorService pool = Executors.newFixedThreadPool(6);
        Executors.newCachedThreadPool();
        pool.execute(() -> {
            System.out.println("thread execute : " + Thread.currentThread().getId());
        });
        pool.execute(() ->{
            System.out.println("thread execute : " + Thread.currentThread().getId());
        });
    }

    @Test
    public void queueTest() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(6);
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();

//        queue.add(1);
//        queue.offer(1);
//        queue.offer(1);

//        pool.execute(()->{
//            try {
//                System.out.println("offer.....");
//                boolean offer = queue.offer(5, 2000, TimeUnit.MILLISECONDS);
//                System.out.println("offer result : " + offer);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        pool.execute(()->{
//            Integer poll = null;
////            try {
//            try {
//                poll = queue.poll(5000,TimeUnit.MILLISECONDS);
//                System.out.println("take from queue : " + poll);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        });

//        pool.execute(()->{
//            try {
//                queue.put(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        pool.execute(()->{
//            try {
//                Integer take = queue.take();
//                System.out.println("take from queue : " + take);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        queue.put(1);
//        Integer take = queue.take();
//        boolean offer = queue.offer(1);
//        Integer poll = queue.poll();
//        System.out.println(poll);
//        queue.add(2);
//        System.out.println(queue.contains(1));
//        System.out.println(queue.contains(1));
    }

}