package com.hiyouka.charptertwo;

import org.junit.Test;

import java.io.IOException;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class Main {

    @Test
    public void testAutoClose() throws IOException {
        new AutoCloseResourceTest().test();
    }


}