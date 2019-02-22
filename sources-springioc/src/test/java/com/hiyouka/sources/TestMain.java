package com.hiyouka.sources;

import com.hiyouka.sources.config.MainConfig;
import com.hiyouka.sources.constant.EncodeConstant;
import com.hiyouka.sources.demo.AnnoDemo;
import com.hiyouka.sources.config.test.ClassUtils;
import com.hiyouka.sources.config.test.TeClassU;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringValueResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@SpringBootTest(classes = Main.class)
//@RunWith(SpringRunner.class)

public class TestMain {

//    @Autowired
//    private Object object;
    @Autowired
    private BeanFactory beanFactory;

    @Test
    public void test() throws ClassNotFoundException, NoSuchFieldException {
        // create ioc context
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        String[] testBeanPostProcessors = beanFactory.getAliases("&classUtils");
//        ClassUtils bean = beanFactory.getBean(ClassUtils.class);
        ClassUtils bean = applicationContext.getBean(ClassUtils.class);
        Field apField = bean.getClass().getField("applicationContext");
//        Annotation[] annotations = apField.getAnnotations();
        applicationContext.getEnvironment().resolvePlaceholders("");
        beanFactory.addEmbeddedValueResolver(new StringValueResolver() {
            @Override
            public String resolveStringValue(String strVal) {
                return null;
            }
        });
//        System.out.println("bean Aliases is : " + Arrays.asList(testBeanPostProcessors));
//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        System.out.println("bean definition is : " + Arrays.asList(beanDefinitionNames));
        applicationContext.close();
    }

    @Test
    public void anTest(){


//        Class<Service> serviceClass = Service.class;
        AnnotatedElement  mainConfigClass = AnnoDemo.class;
        Annotation[] declaredAnnotations = mainConfigClass.getDeclaredAnnotations();
        List<Annotation> annotations = Arrays.asList(declaredAnnotations);
        System.out.println(Arrays.asList(declaredAnnotations));
        System.out.println("====================================");
        Annotation[] annotations1 = mainConfigClass.getAnnotations();
        System.out.println(Arrays.asList(annotations1));

        if (mainConfigClass instanceof Class) { // otherwise getAnnotations doesn't return anything new
            List<Annotation> inheritedAnnotations = new ArrayList<>();
            for (Annotation annotation : mainConfigClass.getAnnotations()) {
                    inheritedAnnotations.add(annotation);
            }

            System.out.println("====================================");
            System.out.println(inheritedAnnotations);
        }

    }

    @Test
    public void classTest() throws ClassNotFoundException {
        TeClassU teClassU = new TeClassU();
        boolean assignableFrom = EncodeConstant.class.isAssignableFrom(TeClassU.class);
        Class<?> aClass = Class.forName("com.hiyouka.sources.config.test.TeClassU");
        System.out.println(assignableFrom);
        TeClassU.testInternal testInternal = teClassU.new testInternal();
        Class<?> enclosingClass = testInternal.getClass().getEnclosingClass();
        System.out.println(enclosingClass.getName());
    }

}
