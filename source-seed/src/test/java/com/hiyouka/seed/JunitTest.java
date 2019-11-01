package com.hiyouka.seed;

import com.hiyouka.seed.bean.TestAutowired;
import org.junit.Test;
import seed.seedframework.context.AnnotationConfigApplicationContext;
import seed.seedframework.context.ApplicationContext;
import seed.seedframework.core.env.Environment;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class JunitTest {

    @Test
    public void seedContextTest(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SeedApplication.class);
//        Object testAutowired = applicationContext.getBean("testAutowired");
        TestAutowired bean = applicationContext.getBean(TestAutowired.class);
        System.out.println(bean.getClass());
        bean.testB(null);
        Environment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("spring.aop.auto");
        System.out.println(property);
    }

}