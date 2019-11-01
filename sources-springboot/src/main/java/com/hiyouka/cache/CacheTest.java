package com.hiyouka.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
//@CacheConfig(cacheNames = "tm")
//@Component
public class CacheTest {

    @Cacheable(key = "#cacheName")
    public String cacheGetTest(String cacheName){
        return System.currentTimeMillis()+cacheName;
    }


    @CachePut(key = "#cacheName")
    public String cachePutTest(String cacheName){
        return System.currentTimeMillis()+cacheName;
    }

    @CacheEvict(key = "#cacheName")
    public void cacheDeleteTest(String cacheName){}



}