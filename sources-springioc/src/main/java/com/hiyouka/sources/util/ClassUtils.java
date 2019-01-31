package com.hiyouka.sources.util;

import com.hiyouka.sources.config.TestBeanPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
public class ClassUtils extends SupClassU{

    @Autowired
    private TestBeanPostProcessor testBeanPostProcessor;

    @Autowired
    private ApplicationContext applicationContext;


    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public TestBeanPostProcessor getTestBeanPostProcessor() {
        return testBeanPostProcessor;
    }

    public void setTestBeanPostProcessor(TestBeanPostProcessor testBeanPostProcessor) {
        this.testBeanPostProcessor = testBeanPostProcessor;
    }

    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public static Class<?> forName(String className, ClassLoader classLoader) {
        return null;
    }



    /**
     * get thread ClassLoader Object
     * @param
     * @return:java.lang.ClassLoader default ClassLoader
     * @Date: 2019/1/29
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                }
                catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl;
    }
}