package com.hiyouka.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
//@Configuration
//@EnableCaching
public class CacheConfig{

    @Value("#{10000}")
    private int defaultExpireTime;


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
//        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
//        // 设置缓存管理器管理的缓存的默认过期时间
//        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(defaultExpireTime))
//                // 设置 key为string序列化
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                // 设置value为json序列化
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
//                // 不缓存空值
//                .disableCachingNullValues();
//        // 对每个缓存空间应用不同的配置
//        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
//        configMap.put(testCacheName, defaultCacheConfig.entryTtl(Duration.ofSeconds(testExpireTime)));

       return RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(defaultCacheConfig)
//                .initialCacheNames(cacheNames)
//                .withInitialCacheConfigurations(configMap)
                .build();
    }

}