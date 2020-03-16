package com.hiyouka.base.thread;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
//        int corePoolSize = 10;
//        int maximumPoolSize = 20;
//        int waitQueueSize = 10;
//        LinkedBlockingDeque<Runnable> waitQueue = new LinkedBlockingDeque<>(waitQueueSize);
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,5L,
//                TimeUnit.MILLISECONDS,waitQueue,Executors.defaultThreadFactory(),
//                new LogDiscardPolicy());
//        executor.allowCoreThreadTimeOut(true);
        ThreadPoolExecutor executor = createDefaultThreadPool();
        BlockingQueue<Runnable> waitQueue = executor.getQueue();
        while (true){
//            if(executor.getTaskCount() == (waitQueueSize + maximumPoolSize)){
//                System.out.println("当前线程池线程数 :" + executor.getPoolSize());
//            }
//            else {
                executor.execute(() -> {
                    System.out.println("当前正在执行任务线程数 ：" + executor.getActiveCount());
                    System.out.println("当前线程池线程数 ：" + executor.getPoolSize());
                    System.out.println("当前等待执行任务数 ：" + waitQueue.size());
                    System.out.println("当前所接受任务总数： " + executor.getTaskCount());
                    try {
                        Thread.sleep(1000 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
//            }
                Thread.sleep(1000);
        }

    }

    private ThreadPoolExecutor createDefaultThreadPool(){
        int corePoolSize = 3000;
        int maximumPoolSize = 3000;
        int waitQueueSize = 10000;
        LinkedBlockingDeque<Runnable> waitQueue = new LinkedBlockingDeque<>(waitQueueSize);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,5L,
                TimeUnit.MILLISECONDS,waitQueue,Executors.defaultThreadFactory(),
                new LogDiscardPolicy());
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }


    @Test
    public void testSynchronized() throws ExecutionException, InterruptedException {

        ThreadPoolExecutor pool = createDefaultThreadPool();
        Body body = new Body();
        long l = System.currentTimeMillis();
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 8000; i++) {
            futures.add(pool.submit(() -> {
                synchronizedExecute(body);
            }));
        }
        for(Future future : futures){
            future.get();
        }
        System.out.println(System.currentTimeMillis()-l);
        System.out.println(body.getSize());
    }



    private String lockKey = "13";
    @Data
    private class Body{
        private int size = 0;

        public void addSize(){
            synchronized (lockKey){
                this.size++;
            }
        }
    }
    private void synchronizedExecute(Body body){
//        synchronized (body){
//        }
        try {
            body.addSize();
            Thread.sleep(1000);
            body.addSize();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static class MatchName implements Callable<List<String>> {

        private File file;

        private ExecutorService pool;

        private List<String> fileNames;

        public MatchName(File file, ExecutorService pool) {
            this.file = file;
            this.pool = pool;
        }

        @Override
        public List<String> call() throws Exception {
            fileNames = new LinkedList<>();
            List<Future<List<String>>> futures = new ArrayList<>();
            File[] files = file.listFiles();
            for(File file : files){
                if(file.isDirectory()){
                    MatchName matchName = new MatchName(file,pool);
                    Future<List<String>> submit = pool.submit(matchName);
                    futures.add(submit);
                }
                else {
                    fileNames.add(file.getName());
                }
            }

            for(Future<List<String>> future : futures){
                fileNames.addAll(future.get());
            }
            return fileNames;
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

    private Log log = LogFactory.getLog(ThreadTest.class);

    public void testScheduled(){
        log.info("execute ...");
    }

    @Test
    public void test() throws NoSuchMethodException {
        // 秒
//        int interval = 10 * 1000;
//        ScheduledTasks scheduledTasks = new ScheduledTasks();
//        ThreadTest threadTest = new ThreadTest();
//        Method testScheduled = threadTest.getClass().getDeclaredMethod("testScheduled");
//        scheduledTasks.add(new Scheduled(new Date(),threadTest,testScheduled));
//        while (true){
//            scheduledTasks.execute();
//        }
    }

    public class ScheduledTasks{

        private List<Scheduled> tasks = new ArrayList<>();

        public void add(Scheduled scheduled){
            if(scheduled != null){
                this.tasks.add(scheduled);
            }
        }

        public void execute(){
            new Thread(() -> {
                while (true){
                    if(tasks != null && tasks.size() != 0){
                        for(Scheduled scheduled : tasks){
                            scheduled.run();
                        }
                    }
                }
            }).start();
        }

    }

    private class Scheduled {

        private Date executeDate;

        private Integer interval;

        private Object target;

        private Method executeMethod;

        public Scheduled(Date executeDate, Object target, Method executeMethod) {
            this.executeDate = executeDate;
            this.target = target;
            this.executeMethod = executeMethod;
        }

        public void setInterval(Integer interval) {
            this.interval = interval;
        }

        public void run(){
//            new Thread(() ->{
                if(new Date().equals(this.executeDate)){
                    try {
                        executeMethod.invoke(target);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
//            }).start();
        }
    }

}