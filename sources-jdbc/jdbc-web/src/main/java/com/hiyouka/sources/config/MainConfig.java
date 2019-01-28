package com.hiyouka.sources.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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


//@Configuration
@ComponentScan({"com.hiyouka.sources.config","com.hiyouka.sources.controller","com.hiyouka.sources.service"})
@PropertySource({"classpath:/datasource.properties"})
@EnableTransactionManagement
public class MainConfig {



}