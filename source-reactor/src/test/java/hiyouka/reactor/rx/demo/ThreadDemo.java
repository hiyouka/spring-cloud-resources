package hiyouka.reactor.rx.demo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class ThreadDemo {

    ThreadLocal<String> threadLocal = new ThreadLocal<>();
    ExecutorService executor = Executors.newFixedThreadPool(2);
    private Logger logger = LoggerFactory.getLogger(ThreadDemo.class);

    @Test
    public void test() throws InterruptedException {
        final int[] i = {1};
        while (true){
            executor.execute(() ->{
                if(i[0] == 1){
                    threadLocal.set("value");
                    i[0] = 2;
                }
                logger.info(Thread.currentThread().getName() + "-" + threadLocal.get());
            });
            Thread.sleep(1000);
        }
    }


}