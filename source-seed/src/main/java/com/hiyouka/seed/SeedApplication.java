package com.hiyouka.seed;

import com.hiyouka.seed.bean.*;
import seed.seedframework.beans.annotation.Autowired;
import seed.seedframework.beans.annotation.Bean;
import seed.seedframework.beans.annotation.Import;
import seed.seedframework.context.annotation.ComponentScan;
import seed.seedframework.context.annotation.Configuration;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Configuration
@ComponentScan("com.hiyouka.seed")
@Import(TestAutowired.class)
public class SeedApplication {


    @Bean("testBeanOfManual")
    public TestBean1<String,Object> stringObjectTestBean1(){
        return new TestBean1<>();
    }

    @Bean("testAutowiredBean")
    public TestAutowiredBean testAutowiredBean(@Autowired TestFather1<Test1,Test1> testFather1, Test1 test1){
        TestAutowiredBean testAutowiredBean = new TestAutowiredBean();
        testAutowiredBean.setTestFather(testFather1);
        testAutowiredBean.setTest1(test1);
        return  testAutowiredBean;
    }

//    @Bean("testAutowired2")
//    public TestAutowired testAutowired(TestBean1<String,Object> testBean2){
//        return new TestAutowired(testBean2);
//    }


}