package hiyouka.reactor.rx.demo;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.publisher.*;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class DemoReactorTest extends Common {

    private static final Logger logger = LoggerFactory.getLogger(DemoReactorTest.class);

    @Test
    public void testFlux() {
//        Flux<List<String>> flux = Flux.create(s -> {
//            s.next(
//        });
//        List<String> list = flux.blockFirst();
    }

    public Flux<List<String>> generateListFlux() {
        List<String> value = new ArrayList<>();
        Flux<List<String>> result = Flux.create(e -> {
            e.next(value);
        });
        return null;
    }


    /**
     * reactor 内置的工厂方法使用
     */
    @Test
    public void innerFactoryMethod() {

        /* Flux */
        Flux<String> just = Flux.just("1", "2", "3");
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("1", "2", "3"));
        Flux<Integer> range = Flux.range(1, 5);
        Disposable subscribe = just.subscribe();
        /* Mono */
        Mono<String> empty = Mono.empty();
        Mono<String> just1 = Mono.just("1");

    }

    /**
     * 订阅方法
     * <p>
     * subscribe();
     * <p>
     * subscribe(Consumer<? super T> consumer);
     * <p>
     * subscribe(Consumer<? super T> consumer,
     * Consumer<? super Throwable> errorConsumer);
     * <p>
     * subscribe(Consumer<? super T> consumer,
     * Consumer<? super Throwable> errorConsumer,
     * Runnable completeConsumer);
     * <p>
     * subscribe(Consumer<? super T> consumer,
     * Consumer<? super Throwable> errorConsumer,
     * Runnable completeConsumer,
     * Consumer<? super Subscription> subscriptionConsumer);
     * <p>
     * 订阅并触发序列。
     * 对每一个生成的元素进行消费。
     * 对正常元素进行消费，也对错误进行响应。
     * 对正常元素和错误均有响应，还定义了序列正常完成后的回调。
     */
    @Test
    public void subscribeMethod() throws InterruptedException {
        /* 最简单订阅  */
        Flux<Integer> range = Flux.range(1, 3);
        range.subscribe();
        System.out.println("=============================1");
        /* 消费消息 */
        Flux<Integer> flux = Flux.create(sink -> {
            Executor executor = getDefaultExecutor();
            AtomicInteger index = new AtomicInteger(0);
            IntStream.range(0, 40).forEach(v -> {
                executor.execute(() -> {
                    int value = index.getAndIncrement();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("push value : " + value);
                    sink.next(value);
                });
            });

        });
//        BaseSubscriber<Integer> defaultSubscribe = getDefaultSubscribe();
        flux.subscribe(getDefaultConsumer());
        Thread.sleep(100000);
//        range.subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onSubscribe(Subscription subscription) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println(integer);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
        System.out.println("=============================2");
        /* 订阅错误处理 */
        Flux<Integer> errorFLux = range.map(i -> {
            if (i <= 2)
                return i;
            throw new RuntimeException("too big!!");
        });
        errorFLux.subscribe(System.out::println, System.err::println);
        System.out.println("=============================3");
        /* 订阅onComplete处理 */
        range.subscribe(System.out::println, System.err::println, () -> System.out.println("onComplete out: done!!"));
        System.out.println("=============================4");

        /* 包含自定义subscriber的订阅 */
        BaseSubscriber<Integer> ss = new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("onSubscription");
                super.hookOnSubscribe(subscription);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println(value);
                super.hookOnNext(value);
            }
        };
        range.subscribe(System.out::println,
                error -> System.err.println("Error " + error),
                () -> {
                    System.out.println("Done");
                },
                s -> ss.request(10));
        range.subscribe(ss);
        System.out.println("=============================5");

        /* 使用BaseSubscriber配置背压 */
        Flux<String> source = Flux.just("geralt", "yennefer", "dandelion", "ciri");
        source.map(String::toUpperCase)
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("onSubscribe");
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(String value) {
                        System.out.println(value);
                        System.out.println("||");
                        request(1);
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("onComplete");
                    }

                    @Override
                    protected void hookOnCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    protected void hookFinally(SignalType type) {
                        System.out.println("onFinally");
                    }
                });
        System.out.println("=============================6");

    }

    /**
     * 生成序列
     */
    @Test
    public void bornFlux() throws InterruptedException {
        ExecutorService executor = getDefaultExecutor();
        int action = 9;
        List<String> data = Arrays.asList("1", "2", "3", "4", "5", "6");
        switch (action) {
            case 1:
                /* generate 同步池 */
                Flux<String> generateFlux = Flux.generate(() -> 0,     // 初始状态为0
                        (state, sink) -> {
                            sink.next("3 x " + state + " = " + 3 * state);     // 基于状态state*3生成下一个值
                            if (state == 10) sink.complete();                  // 定义中止时机
                            return state + 1;
                        });
                generateFlux.subscribe(System.out::println);
                System.out.println("=============================1");
                Flux<Object> generate = Flux.generate(AtomicLong::new, (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3 * i);
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> {
                    System.out.println(state);// onComplete/异常时会调用
                });
                generate.subscribe(System.out::println);
                System.out.println("=============================2");
                break;
            case 2:
                /* create 既可以创建同步序列,也可以创建异步序列 */
                Flux<Object> flux1 = Flux.create(sink -> {
                    data.forEach(v -> {
                        log.info("create next value : " + v);
                        sink.next(v);
                    });
                    sink.complete();
                }, FluxSink.OverflowStrategy.BUFFER);
                flux1.subscribe(getDefaultSubscribe());
                break;
            case 3:
                /* push push是create的变体,适合生成事件流*/
                Flux<Object> push = Flux.push(sink -> {
                    executor.execute(() ->
                            IntStream.range(0, 20).forEach(v -> {
//                            executor.execute(() -> {
                                log.info("push next value : " + v);
                                sink.next(v);
//                            });
                            }));
                    // 如果onComplete之后继续request消息,消息将会被drop\
//                    sink.complete();
                }, FluxSink.OverflowStrategy.ERROR);
                push.subscribeOn(Schedulers.elastic()).subscribe(getDefaultSubscribe());
                break;
            case 4:
                /* push/pull 混合模式 */
                Flux<Object> flux = Flux.create(sink -> {
                    sink.onRequest(n -> {

                    });
                });
                break;
            case 5:
                /* handle 可用于源数据处理*/
                Flux<Integer> just = Flux.just(1, 2, 3).handle((v,sink) -> {
                    if(v == 1){
                        sink.next(v);
                    }
                });
                just.subscribe(getDefaultConsumer());
//                just.handle()
                break;
            case 6:
                /* 线程设定  subscribeOn 影响生成者线程 publishOn影响后续消费逻辑线程*/
//                Flux<String> just1 = Flux.just("1", "2", "3");
//                Disposable subscribe = just1.subscribe(v -> log.info("subscribe on : " + v));
//
//
//                Mono<String> just2 = Mono.just("5");
//                Mono.create(sink -> {
//                   IntStream.range(0,5).forEach(sink::success);
//                }).subscribe(v -> log.info("subscribe on : " + v));
//                just2.subscribe(v -> log.info("subscribe on : "+ v));

                AtomicInteger atomicInteger = new AtomicInteger(1);
//                Flux<Integer> inc = Flux.create(sink -> {
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    int andIncrement = atomicInteger.getAndIncrement();
//                    log.info("push"+andIncrement);
//                    sink.next(andIncrement);
//                });
//                inc = inc.subscribeOn(Schedulers.elastic());
//                inc.subscribe(v -> log.info("subscribe1 on : " + v));
//                inc.subscribe(v -> log.info("subscribe2 on : " + v));

                Flux<Object> flux2 = Flux.push(fluxSink -> {
                    IntStream.range(0, 20).forEach(v -> {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("next push : " + v);
                        fluxSink.next(v);
                    });
                }).subscribeOn(Schedulers.single()).publishOn(Schedulers.elastic());
                Flux.range(1, 20)
//                    .subscribeOn(Schedulers.elastic())
                        .publishOn(Schedulers.elastic())
                        .subscribe(v ->
                        {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            log.info("subscribe1 on : " + v);
                        });
//                range.subscribe(v -> log.info("subscribe2 on : " + v));
                flux2.subscribe(v -> log.info("subscribe on : " + v));


                Mono<Integer> blockMono = Mono.fromCallable(() -> {
                    Thread.sleep(200);
                    int andIncrement = atomicInteger.getAndIncrement();
                    log.info("call" + andIncrement);
                    return andIncrement;
                }).subscribeOn(Schedulers.elastic())
                    .publishOn(Schedulers.fromExecutor(executor))
                    .map(v -> {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("multiplication map now value: " + v);
                        return v * 20;
                    })
                    .publishOn(Schedulers.fromExecutor(executor))
                    .map(v -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("division map now value : " + v);
                        return v / 2;
                    })
                    .publishOn(Schedulers.fromExecutor(executor));

//                BaseSubscriber defaultSubscriber = new BaseSubscriber<Integer>(){
//                    @Override
//                    protected void hookOnNext(Integer value) {
//                        log.info("subscribe on : " + value);
//                    }
//                };
//                blockMono.subscribe(v -> log.info("call-first on : " + v));
//                blockMono.subscribe(v -> log.info("call-second on : " + v));
//                Mono<Integer> blockMono2 = Mono.fromCallable(() -> {
//                    Thread.sleep(200);
//                    log.info("call 2");
//                    return atomicInteger.getAndIncrement();
//                }).subscribeOn(Schedulers.newSingle("test"));
//                blockMono2.subscribe(v -> log.info("subscribe on : " + v));
//                defaultSubscriber.request(1);
//                defaultSubscriber.request(2);
                break;
            case 7:
                /* 错误处理 */
                Flux<Integer> errorHandle = Flux.range(0, 10)
                        .map(v -> {
                            if (v < 5) {
                                v = v - 1;
                            }else {
                                log.error("the num is greater than 5");
                                throw new RuntimeException("too big !!");
                            }
                            return v;
                        })
                        .map(v -> v + 1)
                        .publishOn(Schedulers.parallel());
//                errorHandle.subscribe(v -> log.info("subscribe on : " + v),e -> log.error("error : " + e));

                /* 静态缺省值 */
                Flux<String> errorFallback = Flux.range(0, 20)
                        .map(v -> {
                            if (v < 5) {
                                return v + "";
                            } else {
                                log.error("the num is greater than 5");
                                throw new RuntimeException("too big !!");
                            }
                        })
                        //设置异常错误返回一个缺省值，也可以对异常进行区分处理
//                        .onErrorReturn(e -> e.getMessage().equals("too big !!"), "Default")
//                        .onErrorReturn("Default")
                        .publishOn(Schedulers.parallel());
//                errorFallback.subscribe(v -> log.info("subscribe on : " + v));

                Flux<String> errorFlatFallback = Flux.just("one", "two")
                        .flatMap(k -> getDefaultErrorMono(k)
                        // onErrorResume 可以返回一个Flux/Mono来对异常返回缺省值
                        // 下面的例子为对不同异常的处理方式，当然也可以使用一个，通过判断异常类型来区分
                        .onErrorResume(e -> e instanceof InterruptedException,e -> getDefaultErrorHandleMono(k,e))
//                        .onErrorResume(e -> e instanceof CustomException2,e -> getDefaultErrorProcessMono(k,e))
                        )

                        .subscribeOn(Schedulers.parallel());
//                errorFlatFallback.subscribe(v -> {
//                   if(v.equals("two")){
//                       throw new RuntimeException("can be process on errorResume");
//                   }else {
//                       log.info("subscribe on : " + v);
//                   }
//                }, getDefaultErrorHandleConsumer());

                /* 动态候补值 dynamic fallback value */
                // 其实和之前类似，只是调用统一的异常处理返回
                errorFallback.onErrorResume(error -> Mono.just(decideValueAccordingToThrowable(error)));

                /* 捕获重新抛出 catch and rethrow */
                Flux<String> rethrow = Flux.just("forError")
                        .flatMap(k -> getDefaultErrorMono(k))
                        .onErrorResume(e -> Flux.error(new RuntimeException("rethrow exception")));
//                rethrow.subscribe(getDefaultConsumer(), getDefaultProcessorErrorConsumer());
                // 简易写法 内部其实原理一样
                //public final Flux<T> onErrorMap(Function<? super Throwable, ? extends Throwable> mapper) {
                //		return onErrorResume(e -> Mono.error(mapper.apply(e)));
                //	}
                rethrow = Flux.just("forError")
                    .flatMap(k -> getDefaultErrorMono(k))
                    .onErrorMap(e -> new RuntimeException("rethrow exception"));

                /* doOnError 在有异常时进行一些不会对后续有影响的操作 */
                Flux<String> doOnErrorFlux = Flux.just("doOnError1","doOnError2")
//                        .subscribeOn(Schedulers.parallel())
                        .flatMap(k -> getDefaultErrorMono(k)
                                .doOnError(e -> defaultErrorHandle(e))
//                                .onErrorResume(e -> getLogErrorHandleMono(k,e))
                                .onErrorResume(e -> getDefaultErrorHandleMono(k,e))

                        );
//                doOnErrorFlux.subscribe(getDefaultConsumer());

                /* 类似java finally */
                AtomicInteger inc = new AtomicInteger(0);
                Flux<String> finallyFlux = Flux.just("one", "two")
                        .doFinally(type -> {
                            logger.info("doFinally type : " + type);
                            if (type == SignalType.CANCEL)
                                inc.getAndIncrement();
                        })
                        .doOnCancel(() -> log.info("cancel"))
                        .take(1);
//                finallyFlux.subscribe(getDefaultConsumer());
                /* 类似java try-catch-resource */
                Flux<Integer> using = Flux.using(
                // 资源生成端
                () -> {
                    logger.info("call");
                    return "1";
                },
                // 处理资源端
                disposable -> {
                    logger.info("second : " + disposable);
                    return Mono.just(1);
                },
                // 用于释放资源
                d -> logger.info("consumor : " + d));
                // 消费
//                using.subscribe(getDefaultConsumer());
                /*  */
                Flux<String> handle = Flux.interval(Duration.ofMillis(666),Duration.ofMillis(200),Schedulers.elastic())
                                           .map(v -> {
                                               if(v > 3)
                                                   throw new RuntimeException("too big !!");
                                               else
                                                   return "tick : " + v;
                                           })
                                           .onErrorReturn("nothing");
                Flux<Long> interval = Flux.interval(Duration.ofMillis(200));
                interval.subscribe(getDefaultConsumer());

//                handle.subscribe(getDefaultConsumer());

                break;
            case 8:
                /* retrying 重试*/
                Flux<Tuple2<Long, String>> retry = Flux.interval(Duration.ofMillis(0),Duration.ofMillis(300))
                        .map(v -> {
                            if (v > 3)
                                throw new RuntimeException("too big !!");
                            else
                                return "tick : " + v;
                        })
                        // 重试的间隔时长决定于 interval的初始间隔
                        .retry(1)
                        // 将会计算每次消费的时间 以Tuple对象输出（key为long时间）
                        .elapsed();
//                retry.subscribe(getDefaultConsumer(),getDefaultErrorHandleConsumer());
                Flux<Tuple2<Long, String>> retry2 = Flux.just(1, 2, 3)
                        .map(v -> {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (v > 2)
                                throw new RuntimeException("too big !!");
                            else
                                return "tick : " + v;
                        })
                        .retry(1)
                        .elapsed();
//                retry2.subscribe(getDefaultConsumer(),getDefaultErrorHandleConsumer());
                /* retryWhen */
                Flux<Object> flux3 = Flux.error(new IllegalArgumentException())
                                         .doOnError(e -> defaultErrorHandle(e))
                                         .retryWhen(e -> {
                                             return e.filter(ex -> ex instanceof RuntimeException).take(2);
                                         });
//                flux3.subscribe(getDefaultConsumer(),getDefaultErrorHandleConsumer());

                Flux<Object> flux4 = Flux.error(new IllegalArgumentException())
                        .doOnError(e -> defaultErrorHandle(e))
                        .retryWhen(e -> e.zipWith(Flux.range(1, 4), (error, index) -> {
                            if (index < 4)
                                return index;
//                            throw Exceptions.propagate(error);
                            throw new RuntimeException("retry four times, execute error again");
                        }));
//                flux4.subscribe(getDefaultConsumer(),getDefaultErrorHandleConsumer());
                break;
            case 9:
                /* 处理异常  */
                Flux<String> handle1 = Flux.range(0, 4)
                        .map(v -> {
                            try {
                                logger.info("push thread : " + Thread.currentThread().getName());
                                return convert(v);
                            } catch (IOException e) {
                                // Exceptions 工具类包装异常 返回运行时异常
                                // Exceptions 工具类还可以将异常恢复成初始类型 Exceptions.unwrap(e);
                                throw Exceptions.propagate(e);
                            }
                        }).publishOn(Schedulers.elastic());
                handle1.subscribeOn(Schedulers.single()).subscribe(getDefaultConsumer(),getDefaultErrorHandleConsumer());
                break;
            case 10:
                /* Processor 在Reactor官方文档中建议我们避免使用Processor,由于它较难使用, 尽量使用其他组合的操作符来避免使用*/

                break;

        }

        // wait task complete
        Thread.sleep(5000);
    }

    private String convert(int v) throws IOException {
        if(v < 3)
            return "convert : " + v;
        throw new IOException("Convert error");
    }

    private void defaultErrorHandle(Throwable e){
        logger.error("error cause by : " + e);
    }

    private String decideValueAccordingToThrowable(Throwable error){
        return error.getClass().getName();
    }

    private class CustomException1 extends RuntimeException{
        public CustomException1(String message) {
            super(message);
        }
    }

    private class CustomException2 extends RuntimeException{
        public CustomException2(String message) {
            super(message);
        }
    }


    private <T> Mono<T> getDefaultMono(T t){
        return Mono.create(sink -> {
            log.info("generate mono to push : " + t);
//            if(t.equals("two"))
//                throw new CustomException2("push error");
            sink.success(t);
        });
    }

    private <T> Mono<T> getDefaultErrorMono(T t){
        return Mono.create(sink -> {
            log.info("generate error mono to push : " + t);
            throw new CustomException1("push error");
        });
    }

    private <T> Flux<T> getDefaultFlux(T... ts){
        return Flux.create(sink -> {
            log.info("generate flux to push : " + ts);
            for(T t : ts){
                sink.next(t);
            }
        });
    }

    private String getDefault(String key){
        return "Default-"+key;
    }

    private Mono<String> getLogErrorHandleMono(String v, Throwable e){
        return Mono.create(sink -> {
            log.info("generate log error handle mono : " + v);
        });
    }

    private Mono<String> getDefaultErrorHandleMono(String v, Throwable e){
        return Mono.create(sink -> {
            String nv = e + "-error-" + e.getClass().getName();
            log.info("generate default error handle mono : " + nv);
            sink.success(nv);
        });
    }

    /** 
     * 分为两大类:direct/synchronous(在Reactor 3.3中移除了asynchronous异步的方式,可以使用其他方式来实现异步)
     * direct: DirectProcessor/UnicastProcessor
     *         DirectProcessor: 简单的订阅者处理器，不具备背压能力
     * synchronous: EmitterProcessor/ReplayProcessor
     */
    @Test
    public void testProcessor(){
        
        /* DirectProcessor */
        DirectProcessor<String> directProcessor = DirectProcessor.create();
        FluxSink<String> next = directProcessor.sink(FluxSink.OverflowStrategy.IGNORE)
                                               .next("1");
//        next.onRequest(l -> logger.info(l+""));
//        next.onRequest(l -> logger.info(l+""));

        /* UnicastProcessor */
        UnicastProcessor<String> unicastProcessor = UnicastProcessor.create();
        FluxSink<String> sink = unicastProcessor.sink(FluxSink.OverflowStrategy.IGNORE);
        sink.onRequest(l -> logger.info(l + ""));
        sink.onRequest(l -> logger.info(l + ""));


    }

    /**
     * 测试背压
     * 背压策略(默认采用onBackPressureBuffer策略)
     * onBackPressureBuffer:缓存未消费数据
     * onBackPressureError:无法及时消费消息报错
     * onBackPressureDrop:
     * onBackPressureLatest:只接受最新数据
     */
    @Test
    public void testBackPressure() throws InterruptedException {
        Executor executor = getDefaultExecutor();
        // 同步分发逻辑
        Flux<Integer> flux = Flux.range(0, 20);
        // 异步分发逻辑
        UnicastProcessor<Integer> processor = UnicastProcessor.create();
        CompletableFuture<Void> future_async = CompletableFuture.runAsync(() ->
                IntStream.range(0, 20).parallel().forEach(processor::onNext));
        // 消费逻辑
        BaseSubscriber<Integer> normalSubscriber = getDefaultSubscribe();

        int backPressureStrategy = 2;

        if (backPressureStrategy == 1) {
            /* error背压策略 */
            Flux<Integer> onError = flux.onBackpressureError();
//        Flux<Integer> onError = processor.publish().autoConnect().onBackpressureError();
            onError.subscribeOn(Schedulers.fromExecutor(executor)).subscribe(normalSubscriber);
        }

        if (backPressureStrategy == 2) {
            /* buffer背压策略 , 缓存队列溢出时 ERROR: 抛异常 DROP_OLDEST: 丢弃最旧的元素 DROP_LATEST: 丢弃最新的元素*/
            BufferOverflowStrategy strategy = BufferOverflowStrategy.ERROR;

            if (BufferOverflowStrategy.ERROR.equals(strategy)) {
                // ERROR 策略(默认策略)
//                Flux<Integer> onBufferOfError = processor.publish().autoConnect().onBackpressureBuffer(1);
//                        .subscribeOn(Schedulers.elastic());
                UnicastProcessor<String> sp = UnicastProcessor.create();
                FluxSink<String> onBufferOfError = sp.sink(FluxSink.OverflowStrategy.ERROR);
//                Flux<Integer> onBufferOfError = flux.subscribeOn(Schedulers.elastic()).onBackpressureBuffer(1); //缓存最大1条
//                onBufferOfError.subscribe(normalSubscriber);
                onBufferOfError.next("1");
                // 再次请求1条消息 由于缓存队列过小 此时会抛出 OverflowException
                normalSubscriber.request(1);
                normalSubscriber.request(1);
            }
            if (BufferOverflowStrategy.DROP_OLDEST.equals(strategy)) {
                // DROP_OLDEST 策略
                Flux<Integer> onBufferOfError = flux.onBackpressureBuffer(1, BufferOverflowStrategy.DROP_OLDEST);
                onBufferOfError.subscribe(normalSubscriber);
                // 再次请求1条消息 由于缓存队列过小 此时会抛弃旧元素
                normalSubscriber.request(1);
            }
            if (BufferOverflowStrategy.DROP_LATEST.equals(strategy)) {
                // DROP_LATEST 策略
                Flux<Integer> onBufferOfError = flux.onBackpressureBuffer(1, BufferOverflowStrategy.DROP_LATEST);
                onBufferOfError.subscribe(normalSubscriber);
                // 再次请求1条消息 由于缓存队列过小 此时会抛弃新元素
                normalSubscriber.request(1);
            }
        }

        if (backPressureStrategy == 3) {
            // onBackPressureLatest背压策略 获取最新数据,丢弃老数据(类似 BufferOverflowStrategy.DROP_OLDEST)
//            Flux<Integer> onBackpressureLatest = processor.publish().autoConnect().onBackpressureLatest();
            Flux<Integer> onBackpressureLatest = flux.onBackpressureLatest();
            onBackpressureLatest.subscribe(normalSubscriber);
            normalSubscriber.request(1);
        }

        if (backPressureStrategy == 4) {
            // drop背压策略会丢弃所有溢出的元素
            Flux<Integer> onBackpressureDrop = flux.onBackpressureDrop();
            onBackpressureDrop.subscribe(normalSubscriber);
            normalSubscriber.request(1);
        }

    }

    /**
     * 异步测试
     */
    @Test
    public void testSync(){
        ExecutorService executor = getDefaultExecutor();
        Flux<Integer> tFlux = Flux.<Integer>create(sink -> {
            IntStream.range(0,20).forEach(v -> {
//                executor.execute(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("push - " + v);
                sink.next(v);
//                });
            });
        }).subscribeOn(Schedulers.parallel()).publishOn(Schedulers.parallel());
//        tFlux.subscribe(getDefaultSlowerConsumer("first"),getDefaultErrorHandleConsumer());
//        tFlux.subscribe(getDefaultSlowerConsumer("second"),getDefaultErrorHandleConsumer());
//        tFlux.publishOn(Schedulers.elastic()).subscribe(getDefaultSlowerConsumer(),getDefaultErrorHandleConsumer());

        Flux<Integer> flux = Flux.<Integer>create(sink -> IntStream.range(0, 20)
                .forEach(v -> {
                    getDefaultExecutor().execute(() -> {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sink.next(v);
                    });
                }))
                .concatMap(v -> {
                    return Mono.just(v).subscribeOn(Schedulers.elastic());
                })

                //                .map(v -> {
//                    return v;
//                })
//                .concatMap(v -> {
//                    return Mono.just(v).subscribeOn(Schedulers.elastic());
//                },2)
                ;
//        flux.subscribe(getDefaultSlowerConsumer(),getDefaultErrorHandleConsumer());
    }
    
    /** 
     * 测试组件（需要引入包reactor-test）
     * StepVerifier
     * 如果与预期值不一致，则会抛出AssertionError的异常
     *
     *
     */
    @Test
    public void testVerifier() throws InterruptedException {

        Flux<String> just = Flux.just("foo", "bar");

        StepVerifier.create(appendError(just))
                    // 预期的下个onNext 消息
                    .expectNext("88","bar")
                    // 预期的错误信息
                    .expectErrorMessage("boom")
                    // 开始测试
                    .verify();

        Thread.sleep(20000);
    }

    /**
     * Reactor调试模式
     * Hooks.onOperatorDebug();
     *  开启全局的debug模式
     * checkpoint(); （之后的操作符）
     *  开启当前链路的debug模式
     *
     * 全局调试模式 需要添加jar -> reactor-tools
     *  之后需要初始化(一般在容器初始化之前)ReactorDebugAgent.init()
     *
     * 日志模式 : log()
     * 通过在链路上添加log()方法来
     *
     */
    @Test
    public void testReactorDebug(){
//        Hooks.onOperatorDebug();
        Flux<Integer> no = obtainErrorFluxCheckPoint();
        no.map(v -> v/2)
          .single()
          .subscribe(getDefaultConsumer());
        logger.info("start");
        /* test log */
//        Mono<Integer> take = Flux
//                .range(0, 10)
//                .log()
////                .log()
//                .map(v -> v/2)
////                .log()
//                .single() // 由take发起的cancel命令
//                .log();
//        take.subscribe(getDefaultConsumer());

    }

    /**
     * Reactor Context测试
     */
    @Test
    public void testReactorContext(){

    }

    public Flux<Integer> obtainErrorFluxCheckPoint(){
        Hooks.onOperatorDebug();
        return Flux.range(1, 4)
                .map(v -> 17/v)
                .flatMap(v -> {
                    return Flux.just(v);
                })
                .take(2)
//                .checkpoint("light",true)
//                               .concatWith(Mono.error(new RuntimeException("no")))
//                               .checkpoint("error-lines")
                ;
    }


    private <T> Flux<T> appendError(Flux<T> source){
        return source.concatWith(origin -> Mono.error(new IllegalArgumentException("bom")));
    }

    @Test
    public void testCompletableFuture() {
        CompletableFuture<List<String>> objectCompletableFuture = generateList().thenComposeAsync(v -> {
            v.stream().map(k -> {

                return k;
            });
            return null;
        });
        List<String> join = objectCompletableFuture.join();
    }

    /**
     * Reactor监控组件
     */
    @Test
    public void testReactorMetrics() throws InterruptedException {

        /* 启动线程监控程序，如果使用springboot 建议在SpringApplication.run()启动之前调用 */
        Schedulers.enableMetrics();

        Flux<Integer> tFlux = Flux.<Integer>create(sink -> {
                                        IntStream.range(0,20).forEach(v -> {
                                //                executor.execute(() -> {
                                            try {
                                                Thread.sleep(200);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            logger.info("push - " + v);
                                            sink.next(v);
                                //                });
                                        });
                                    })
                                  .name("metrics")
                                  .metrics()
                                  .subscribeOn(Schedulers.parallel())
                                  .publishOn(Schedulers.parallel())
                                  ;

        tFlux.doOnNext(event -> logger.info("receive event : " + event))
             .delayUntil(s -> {
                 logger.info("delayUnit : " + s);
                 return Mono.just(s);
             })
             .retry()
             .subscribe();
//        tFlux.subscribe(getDefaultSlowerConsumer("first"),getDefaultErrorHandleConsumer());

        Thread.sleep(100000);

    }

    AtomicInteger ai = new AtomicInteger();

    /** 
     * Reactor高级特性
     * 1. transform/transformDeferred:
     *  调用其他的操作链，transform和transformDeferred区别在于transformDeferred时多次订阅时会多次调用传入function的apply
     * 2. 冷/热 发布
     *  just就是热发布操作符： 在组装期(assembly time)就拿到了数据
     * 3. ConnectableFlux
     *  对多个订阅者进行广播
     *
     */
    @Test
    public void testAdvanceFeatures() throws InterruptedException {

        /* transform */
        Flux<String> transform = Flux.interval(Duration.ofMillis(500))
                .subscribeOn(Schedulers.parallel())
                .transform(filterAndMap(ai));
//        transform.subscribe(getDefaultConsumerCustomTip("Subscribe1"));
//        transform.subscribe(getDefaultConsumerCustomTip("Subscribe2"));

        Flux<String> summon = Flux.range(0, 3)
//                .transformDeferred(ai)
                .transform(generateHero(ai));
//        summon.subscribe(getDefaultConsumerCustomTip("Subscribe1"));
//        summon.subscribe(getDefaultConsumerCustomTip("Subscribe2"));

        AtomicInteger ai = new AtomicInteger();
        Function<Flux<String>, Flux<String>> filterAndMap = f -> {
            if (ai.incrementAndGet() == 1) {
                return f.filter(color -> !color.equals("orange"))
                        .map(String::toUpperCase);
            }
            return f.filter(color -> !color.equals("purple"))
                    .map(String::toUpperCase);
        };

        Flux<String> composedFlux =
                Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                        .doOnNext(System.out::println)
                        .transformDeferred(filterAndMap);

//        composedFlux.subscribe(d -> System.out.println("Subscriber 1 to Composed MapAndFilter :"+d));
//        composedFlux.subscribe(d -> System.out.println("Subscriber 2 to Composed MapAndFilter: "+d));

        /* 冷/热发布 */
        // 冷发布 冷发布 每次订阅都会将会消费所有发布消息
        Flux<String> source = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .doOnNext(System.out::println)
                .filter(s -> s.startsWith("o"))
                .map(String::toUpperCase)
                .log();
//        source.subscribe(d -> System.out.println("Subscriber 1: "+d));
//        source.subscribe(d -> System.out.println("Subscriber 2: "+d));

        // 热发布  热发布生产者在 订阅之前就运作了,消费者消费的消息取决于订阅时期
        UnicastProcessor<String> hotSource = UnicastProcessor.create();
        Flux<String> hotFlux = hotSource.publish()
                .autoConnect(2)
                .map(String::toUpperCase);
//        hotFlux.subscribe(getDefaultConsumerCustomTip("Subscribe1"));
        hotSource.onNext("blue");
        hotSource.onNext("green");
//        hotFlux.subscribe(getDefaultConsumerCustomTip("Subscribe2"));
        hotSource.onNext("orange");
        hotSource.onNext("purple");
        hotSource.onComplete();
        hotSource.onNext("pink");

        /* ConnectableFlux */
        Hooks.onOperatorDebug();
        // connect 执行connect之前的订阅
        ConnectableFlux<Integer> publish = Flux.<Integer>push(sink -> {
            IntStream.range(0, 1).forEach(sink::next);
        }).doOnSubscribe(s -> logger.info("on subscribe ...")).doOnCancel(() -> logger.info("on cancel ...")).publish();
//        ConnectableFlux<Long> publish = Flux.interval(Duration.ofMillis(200))
//                                             .doOnSubscribe(s -> logger.info("on subscribe ..."))
//                                             .publish();

        BaseSubscriber<Object> subscribe1 = getDefaultSubscribeTip("subscribe1");
//        publish.publishOn(Schedulers.parallel()).subscribe(subscribe1);

        BaseSubscriber<Object> subscribe2 = getDefaultSubscribeTip("subscribe2");
//        publish.publishOn(Schedulers.parallel()).subscribe(subscribe2);
//        publish.connect();

        //autoConnect autoConnect 会执行 之前和之后的订阅(和设定数量相等)
//        Flux<Integer> autoConnectPublish = publish.autoConnect(2);
//        autoConnectPublish.publishOn(Schedulers.parallel()).subscribe(getDefaultSubscribeTip("subscribe3"));
////        Thread.sleep(2000);
//        autoConnectPublish.publishOn(Schedulers.parallel()).subscribe(getDefaultSubscribeTip("subscribe4"));
////        Thread.sleep(5000);
//        autoConnectPublish.publishOn(Schedulers.parallel()).subscribe(getDefaultSubscribeTip("subscribe5"));


        //refCount
        Flux<Integer> refCountPublish = publish.refCount(1);
//        logger.info(refCountPublish.getClass().getName());

//
        logger.info("subscribe2 subscribed");
//        BaseSubscriber<Object> subscribe2 = getDefaultSubscribeTip("subscribe2");
        refCountPublish.log().subscribeOn(Schedulers.parallel()).subscribe(subscribe2);
        Thread.sleep(2000);

        logger.info("subscribe3 subscribed");
        BaseSubscriber<Object> subscribe3 = getDefaultSubscribeTip("subscribe3");
        refCountPublish.log().subscribeOn(Schedulers.parallel()).subscribe(subscribe3);
        Thread.sleep(2000);
//
        logger.info("subscribe4 subscribed");
        BaseSubscriber<Object> subscribe4 = getDefaultSubscribeTip("subscribe4");
        refCountPublish.log().subscribeOn(Schedulers.parallel()).subscribe(subscribe4);
        Thread.sleep(2000);
//        logger.info("cancel");
////        subscribe2.cancel();
////        subscribe3.cancel();
////        subscribe4.cancel();
//        Thread.sleep(2000);
//
//        logger.info("subscribe5 subscribed");
//        refCountPublish.subscribeOn(Schedulers.parallel()).subscribe(getDefaultSubscribeTip("subscribe5"));
////        Thread.sleep(2000);
//
//        logger.info("subscribe6 subscribed");
//        refCountPublish.subscribeOn(Schedulers.parallel()).subscribe(getDefaultSubscribeTip("subscribe6"));

        Thread.sleep(500000);
    }

   private Function<Flux<Long>, Flux<String>> filterAndMap(AtomicInteger ai){
       return f -> {
           if (ai.incrementAndGet() == 1) {
               return f.filter(color -> {
                   logger.info("color number : " + color);
                   return color == 1;
               })
                       .map(color -> "orange")
                       .map(String::toUpperCase);
           }
           return f.filter(color -> {
                logger.info("color number : " + color);
                return color != 1;
           })
                   .map(color -> "purple")
                   .map(String::toUpperCase);
       };
   }

   private Function<Flux<Integer>,Flux<String>> generateHero(AtomicInteger ai){

        logger.info("start invoke ..... ");
        logger.info("ai value : " + ai);
        List<String> heroList = new ArrayList<>();
        heroList.add("surtr");
        heroList.add("ldunn");
        heroList.add("reinhardt");
        heroList.add("celica");
        heroList.add("lyn");
        heroList.add("azura");
        return flux -> {
            logger.info("start function apply ....");
            return flux.map(v ->{
                logger.info("start map apply ...");
                return heroList.get(v);
            });
        };
   }


    public static CompletableFuture<List<String>> generateList() {
        CompletableFuture<List<String>> result = new CompletableFuture<>();
        ArrayList<String> value = new ArrayList<>();
        result.complete(value);
        return result;
    }

    private static ExecutorService getDefaultExecutor() {
        return Executors.newFixedThreadPool(40);
    }

    private static <T> Consumer<T> getDefaultConsumer(){
        return t -> logger.info("onNext value : " + t);
    }

    private static <T> Consumer<T> getDefaultConsumerCustomTip(String tip){
        return t -> logger.info(tip + " : onNext value : " + t);
    }

    private static <T> Consumer<T> getDefaultSlowerConsumer(String tag){
        return t -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info(tag + "-onNext value : " + t);
        };
    }

    private <T> Consumer<T> getDefaultErrorConsumer(){
        return t -> {
            throw new CustomException1("subscribe error");
        };
    }

    private static <T> Consumer<T> getDefaultErrorHandleConsumer(){
        return e -> logger.error("subscribe error : " + e);
    }



    private static <T> BaseSubscriber<T> getDefaultSubscribe() {
        return new BaseSubscriber<T>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                // 背压消费量
                this.request(1);
            }

            @Override
            protected void hookOnNext(T value) {
                logger.info("subscribe value : " + value);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                request(1);
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                throwable.printStackTrace();
            }
        };
    }

    private static <T> BaseSubscriber<T> getDefaultSubscribeTip(String tip) {
        return new BaseSubscriber<T>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                logger.info("subscribe-" + tip + ": on subscribe ...");
                // 背压消费量
                this.request(Integer.MAX_VALUE);
            }

            @Override
            protected void hookOnNext(T value) {
                logger.info(tip + " : onNext value : " + value);
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            protected void hookOnComplete() {
                logger.info(tip + ": onComplete ....");
            }

            @Override
            protected void hookOnCancel() {
                logger.info(tip + ": onCancel ....");
            }
        };
    }



}