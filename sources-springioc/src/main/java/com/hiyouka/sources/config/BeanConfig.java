package com.hiyouka.sources.config;

import com.hiyouka.sources.config.test.ImportTestClass;
import com.hiyouka.sources.config.test.TestImportBeanDefinitionRegistrar;
import com.hiyouka.sources.config.test.TestImportSelector;
import com.hiyouka.sources.core.annotation.Scope;
import com.hiyouka.sources.config.test.ClassUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

/**
 * @author hiyouka
 * Date: 2019/1/23
 * @since JDK 1.8
 */
@Configuration
//@ComponentScan({"com.hiyouka.sources.util"})
@Import({ImportTestClass.class,TestImportSelector.class,TestImportBeanDefinitionRegistrar.class})
public class BeanConfig {


    @Bean(initMethod = "init")
    @Lazy(false)
    @Scope
//    @Conditional(OnBean.class)
    public ClassUtils classUtils(){
        return new ClassUtils();
    }

}