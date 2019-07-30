package com.hiyouka.sources;

import com.hiyouka.sources.config.AnnoDemo;
import com.hiyouka.sources.demo.ConfigClass;
import com.hiyouka.sources.demo.seedframework.comp.ImportClassToBean;
import com.hiyouka.sources.demo.seedframework.context.BeanConfig;
import hiyouka.seedframework.context.ApplicationContext;
import hiyouka.seedframework.core.env.Environment;
import hiyouka.seedframework.util.AnnotatedElementUtils;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@SpringBootTest(classes = Main.class)
//@RunWith(SpringRunner.class)

@Lazy(value = false)
@Primary
@Scope
public class TestMain {

//    @Autowired
//    private Object object;
    @Autowired
    private BeanFactory beanFactory;

    @Test
    public void test() throws ClassNotFoundException, NoSuchFieldException {
        // create ioc context
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        AnnotatedElementUtils bean1 = applicationContext.getBean(AnnotatedElementUtils.class);
        System.out.println(bean1);

//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfigClass.class);
//
//        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
//        beanFactory.getBean(TeProTes.class);
//        ConfigurableEnvironment environment = applicationContext.getEnvironment();
//        MutablePropertySources propertySources = environment.getPropertySources();
//        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
//        Map<String, Object> systemProperties = environment.getSystemProperties();
//        String property = environment.getProperty("token.encrypt.salt");
//        environment.getProperty("");
//        AnnoDemo bean = beanFactory.getBean(AnnoDemo.class);
//        String test = bean.getTest();
//        System.out.println(test);
//        beanFactory.registerSingleton("opppp",new SeedCoreException("oppppp"));
//        System.out.println(beanFactory.getSingletonMutex());
//        Object supClassU = beanFactory.getBean("TeProTes");
//        Object teAuClass = beanFactory.getBean("teAuClass");

//        System.out.println(supClassU + " ================== " + teAuClass);
//        String[] beanNamesForType = beanFactory.getBeanNamesForType(EncodeConstant.class);
//        String[] beanNamesForType1 = beanFactory.getBeanNamesForType(ClassUtils.class);
//        String[] beanNamesForType2 = beanFactory.getBeanNamesForType(SupClassU.class);
//        String[] testBeanPostProcessors = beanFactory.getAliases("&classUtils");
////        ClassUtils bean = beanFactory.getBean(ClassUtils.class);
//        ClassUtils bean = applicationContext.getBean(ClassUtils.class);
//        Field apField = bean.getClass().getDeclaredField("applicationContext");
////        Annotation[] annotations = apField.getAnnotations();
//        applicationContext.getEnvironment().resolvePlaceholders("");
//        beanFactory.addEmbeddedValueResolver(new StringValueResolver() {
//            @Override
//            public String resolveStringValue(String strVal) {
//                return null;
//            }
//        });
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
    public void classTest() throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

//        TeClassU teClassU = new TeClassU();
//        boolean assignableFrom = EncodeConstant.class.isAssignableFrom(TeClassU.class);
//        Class<?> aClass = Class.forName("com.hiyouka.sources.config.test.TeClassU");
//        System.out.println(assignableFrom);
//        TeClassU.testInternal testInternal = teClassU.new testInternal();
//        Class<?> enclosingClass = testInternal.getClass().getEnclosingClass();
//        System.out.println(enclosingClass.getName());
//        String s = StringUtils.cleanPath("file:/home/./utubunt/abc..class");
//        System.out.println(s);
//        String s1 = StringUtils.deleteAny("66yaoqifeilea", "6");
//        System.out.println(s1);
//        List<String> stringList = Arrays.asList("12", "13", "666");
//        String string = StringUtils.collectionToDelimitedString(stringList, ",", "p", "s");
//        System.out.println(string);
//        String replace = StringUtils.replaRce("6667f8", "6", "7");
//        System.out.println(replace);
//        String replace1 = "6667f8".replace("6", "7");
//        String s2 = "6667f8".replaceAll("6", "7");
//        System.out.println(replace1);
//        System.out.println(s2);
//        String[] strings = StringUtils.tokenizeToStringArray("file:/home/./ u tub unt /abc..class/  ", "/", true, true);
//        System.out.println(Arrays.asList(strings));
//        System.out.println(strings[1]);
//        Pattern pattern = Pattern.compile("\\?|\\*|\\{((?:\\{[^/]+?\\}|[^/{}]|\\\\[{}])+?)\\}");
//        Matcher matcher = pattern.matcher("{?}");
//        boolean b = matcher.find();
//        System.out.println(b);
//        String group = matcher.group();
//        System.out.println(group);
//        String jar = StringUtils.applyRelativePath("/home/ubuntu/", "jar");
//        System.out.println(jar);
//        File file = FileUtils.getFile("classpath:home/hiyouka/config/BeanConfig.java");
//        ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
//        URL resource = defaultClassLoader.getResource("");
//        URL resource = defaultClassLoader.getResource("application.properties");
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = resolver.getResources("classpath:com/hiyouka/sources/**/*.class");
//        for(Resource resource1: resources){
//            System.out.println(resource1);
//        }
//        System.out.println(resource);
//
//        System.out.println(resource);
//        File file = ResourceUtils.getFile("classpath:sources-springioc-1.0.0-SNAPSHOT.jar/com/hiyouka");
//        URL url = file.toURI().toURL();
//        String protocol = url.getProtocol();
//        boolean jarURL = ResourceUtils.isJarURL(url);
//        boolean fileURL = ResourceUtils.isJarFileURL(url);
//        System.out.println(protocol);
//        System.out.println(jarURL + "================" + fileURL);
//        AnnotationBeanNameGenerator annotationBeanNameGenerator = new AnnotationBeanNameGenerator();
//        AnnotatedBeanDefinition rootBeanDefinition = new AnnotatedGenericBeanDefinition(AnnoDemo.class);
//        String beanName = annotationBeanNameGenerator.generateBeanName(rootBeanDefinition, null);
//        System.out.println(beanName);
//        Class<TestSmartLifecycle.innerClass> innerClassClass = TestSmartLifecycle.innerClass.class;
////        Constructor<TestSmartLifecycle.innerClass> declaredConstructor = innerClassClass.getDeclaredConstructor();
//        Constructor<TestSmartLifecycle.innerClass>[] declaredConstructors = (Constructor<TestSmartLifecycle.innerClass>[]) innerClassClass.getDeclaredConstructors();
////        Object o = declaredConstructors[0].newInstance();
//        BeanUtils.instantiateClass(declaredConstructors[0],BeanUtils.instantiateClass(TestSmartLifecycle.class));
//        BeanUtils.instantiateClass()
    }

    @Test
    public void seedTest(){
        ApplicationContext applicationContext = new hiyouka.seedframework.context.AnnotationConfigApplicationContext(ConfigClass.class);
        Environment bean2 = (Environment) applicationContext.getBean("environment");
        BeanConfig bean1 = applicationContext.getBean(BeanConfig.class);
        ImportClassToBean bean = applicationContext.getBean(ImportClassToBean.class);

//        BeanDefinitionRegistry registry = new DefaultBenFactory();
//        registry.registerBeanDefinition("config",new AnnotatedGenericBeanDefinition(com.hiyouka.sources.demo.ConfigClass.class));
//
//        ConfigurationClassPostProcessor postProcessor = new ConfigurationClassPostProcessor();
//        postProcessor.postProcessBeanDefinitionRegistry(registry);
//        if(registry instanceof hiyouka.seedframework.beans.factory.BeanFactory){
//            AnoTest bean = ((hiyouka.seedframework.beans.factory.BeanFactory) registry).getBean(AnoTest.class);
//            System.out.println(bean);
//        }
    }

}
