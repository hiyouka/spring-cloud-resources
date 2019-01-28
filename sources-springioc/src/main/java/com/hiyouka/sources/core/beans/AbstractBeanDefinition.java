package com.hiyouka.sources.core.beans;

import com.hiyouka.sources.core.beans.definition.BeanDefinition;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
public abstract class AbstractBeanDefinition implements BeanDefinition {

    private String beanClassName;

    private Class<?> beanClass;

    private String beanName;

    private String initMethod;

    private String destroyMethod;

    private boolean primary;

    private boolean singleton;

    private boolean prototype;

    private boolean lazyInit;


    @Override
    public boolean isPrimary() {
        return this.primary;
    }

    @Override
    public boolean isSingleton() {
        return this.singleton;
    }

    @Override
    public boolean isPrototype() {
        return this.prototype;
    }


    @Override
    public boolean isLazyInit() {
        return this.lazyInit;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }


    public String getBeanName() {
        return beanName;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public String getDestroyMethod() {
        return destroyMethod;
    }

    public void setDestroyMethod(String destroyMethod) {
        this.destroyMethod = destroyMethod;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public Class<?> getBeanClass() {
        return this.beanClass;
    }



}