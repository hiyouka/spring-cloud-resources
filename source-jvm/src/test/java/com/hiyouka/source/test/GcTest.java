package com.hiyouka.source.test;

import lombok.Data;
import org.junit.Test;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class GcTest {

    @Data
    class Reference<T>{

        private Byte[] bytes = new Byte[1024 * 1024];

        private T reference;

    }

    /**
     * -XX:+PrintGC             输出GC日志
     * -XX:+PrintGCDetails      输出GC的详细日志
     * -XX:+PrintGCDateStamps   输出GC的日期
     * -XX:+PrintGCTimeStamps   输出GC的时间戳
     * -XX:+PrintHeapAtGC       在进行GC前后打印出堆的信息
     * -Xloggc:../logs/gc.log   日志文件的输出路径
     */
    @Test
    public void testGc(){
        Reference<Reference> reference1 = new Reference<>();
        Reference<Reference> reference2 = new Reference<>();
        reference1.setReference(reference2);
        reference2.setReference(reference1);
        reference1 = null;
        reference2 = null;
        System.gc();
    }

    public void testEscapedGc(){

    }

    /**
     *  查看java使用垃圾收集器
     *  java -XX:+PrintCommandLineFlags -version
     *  标记-清除算法
     */
    @Test
    public void markAndSweepAlgorithm(){
        List<java.lang.management.GarbageCollectorMXBean> l = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean b : l) {
            System.out.println(b.getName());
        }
    }

}