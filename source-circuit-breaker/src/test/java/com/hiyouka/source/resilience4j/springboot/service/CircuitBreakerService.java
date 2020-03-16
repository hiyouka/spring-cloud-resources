package com.hiyouka.source.resilience4j.springboot.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Service
public class CircuitBreakerService {

    @CircuitBreaker(name = "circuitA")
    public String testCircuit(){
        throw new RuntimeException();
    }



}