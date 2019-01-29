package com.hiyouka.sources.core.processor;

import org.springframework.beans.BeansException;

/**
 * @author hiyouka
 * Date: 2019/1/29
 * @since JDK 1.8
 */
public interface BeanPostProcessor {

    
    /**
     *
     * Apply this BeanPostProcessor to the given new bean instance <i>before</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     * @param bean
     * @param beanName
     * @return:java.lang.Object
     * @Date: 2019/1/29
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    /**
     *
     * Apply this BeanPostProcessor to the given new bean instance <i>after</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method).
     * @param bean
     * @param beanName
     * @return:java.lang.Object
     * @Date: 2019/1/29
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


}