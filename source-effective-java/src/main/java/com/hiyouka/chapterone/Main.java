package com.hiyouka.chapterone;

import com.hiyouka.base.BaseTestInterface;
import org.junit.Test;

import java.util.function.Supplier;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class Main implements BaseTestInterface{

    @Test
    public void testStaticFactory() throws Throwable {
        //        Map<String,Object> entry = new HashMap<>();
        //        entry.put("123","123");
        //        System.out.println(entry.keySet());
        //        entry.put("234","234");
        //        System.out.println(entry.keySet());
        long l = System.currentTimeMillis();
        long sum = 0L;

        for(long i = 0L ; i< Integer.MAX_VALUE; i++){
            sum += i;
        }

        System.out.println(System.currentTimeMillis() - l);

    }

    class Sut implements Supplier{

        @Override
        public Object get() {
            return new Sut();
        }
    }

}