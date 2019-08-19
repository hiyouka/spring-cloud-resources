package com.hiyouka.source;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@SpringBootTest(classes = TransactionMain.class)
@RunWith(SpringRunner.class)
public class TransactionTest implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Autowired
    private Environment environment;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test(){
//        if(beanFactory instanceof ListableBeanFactory){
//            String[] beanDefinitionNames = ((ListableBeanFactory) beanFactory).getBeanDefinitionNames();
//            for(String beanName : beanDefinitionNames){
//                logger.info("beanName:" + beanName);
//            }
//        }
        String ios = environment.getProperty("ios.key.video");
        System.out.println("ok");
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}