package com.hiyouka.sources.exception;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
public class BeansException extends RuntimeException {

    private final static String message = "get Bean error !!";

    public BeansException() {
        super(message);
    }

}