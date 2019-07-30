package com.hiyouka.sources.demo.seedframework.context;

import hiyouka.seedframework.beans.annotation.Import;
import hiyouka.seedframework.context.annotation.ComponentScan;
import hiyouka.seedframework.context.annotation.Configuration;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Configuration
@ComponentScan("hiyouka.seedframework.context")
@Import({ImportClass.class,ImportClass2.class,BeanConfig.class})
//@PropertySources("classpath:/test.properties")
public class ConfigClass {



}