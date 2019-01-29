package com.hiyouka.sources.exception;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
public class BeanDefinitionStoreException extends RuntimeException {

    private final static String message = "BeanDefinition invalid !!";

    public BeanDefinitionStoreException() {
        super(message);
    }

    public BeanDefinitionStoreException(String message) {
        super(message);
    }

}