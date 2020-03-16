package hiyouka.reactor.rx.demo;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public interface Callback<T> {

    void onSuccess(T t);

    void onError(Throwable e);

}