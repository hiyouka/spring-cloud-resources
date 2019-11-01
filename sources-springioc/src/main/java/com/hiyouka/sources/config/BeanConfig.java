package com.hiyouka.sources.config;

import com.hiyouka.sources.config.test.ClassUtils;
import com.hiyouka.sources.config.test.ImportTestClass;
import com.hiyouka.sources.config.test.TestImportBeanDefinitionRegistrar;
import com.hiyouka.sources.config.test.TestImportSelector;
import com.hiyouka.sources.core.annotation.Scope;
import org.springframework.context.annotation.*;

/**
 * @author hiyouka
 * Date: 2019/1/23
 * @since JDK 1.8
 */
@Configuration
//@ComponentScan({"com.hiyouka.sources.util"})
//@ComponentScan({"com.hiyouka.sources.config","com.hiyouka.sources.util"})
@Import({ImportTestClass.class,TestImportSelector.class,TestImportBeanDefinitionRegistrar.class})
public class BeanConfig {


    @Bean(initMethod = "init")
    @Lazy(false)
    @Scope
    @Primary
//    @Conditional(OnBean.class)
    public ClassUtils classUtils(){
        org.springframework.util.ClassUtils
        return new ClassUtils();
    }

}