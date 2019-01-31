package com.hiyouka.sources.util;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author hiyouka
 * Date: 2019/1/30
 * @since JDK 1.8
 */
public class OnBean extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        ClassUtils bean = beanFactory.getBean(ClassUtils.class);
        if(bean == null){
            return ConditionOutcome.match("ok");
        }else {
            return ConditionOutcome.noMatch("already exist this type bean");
        }
    }
}