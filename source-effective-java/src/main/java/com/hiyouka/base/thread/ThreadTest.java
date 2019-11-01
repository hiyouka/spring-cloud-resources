package com.hiyouka.base.thread;

import org.junit.Test;

import java.util.concurrent.*;

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

    /**
         1.当线程池小于corePoolSize时，新提交任务将创建一个新线程执行任务，即使此时线程池中存在空闲线程。
         2.当线程池达到corePoolSize时，新提交任务将被放入workQueue中，等待线程池中任务调度执行
         3.当workQueue已满，且maximumPoolSize>corePoolSize时，新提交任务会创建新线程执行任务
         4.当提交任务数超过maximumPoolSize时，新提交任务由RejectedExecutionHandler处理
         5.当线程池中超过corePoolSize线程，空闲时间达到keepAliveTime时，关闭空闲线程
         6.当设置allowCoreThreadTimeOut(true)时，线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭
     */

    @Test
    public void testThreadPoolExecutor() throws InterruptedException {
        int corePoolSize = 10;
        int maximumPoolSize = 20;
        int waitQueueSize = 10;
        LinkedBlockingDeque<Runnable> waitQueue = new LinkedBlockingDeque<>(waitQueueSize);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,5L,
                TimeUnit.MILLISECONDS,waitQueue,Executors.defaultThreadFactory(),
                new LogDiscardPolicy());
        executor.allowCoreThreadTimeOut(true);

        while (true){
            if(executor.getTaskCount() == (waitQueueSize + maximumPoolSize)){
                System.out.println("当前线程池线程数 :" + executor.getPoolSize());
            }
            else {
                executor.execute(() -> {
                    System.out.println("当前正在执行任务线程数 ：" + executor.getActiveCount());
                    System.out.println("当前线程池线程数 ：" + executor.getPoolSize());
                    System.out.println("当前等待线程数 ：" + waitQueue.size());
                    System.out.println("当前所接受任务总数： " + executor.getTaskCount());
                    try {
                        Thread.sleep(1000 * 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
                Thread.sleep(200);
        }

    }

    private static class LogDiscardPolicy extends ThreadPoolExecutor.DiscardPolicy{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            synchronized (this){
                super.rejectedExecution(r, e);
                System.out.println("skip this task ");
                System.out.println("当前所接受任务总数: " +  e.getTaskCount());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


}