package com.hiyouka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Configuration
//@ConditionalOnClass(RedisOperations.class)
//@EnableConfigurationProperties(RedisProperties.class)
@hiyouka.seedframework.context.annotation.Configuration
public class RedisConfig {

    @Bean
    @ConditionalOnMissingBean(name ="redisTemplate")

    @hiyouka.seedframework.beans.annotation.Bean
    public RedisTemplate<Object, Object> redisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)

    @hiyouka.seedframework.beans.annotation.Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}