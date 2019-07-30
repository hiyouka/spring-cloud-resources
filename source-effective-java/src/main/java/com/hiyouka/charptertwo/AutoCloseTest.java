package com.hiyouka.charptertwo;

import com.hiyouka.base.BaseTestInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class AutoCloseTest extends FileInputStream implements BaseTestInterface, AutoCloseable{

    private boolean exception = true;

    public AutoCloseTest(String name) throws FileNotFoundException {
        super(name);
    }

    @Override
    public int read() throws IOException {
        if(exception){
            throw new IOException();
        }
        return super.read();
    }

    @Override
    public void close() throws IOException {
        getLogger().info("close method start .....");
        if(exception){
            throw new IOException();
        }
        super.close();
        getLogger().info("close method end .......");
    }


}