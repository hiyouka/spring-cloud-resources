package hiyouka.reactor.rx.demo;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

/**
 @author hiyouka
 @since JDK 1.8
 */
public class DemoRxJavaTest {

    public static Logger logger = LoggerFactory.getLogger(DemoRxJavaTest.class);
//    Log log = LogFactory.getLog(DemoRxJavaTest.class);

    /**
     *
     *
     * 个人对ObservableEmitter的理解 : 封装observer,对资源进行管理, 传递操作
     *
     *
     根据测试结果可知：
     关于上游(Observable):
       1.Observable 作为producer(生产者/上游),制定具体的上游对下游的操作(onSubscribe,onComplete,onNext,OnComplete,OnError)。
       2.ObservableOnSubscribe作为中间环节用于实现具体对下游操作的类。
       3.当调用上游的subscribe(订阅)操作时就会调用下游 Observer.onSubscribe()操作,之后调用ObservableOnSubscribe的subscribe()也就是需要我们所实现的对下游的操作)。
       4.ObservableOnSubscribe所调用的onComplete/onNext/OnError全部会链接到下游的onComplete/onNext/OnError。
       5.上游的onComplete起到了结束订阅的作用(作为上游发起)。
     @see io.reactivex.ObservableOnSubscribe
     @see io.reactivex.ObservableEmitter
     @see io.reactivex.internal.operators.observable.ObservableCreate
     *关于下游(Observer):
      1.Observer 作为Consumer(消费者/下游),作为具体实现操作业务逻辑的类(onSubscribe,onComplete,onNext,OnComplete,OnError)。
      2.利用上游进行subscribe还有一种下游类型->Consumer,该类型下游作为Observer的一种简便实现方式(实际操作类型是LambdaObserver),通过Consumer的accept链接到onNext。
      3.下游可以通过onSubscribe的Disposable.dispose()来进行取消订阅(作为下游发起)。
     *
     @see io.reactivex.Observer
     @see io.reactivex.functions.Consumer
     @see io.reactivex.internal.observers.LambdaObserver
     *
     API：
        上游(Observable):
          1. subscribeWith: 和 subscribe 类似只是在执行之后会返回下游对象(observer),对于取消订阅的observer可以调用其方法。
          2. suscribeOn: 简单来讲就是另开线程来执行ObservableOnSubscribe.subscribe()操作。
          3. observeOn: 另开线程来执行observer的操作/可指定多个 observerOn 对应多个 observer,即每个消费者一个线程去执行。
          4. map: 类似Java8 Stream的map,将上游(observable)的泛型类型转换,可以通过转换来适配下游。
          5. concat: 多个上游进行消息发布,当调用onComplete的时候会触发下个上游发布消息,否则不会触发。
          6.
        下游(Observer):
          下游有onSubscribe,onComplete,onNext,OnComplete,OnError这个操作，关注具体的操作业务逻辑
     *
     */
    /**
     @see
     @param
     @return void
     @throws
     */
    @Test
    public void observableBlocking(){
        logger.info("starter");
        Observable<Integer> objectObservable = Observable.create(aSubscriber -> {
            logger.info("observable start ....");
            for(int i=0; i<50; i++){
                if(!aSubscriber.isDisposed()){
                    aSubscriber.onNext(i);
                }
            }
            if(!aSubscriber.isDisposed()){
                aSubscriber.onComplete();
            }
            aSubscriber.onNext(10000);

        });
        Map<String,Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        objectObservable.map(k -> {
            if(k == 1){
                return k.toString();
            }else {
                return "666";
            }
        });
//        Observable<String> map = objectObservable.map((k) -> {
//            if(k == 1){
//                return k.toString();
//            }else {
//                return "666";
//            }
//        });
        Scheduler scheduler = Schedulers.newThread();
        Scheduler computation = Schedulers.computation();
        Scheduler executorScheduler = Schedulers.from(Executors.newFixedThreadPool(10));
        objectObservable.subscribeOn(scheduler)
                        .observeOn(computation)

                        .doOnNext(i -> {
                            String value = "value_1_" + i;
                            logger.info(value);
                        })
                        .observeOn(executorScheduler)
//                        .doOnNext(i -> {
//                            String value = "value_2_" + i;
//                            logger.info(value);
//                        })
//                        .subscribe();
                        .subscribe(new Observer<Integer>() {

                            private Disposable innerDisposable;

                            @Override
                            public void onSubscribe(Disposable disposable) {
                                logger.info("onSubscribe ....");
                                innerDisposable = disposable;
                            }

                            @Override
                            public void onNext(Integer i) {
                                String value = "ob value_" + i;
                                logger.info(value);
                //                if(i == 5){
                //                    innerDisposable.dispose();
                //                }
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                logger.info("onError: {}", throwable.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                logger.info("on complete .....");
                            }
                        });



//        Observer<Integer> observer = objectObservable.subscribeWith(new Observer<Integer>() {
//
//            private Disposable innerDisposable;
//
//            @Override
//            public void onSubscribe(Disposable disposable) {
//                logger.info("onSubscribe ....");
//                innerDisposable = disposable;
//            }
//
//            @Override
//            public void onNext(Integer i) {
//                String value = "value_" + i;
//                logger.info(value);
////                if(i == 5){
////                    innerDisposable.dispose();
////                }
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                logger.info("onError: {}", throwable.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                logger.info("on complete .....");
//            }
//        });
//
//        observer.onComplete();
//        observer.onNext(10);
//        objectObservable.subscribe(new Observer<Integer>() {
//
//            private Disposable innerDisposable;
//
//            @Override
//            public void onSubscribe(Disposable disposable) {
//                logger.info("onSubscribe ....");
//                innerDisposable = disposable;
//            }
//
//            @Override
//            public void onNext(Integer i) {
//                String value = "value_" + i;
//                logger.info(value);
////                if(i == 5){
////                    innerDisposable.dispose();
////                }
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                logger.info("onError: {}",throwable.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                logger.info("on complete .....");
//            }
//        });
        logger.info("end");

    }

    @Test
    public void observableNonBlocking(){
        logger.info("starter");
        Observable<String> objectObservable = Observable.create(aSubscriber -> {
//            new Thread(() ->{
                for(int i=0; i<75; i++){
                    if(!aSubscriber.isDisposed()){
                        aSubscriber.onNext("value_"+i);
                    }
                }
                if(!aSubscriber.isDisposed()){
                    aSubscriber.onComplete();
                }
//            }).start();
        });
        objectObservable.subscribe(s -> {
            logger.info(s);
        });
        logger.info("end");
    }

    @Test
    public void testConcat() throws InterruptedException {
//        AtomicInteger integer = new AtomicInteger(1);
        ExecutorService executor = Executors.newFixedThreadPool(20);
        CopyOnWriteArrayList<String> que = new CopyOnWriteArrayList<>();
        String cacheKey = "cache";
        /** 缓存获取操作 */
        Map<String,String> cache = new HashMap<>();
        Observable<String> first = Observable.create(s -> {
//            executor.execute(() ->{
                String data = cache.get(cacheKey);
//            if(data != null){
              for(int i=0; i<5; i++){
                  data = "123_" + i;
                  if(i != 4){
                      que.add("observable first");
                      logger.info("缓存获取数据....");
                      s.onNext(data);
                  }else {
                      s.onComplete();
                  }
              }
//            }
//            else {
//                s.onComplete();
//            }
//            });

        });
        Observable<String> second = Observable.create(s -> {
            logger.info("业务获取数据....");
            String data = "123_4";
            cache.put(cacheKey,data);
            que.add("observable second");
            s.onNext(data);
//            s.onComplete();
//            throw new RuntimeException("666");
        });
        Disposable subscribe = Observable.concat(first, second)
                .doOnEach(d ->
                        executor.execute(() ->{
                            if(d.isOnNext()){
                                logger.info("一号机: " + d.getValue());
                            }else if(d.isOnError()){
                                logger.info("一号机: error" + d.getError());
                            }
                        })
                )
                .doOnEach(d ->
                        executor.execute(() -> {
                            if(d.isOnNext()){
                                logger.info("二号机: " + d.getValue());
                            }else if(d.isOnError()){
                                logger.info("二号机: error" + d.getError());
                            }
                        })
                )
                .doOnEach(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        logger.info("二号机: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.info("三号机: error  " + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                })
//                .subscribe();
                .subscribe((String d) ->
                        executor.execute(() ->{
                            que.add("observer subscribe 1");
                            logger.info("接收数据:" + d);
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }),(Throwable s) -> {
                    logger.info("异常:" + s);
                });
        subscribe.dispose();
        System.out.println(subscribe.isDisposed());
        Thread.sleep(20000);
        System.out.println(que);
        System.out.println(subscribe.isDisposed());
    }

    /**
     *  分割任务
     */
    @Test
    public void testFlatMap(){
//        Observable<Integer> invert = Observable.create(s -> {
//            for (int i = 0; i < 20; i++) {
//                s.onNext(i);
//            }
//        });
//        invert.flatMap(new Function<Integer, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(Integer integer) throws Exception {
//                return null;
//            }
//        });

        List<Integer> list = Arrays.asList(1,2,3);
        Observable<Integer> invert2 = Observable.fromIterable(list);
        invert2.flatMap(i -> {
            logger.info("执行任务 : " + i);
            return Observable.create(s -> {
                s.onNext(i);
            }).subscribeOn(Schedulers.newThread());
        }).subscribe(s -> {
            logger.info("执行完成..." + s);
        });

//        RxJava2Adapter.flowableToFlux();
    }


    @Test
    public void testZip(){
        List<Integer> first = Arrays.asList(1, 2, 3);
        List<Integer> second = Arrays.asList(4, 5, 6);
    }

    @Test
    public void flowableTest() throws Exception {
        List<String> result = new ArrayList<>();
        Callable<List<String>> callable = Executors.callable(() -> {result.add("1");logger.info("run");},result);
        ExecutorService executors = Executors.newFixedThreadPool(20);
        Future<?> submit = executors.submit(() -> {
            logger.info("submit");
            result.add("666");
        });

        Future<List<String>> submit_of_result = executors.submit(() -> {
            logger.info("submit of result");
            result.add("777");
        }, result);
        Thread.sleep(2000);
//        Object o = submit.get();
//        System.out.println(result);
        List<String> r = callable.call();
        Object o = submit.get();
        List<String> list = submit_of_result.get();
//        System.out.println(result);
//        System.out.println(r);
    }



    public static void main(String[] args) {
        args = new String[2];
        args[0] = "world";
        args[1] = "hiyouka";
        List<String> list = new ArrayList<>();
        Observable<String> observable = Observable.fromIterable(list);
        for(int i=0; i<100; i++){
            list.add(i+"");
        }
//        observable.subscribe(s -> System.out.println("hello " + s + " !"));

        Disposable subscribe = Flowable.fromIterable(list).subscribe(s -> System.out.println("hello " + s + " ! "));
        boolean disposed = subscribe.isDisposed();


    }





    /**
     *  Callback
     */

}