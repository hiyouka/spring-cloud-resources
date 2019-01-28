package com.hiyouka.sources.core.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
public class AnnotractionDefaultBeanDefinition extends AbstractBeanDefinition implements AnnotationBeanDefinition {

    private Set<String> annotations = new HashSet<>(32);

    @Override
    public Set<String> getAnnotationTypes() {
        return annotations;
    }


    @Override
    public boolean hasAnnotation(String annotationName) {
        for(String name : annotations){
            if(name.equals(annotationName))
                return true;
        }
        return false;
    }

    @Override
    public void addAnnotionType(String name) {

    }


    @Override
    public void setScope(String scope) {

    }

    @Override
    public String getScope() {
        return null;
    }

    @Override
    public void setSingleton(boolean primary) {

    }


    @Override
    public void setPrototype(boolean prototype) {

    }

    @Override
    public void setAttribute(String name, Object value) {

    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public Object removeAttribute(String name) {
        return null;
    }

    @Override
    public boolean hasAttribute(String name) {
        return false;
    }

    @Override
    public String[] attributeNames() {
        return new String[0];
    }
}