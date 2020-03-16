package hiyouka.reactor.rx.demo;

import hiyouka.reactor.rx.entity.User;
import io.reactivex.Flowable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class FlowableDemoImpl implements FlowableDemo {

    @Override
    public Flowable<User> findAllPeople1(String type) {
        return Flowable.interval(1, TimeUnit.SECONDS)
                .map(timestamp -> new User((int) (timestamp % 1000), "nick:" + type));

    }

    @Override
    public Flux<User> findAllPeople2(String type) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(v -> new User(v.intValue(), "nick:" + type));
    }


    public static void main(String[] args) {
        FlowableDemoImpl flowableDemo = new FlowableDemoImpl();
        Flowable<User> allPeople = flowableDemo.findAllPeople1("123");
        Iterable<User> users = allPeople.blockingIterable();
        users.forEach(v -> {
            System.out.println(v);
        });
        System.out.println();
    }

}