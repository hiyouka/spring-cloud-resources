package com.hiyouka.sources.exception;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
public class NoSuchBeanDefinitionException extends RuntimeException {

    private final static String message = "not find BeanDefinition error !!";

    public NoSuchBeanDefinitionException() {
        super(message);
    }

    public NoSuchBeanDefinitionException(String message) {
        super(message);
    }

}