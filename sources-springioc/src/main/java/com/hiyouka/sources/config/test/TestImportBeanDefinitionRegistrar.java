package com.hiyouka.sources.config.test;

import com.hiyouka.sources.constant.SeedConstant;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author hiyouka
 * Date: 2019/2/19
 * @since JDK 1.8
 */
public class TestImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition("seedConstant", new RootBeanDefinition(SeedConstant.class));
    }
}