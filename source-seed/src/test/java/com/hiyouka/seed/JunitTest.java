package com.hiyouka.seed;

import com.hiyouka.seed.bean.TestAutowired;
import com.hiyouka.seed.bean.TestBean1;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seed.seedframework.context.AnnotationConfigApplicationContext;
import seed.seedframework.context.ApplicationContext;
import seed.seedframework.core.env.Environment;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class JunitTest {

    public static Logger logger = LoggerFactory.getLogger(JunitTest.class);

    @Test
    public void seedContextTest(){
        logger.info("start ");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SeedApplication.class);
        Object testAutowired = applicationContext.getBean("testAutowired");
        TestAutowired bean = applicationContext.getBean(TestAutowired.class);
        bean.testB(null);
        TestBean1<String, Object> testBean2 = bean.getTestBean2();
        bean.testB(null);
        System.out.println(bean.getClass());
        Environment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("spring.aop.auto");
        System.out.println(property);

        Son son = new Son();
        son.addIndex();
        Son son1 = new Son();
        son1.printIndex();
    }


    class Father{

        protected volatile int index;

        public void addIndex(){
            index++;
        }

    }


    class Son extends Father{

        public void printIndex(){
            System.out.println(super.index);
        }

    }

}