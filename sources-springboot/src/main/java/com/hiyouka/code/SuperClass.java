package com.hiyouka.code;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class SuperClass<E,F> extends BaseService<E> implements TestInterface1<F,Object>,TestInterface2<E,String>{

    @Override
    public void test(E o) {
        super.test(o);
    }
}