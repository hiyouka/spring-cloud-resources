package com.hiyouka.source;

import com.hiyouka.source.model.ConfigDataEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

import static com.hiyouka.source.Main.OperationType.*;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@SpringBootApplication
//@MapperScan("com.hiyouka.source.mapper")
@ComponentScan({"com.hiyouka.source.controller","com.hiyouka.source.service","com.hiyouka.source.config","com.hiyouka.source.properties"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableAsync
public class Main {

    private volatile String key;

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) throws Exception {

        SpringApplication.run(Main.class,args);

//        threadPoolTest();

//        Queue<String> queue_one = new ArrayBlockingQueue<String>(10000);
//        List<String> queue_one = new CopyOnWriteArrayList<>();
//
//        List<Future> futures = new ArrayList<>();
//        ExecutorService pool = Executors.newCachedThreadPool();
//        long l = System.currentTimeMillis();
//        for(int i = 0 ; i < 10000; i++){
//            String entry = i + "#body";
//            Future<?> submit = pool.submit(() -> {
//                queue_one.add(entry);
//            });
//            futures.add(submit);
//        }
//        for(Future future : futures){
//            future.get();
//        }
//        System.out.println(queue_one.size());
//        for(int i = 0 ; i< 10000; i++){
//            queue_one.get(i);
//        }
//        System.out.println(System.currentTimeMillis() - l);
//
//        BeanA beanA = new BeanA(1,"2");
//        BeanB beanB = new BeanB();
//        BeanUtils.copyProperties(beanA,beanB);
//        System.out.println(beanB);

//        QueueOperationManager<String> manager = new QueueOperationManager<>();
//        List<Entry<String>> entries = new ArrayList<>();
//        entries.add(new Entry<>(null,OperationType.POLL));
//        manager.queueTest(entries);
        ConfigDataEntry configDataEntry = new ConfigDataEntry();
        configDataEntry.setType("1");
        Field type = configDataEntry.getClass().getDeclaredField("type");
        type.setAccessible(true);
        Object o = type.get(configDataEntry);
        BeanA beanA = new BeanA(1,"2");
        Map<String,Object> entity = new HashMap<>();
//        BeanUtils.copyProperties(beanA,entity);
        Method getId = beanA.getClass().getMethod("getId");
        Object invoke = getId.invoke(beanA);
        System.out.println("invoke value --- " + invoke);
//        ReflectUtils.copyProperties(beanA,entity);
//        BeanUtils.copyProperties();
        entity.forEach((k,v)->{
            System.out.println(k + "====" + v);
        });
    }

    public static class BeanA{

        private Integer id;

        private String name;

        private BeanB beanB;

        public BeanB getBeanB() {
            return beanB;
        }

        public void setBeanB(BeanB beanB) {
            this.beanB = beanB;
        }

        public BeanA(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static class BeanB{

        private Integer id;

        private String code;

        public BeanB() {
        }

        public BeanB(Integer id, String code) {
            this.id = id;
            this.code = code;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return "BeanB{" +
                    "id=" + id +
                    ", code='" + code + '\'' +
                    '}';
        }
    }

    enum OperationType{
        OFFER,POLL
    }

    static class Entry<T>{

        final private T entry;

        final private OperationType type;

        public Entry(T entry, OperationType type) {
            if(POLL.equals(type)){
                entry = null;
            }
            this.entry = entry;
            this.type = type;
        }

        public T getEntry() {
            return entry;
        }

        public OperationType getType() {
            return type;
        }
    }

    static class QueueOperationManager<T>{


        final private ArrayBlockingQueue<T> queue = new ArrayBlockingQueue<>(5);

        public List<T> queueTest(List<Entry<T>> entries){

            List<T> pollEntries = new ArrayList<>();
            for(Entry<T> entry : entries){
                OperationType type = entry.getType();
                if(OFFER.equals(type)){
                    queue.offer(entry.getEntry());
                    System.out.println("offer " + entry + "from queue");
                }else if(POLL.equals(type)){
                    T poll = queue.poll();
                    pollEntries.add(poll);
                    System.out.println("poll " + poll + " from queue");
                }
            }

            return pollEntries;

        }

    }




    static void test(){
        //        int i = 6;
//        int key = hash("hiyouka");
//        System.out.println(key);
////        System.out.println(i&key);
//
//        while (true){
//            for(int i = 0 ; i< 10; i++){
//                vector.add(i);
//            }
//            Thread rem = new Thread(() -> {
//                synchronized (vector){
//                    for (int i = 0; i < vector.size(); i++) {
//                        vector.remove(i);
//                    }
//                }
//            });
//
//            Thread get = new Thread(() -> {
//                synchronized (vector){
//                     for (int i = 0; i < vector.size(); i++) {
//                        System.out.println(vector.size());
//                        vector.get(i);
//                    }
//                }
//            });
//            rem.start();
//            get.start();
//
//            while (Thread.activeCount() > 20);
//        }
    }


    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


    private static void threadPoolTest() throws ExecutionException, InterruptedException {
        String path = "D:\\WorkApp\\WorkSpace\\demo";
        File file = new File(path);


        System.out.println("线程初始化.......");
        ExecutorService pool = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor temp = (ThreadPoolExecutor) pool;
        System.out.println("核心线程size：" + temp.getCorePoolSize());
        System.out.println("最大线程size：" + temp.getMaximumPoolSize());
        while (true){
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            if(next.equals("start")){
                long l = System.currentTimeMillis();
                MatchName matchName = new MatchName(file,pool);
                Future<List<String>> submit = pool.submit(matchName);
//                FutureTask task = new FutureTask(matchName);
//                Thread thread = new Thread(task);
//                thread.start();
                List<String> result = submit.get();
                System.out.println(result.size());
                System.out.println("用时：" + (System.currentTimeMillis() - l));
            }
            else if(next.equals("edit")){
                System.out.println("输入文件路径");
                path = scanner.next();
                file = new File(path);
                System.out.println("设置文件成功!");
            }
            else if(next.equals("end"))
                break;
            else if(next.equals("message")){
                ThreadPoolExecutor ex = (ThreadPoolExecutor) pool;
                System.out.println("线程池大小:"+ex.getLargestPoolSize());
                System.out.println("核心线程size：" + temp.getCorePoolSize());
                System.out.println("最大线程size：" + temp.getMaximumPoolSize());
                System.out.println("当前文件路径：" + path);
            }else if(next.equals("setting")){
                while (true){
                    try{
                        ThreadPoolExecutor ex = (ThreadPoolExecutor) pool;
                        System.out.println("输入核心线程size：");
                        String coreSize = scanner.next();
                        ex.setCorePoolSize(Integer.parseInt(coreSize));
                        System.out.println("输入最大线程size：");
                        String maxNum = scanner.next();
                        ex.setMaximumPoolSize(Integer.parseInt(maxNum));
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("请输入数字!!");
                    }
                }
            }
            else
                System.out.println("命令不存在!");

            System.out.println(123);
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


}