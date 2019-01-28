package com.hiyouka.sources.util;

import com.hiyouka.sources.config.TestBeanPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
//@Component
public class ClassUtils extends SupClassU{

    @Autowired
    private TestBeanPostProcessor testBeanPostProcessor;

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
}