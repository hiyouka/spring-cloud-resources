package com.source.hessian;

import org.junit.Test;

/**
 * study hessian
 * @author hiyouka
 * @since JDK 1.8
 */
public class HessianTest {

    @Test
    public void unicode(){

        int yiFirst = 0x4DC0;
        for(int i = 0; i< 64; i++){
            System.out.println((char) (yiFirst));
            yiFirst++;
        }

    }

}