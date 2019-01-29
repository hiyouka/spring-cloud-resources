package com.hiyouka.sources.core.factory;

/**
 * @author hiyouka
 * Date: 2019/1/29
 * @since JDK 1.8
 */
// 自动装配的设置工程方法 : 暂时不实现这块
public interface ConfigurableListableBeanFactory {

    void registerResolvableDependency(Class<?> dependencyType,Object autowiredValue);


    /**
     * ignore the given dependency interface for autowiring.
     */
    void ignoreDependencyInterface(Class<?> ifc);

    /**
     * Ignore the given dependency type for autowiring:
     * for example, String. Default is none.
     * @param type the dependency type to ignore
     */
    void ignoreDependencyType(Class<?> type);
}