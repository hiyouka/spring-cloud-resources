package com.hiyouka.source.resilience4j.example;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class CircuitBreakerExample {



    interface RemoteService {
        int process();
    }

    @Test
    public void log(){
        Logger db = LoggerFactory.getLogger("db");
        db.info("hhhh");
    }



    @Test
    public void circuitBeakerTest(){
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        CircuitBreaker circuitBreaker = registry.circuitBreaker("my");
        RemoteService service = () -> {
            System.out.println(" service process execute");
            throw new RuntimeException("error ");
        };
//        Function<Integer, Integer> decorated = CircuitBreaker
//                .decorateFunction(circuitBreaker, service::process);
////        when(service.process(any(Integer.class))).thenThrow(new RuntimeException());
//        for (int i = 0; i < 10; i++) {
//            try {
//                decorated.apply();
//            } catch (Exception ignore) {
//                ignore.printStackTrace();
//            }
//        }

        Supplier<Integer> integerSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, service::process);

        CheckedFunction0<Object> objectCheckedFunction0 = CircuitBreaker.decorateCheckedSupplier(circuitBreaker, service::process);

        Try<Object> recover = Try.of(objectCheckedFunction0).recover(throwable -> "hhhh");

        Object o = recover.get();

        System.out.println(o);

//        verify(service, times(5)).process(any(Integer.class));

    }

}