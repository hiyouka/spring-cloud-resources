package com.hiyouka.sources;

/**
 * @author hiyouka
 * Date: 2019/1/16
 * @since JDK 1.8
 */

import org.springframework.boot.SpringApplication;

/**
 * 1、导入依赖；
 * 		1）、导入dubbo-starter
 * 		2）、导入dubbo的其他依赖
 * @author lfy
 *
 * SpringBoot与dubbo整合的三种方式：
 * 1）、导入dubbo-starter，在application.properties配置属性，使用@service【暴露服务】使用@Reference【引用服务】
 * 2）、保留dubbo xml配置文件;
 * 		导入dubbo-starter，使用@ImportResource导入dubbo的配置文件即可
 * 3）、使用注解API的方式：
 * 		将每一个组件手动创建到容器中,让dubbo来扫描其他的组件
 */
//@EnableDubbo //开启基于注解的dubbo功能
//@ImportResource(locations="classpath:provider.xml")
//@EnableDubbo(scanBasePackages="com.hiyouka.sources.api")
//@EnableHystrix //开启服务容错
//@SpringBootApplication
public class SpringBootServer {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootServer.class, args);
    }

}