package com.hiyouka.source.test;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class TransactionTest {

    public static void main(String[] args) {
        new Thread(() -> System.out.println(Thread.currentThread().getId() + "thread run ...........")).start();
        new Thread(() -> System.out.println(Thread.currentThread().getId() + "thread run ...........")).start();
        new Thread(() -> System.out.println(Thread.currentThread().getId() + "thread run ...........")).start();
    }


    public void test(){
//        TransactionFactory

    }

}