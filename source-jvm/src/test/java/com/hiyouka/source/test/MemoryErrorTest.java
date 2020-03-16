package com.hiyouka.source.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class MemoryErrorTest {

    Logger logger = LoggerFactory.getLogger(MemoryErrorTest.class);

    class ListBind{

        private List<Object> outTim = new ArrayList<Object>();

        public ListBind(){
            this.add();
        }

        public void add(){
            outTim.add(new ListBind());
        }

    }

    class OOM{

    }


    /**
     * 堆溢出
     * -Xms20m  堆最小空间
     * -Xmx20m  对最大空间
     * -XX:+HeapDumpOnOutOfMemoryError  当出现内存不足时dump堆信息
     */
    @Test
    public void outOfMemoryError(){
//        ListBind listBind = new ListBind();
        List<Object> list = new ArrayList<>();
        while (true){
            new OOM();
//            list.add(new OOM());
        }
        // 2m
//        byte[] buff = new byte[1024 * 1024 * 2];


    }

    class SOE{

        private int stackLength = 0;

        public void loop(){
            stackLength ++;
            while (true){}
//            loop();
        }

    }


    /**
     * 栈溢出
     * -Xss108k 设置虚拟机栈大小
     * -Xoss108k 设置本地方法栈大小 （栈容量只由-Xss参数设定）
     */
    @Test
    public void stackOverflowError(){
        final SOE soe = new SOE();
        while (true){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    soe.loop();
                }
            }).start();
        }
//        SOE soe = new SOE();
//        try {
//            soe.loop();
//        }catch (Throwable e){
//            System.out.println("stack length : " + soe.stackLength);
//            throw e;
//        }
    }

    /**
     * 元数据溢出
     * -XX:MetaspaceSize=10m    元数据区大小
     * -XX:MaxMetaspaceSize=10m 元数据区最大内存
     *
     *  1.8 之前内存分区
     * -XX:PermSize=10m     方法区大小
     * -XX:MaxPermSize=10m  方法区最大内存
     *
     */
    @Test
    public void oomForConstantPool(){
        int i = 0;
        while (true){
            String.valueOf(i++).intern();
        }
//
//        String s1 = new StringBuffer("computer").append("soft").toString();
//        System.out.println(s1.intern() == s1);
//
//        String s2 = new StringBuffer("computer").append("soft").toString();
//        System.out.println(s2.intern() == s2);
    }


    /**
     * DirectMemory java向系统直接申请的内存
     * DirectMemory容量不指定默认和java堆最大值一样
     * -XX:MaxDirectMemorySize=10m
     *
     */
    @Test
    public void directMemory() throws IllegalAccessException {
        OOM oom = new OOM();
        Field unSafeFiled = Unsafe.class.getDeclaredFields()[0];
        unSafeFiled.setAccessible(true);
        Unsafe unsafe = (Unsafe) unSafeFiled.get(null);
        while (true){
            unsafe.allocateMemory(1024*1024);
        }
    }


}