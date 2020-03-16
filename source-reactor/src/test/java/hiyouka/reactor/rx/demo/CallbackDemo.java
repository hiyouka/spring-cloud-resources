package hiyouka.reactor.rx.demo;

import hiyouka.reactor.rx.entity.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class CallbackDemo {

    private static final Logger logger = LoggerFactory.getLogger(CallbackDemo.class);



    @Test
    public void obtainCall(){
        UserClass userClass = new UserClass();
        Callback<List<User>> allUser = userClass.getAllUser();
        List<User> result = new ArrayList<>();
        List<User> call = blockingCall(allUser, result);
        System.out.println(call);
    }

    @Test
    public void testCall() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(50000);
                return "ok";
            }
        };
        Future<String> submit = executor.submit(callable);
        String s = submit.get();
    }

    private class UserClass{

        public Callback<List<User>> getAllUser(){
            return new Callback<List<User>>() {
                @Override
                public void onSuccess(List<User> users) {
                    logger.info("get user list");
                    users.add(new User(1,"roach"));
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(Throwable e) {
                    logger.error("get User error");
                }
            };
        }

    }


    /**
     * 阻塞获取结果
     */
    public static  <T> T blockingCall(Callback<T> callback, T result){
        T v;
        Thread t = new Thread(()->{
            try{
                callback.onSuccess(result);
            }catch (Exception e){
                callback.onError(e);

            }
        });
        t.start();
        // wait result
        while (!Thread.State.TERMINATED.equals(t.getState())){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                logger.error("task wait error");
                throw new RuntimeException("Thread sleep error");
            }
        }
        logger.info("task executor finish");
        v = result;
        return v;
    }

}