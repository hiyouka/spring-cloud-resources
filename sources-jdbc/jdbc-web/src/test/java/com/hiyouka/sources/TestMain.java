package com.hiyouka.sources;

import com.hiyouka.sources.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Main.class)
@RunWith(SpringRunner.class)
public class TestMain {

    @Autowired
    private TestService testService;

        @Test
        public void test(){
            testService.sys();
            System.out.println(testService.getClass());
            System.out.println("======================"+"537200805317443584".length());
        }

}
