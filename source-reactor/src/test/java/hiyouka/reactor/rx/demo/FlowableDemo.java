package hiyouka.reactor.rx.demo;

import hiyouka.reactor.rx.entity.User;
import io.reactivex.Flowable;
import reactor.core.publisher.Flux;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public interface FlowableDemo {

    Flowable<User> findAllPeople1(String type);

    Flux<User> findAllPeople2(String type);

}