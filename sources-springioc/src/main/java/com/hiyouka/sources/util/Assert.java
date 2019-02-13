package com.hiyouka.sources.util;


/**
 * @author hiyouka
 * Date: 2019/1/28
 * @since JDK 1.8
 */
public class Assert {

    public static void notEmpty( Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }


    public static void notEmpty( Object item, String message) {
        if (ObjectUtils.isEmpty(item)) {
            throw new IllegalArgumentException(message);
        }
    }
}