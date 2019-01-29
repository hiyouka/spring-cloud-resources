package com.hiyouka.sources;

import com.hiyouka.sources.config.MainConfig;
import com.hiyouka.sources.util.ClassUtils;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.URL;
import java.util.Arrays;

//@SpringBootTest(classes = Main.class)
//@RunWith(SpringRunner.class)
public class TestMain {

//    @Autowired
//    private Object object;
    @Autowired
    private BeanFactory beanFactory;

    @Test
    public void test() throws ClassNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("com.hiyouka.sources.config.MainConfig");
        ClassLoader parent = classLoader.getParent();
        System.out.println((parent == classLoader) + "===========================");
        System.out.println(resource);
        System.out.println(123);
        Class<?> aClass = classLoader.loadClass("com.hiyouka.sources.config.MainConfig");
        classLoader.getResource("");
//        org.springframework.context.ApplicationContext application = ApplicationContext.getApplication();
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(MainConfig.class);
        BeanFactory beanFactory = application.getParentBeanFactory();
        AnnotationConfigApplicationContext realApplication = (AnnotationConfigApplicationContext) application;
        ConfigurableListableBeanFactory realBeanFactory = realApplication.getBeanFactory();
        System.out.println("realBeanFactory:"+realBeanFactory);
        System.out.println("beanFactory:"+beanFactory);
        BeanDefinition classUtils = ((AnnotationConfigApplicationContext) application).getBeanDefinition("classUtils");
        ClassUtils classUtil = application.getBean("classUtils", ClassUtils.class);
        ApplicationContext applicationContext = classUtil.getApplicationContext();
        Object classUtils1 = applicationContext.getBean("classUtils");
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
        String[] beanDefinitionNames = realBeanFactory.getBeanDefinitionNames();
        System.out.println(Arrays.asList(beanDefinitionNames));

        // create ioc context
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
//        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
//        String[] testBeanPostProcessors = beanFactory.getAliases("classUtils");
//        System.out.println("bean Aliases is : " + Arrays.asList(testBeanPostProcessors));
//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        System.out.println("bean definition is : " + Arrays.asList(beanDefinitionNames));
//        applicationContext.close();
    }



}
