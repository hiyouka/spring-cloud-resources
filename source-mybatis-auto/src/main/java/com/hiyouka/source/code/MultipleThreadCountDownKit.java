package com.hiyouka.source.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Component
public class MultipleThreadCountDownKit {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


}