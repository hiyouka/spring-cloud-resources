package com.hiyouka.sources.config.test;

import com.hiyouka.sources.exception.BeanDefinitionStoreCoreException;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author hiyouka
 * Date: 2019/2/19
 * @since JDK 1.8
 */
public class TestImportSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{BeanDefinitionStoreCoreException.class.getName()};
    }
}