package com.hiyouka.sources.util;

import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * Date: 2019/2/13
 * @since JDK 1.8
 */
@Component
public class SeedFactoryBean implements org.springframework.beans.factory.FactoryBean<ClassUtils> {

    @Override
    public ClassUtils getObject() throws Exception {
        return new ClassUtils();
    }

    @Override
    public Class<?> getObjectType() {
        return ClassUtils.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}