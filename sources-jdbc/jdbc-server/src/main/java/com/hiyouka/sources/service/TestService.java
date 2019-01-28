package com.hiyouka.sources.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hiyouka
 * Date: 2019/1/25
 * @since JDK 1.8
 */
@Service
public class TestService {

    @Autowired
    private AutowiredService autowiredService;

    public void sys(){
        System.out.println("ok");
    }

}