package com.hiyouka.sources.demo.seedframework.context;

import com.hiyouka.sources.demo.seedframework.comp.ImportClassToBean;
import hiyouka.seedframework.beans.annotation.Component;
import hiyouka.seedframework.beans.annotation.Import;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Import(ImportClassToBean.class)
public class BeanClass {


    @Component
    public static class InnerStaticClass{

    }

}