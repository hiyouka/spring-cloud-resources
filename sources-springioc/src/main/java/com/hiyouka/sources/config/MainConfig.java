package com.hiyouka.sources.config;

import com.hiyouka.sources.constant.EncodeConstant;
import com.hiyouka.sources.exception.SeedCoreException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author hiyouka
 * Date: 2019/1/23
 * @since JDK 1.8
 */

/**
 * rabbitMq springboot
 * RabbitAutoConfiguration 自动配置
 * 连接工厂CachingConnectionFactory
 * RabbitProperties封装了springmq的所有配置 spring.rabbitmq
 * RabbitTemplate 用于给rabbitmq发送接收消息
 * AmqpAdmin RabbitMQ系统管理工具 用于创建对列
 */


@Configuration
//@ComponentScan({"com.hiyouka.sources.config"})
@PropertySource({"classpath:/datasource.properties"})
//@EnableTransactionManagement
@ComponentScan({"com.hiyouka.sources.config","com.hiyouka.sources.util"})
public class MainConfig implements EncodeConstant{

    @Value("${token.encrypt.salt}")
    private String salt;

    @Bean
    public SeedCoreException seedCoreException(){
        return new SeedCoreException("new");
    }


}