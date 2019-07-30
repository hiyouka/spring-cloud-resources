package com.hiyouka.source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@SpringBootApplication
@EnableTransactionManagement
public class TransactionMain {

    public static void main(String[] args) {
        SpringApplication.run(TransactionMain.class);
    }

}