package com.hiyouka;

import com.hiyouka.code.*;
import hiyouka.seedframework.context.ApplicationContext;
import org.junit.Test;
import org.springframework.core.ResolvableType;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class JunitTest {

    @Test
    public void test() throws NoSuchFieldException {
        Class<TestAutowired> aClass = TestAutowired.class;
        Class<TestAutowired> testAutowiredClass = TestAutowired.class;
        System.out.println(aClass.isAssignableFrom(testAutowiredClass));
//        Field baseService1 = aClass.getDeclaredField("baseService1");
//        Type genericType = baseService1.getGenericType();
//        System.out.println(baseService1);
    }



    @Test
    public void seedAutowireTest() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        Method methodTest = TestAutowired.class.getDeclaredMethod("methodTest",BaseService.class,BaseService.class);
        Parameter[] parameters = methodTest.getParameters();
        Type parameterizedType = parameters[0].getParameterizedType();
        Type parameterizedType1 = parameters[1].getParameterizedType();
        System.out.println("123");
        ApplicationContext applicationContext = new hiyouka.seedframework.context.AnnotationConfigApplicationContext(SpringBootMain.class);
//        String[] names = applicationContext.getBeanNamesForType(BaseService.class);
//        TestAutowired manager = (TestAutowired) applicationContext.getBean("manager");
//        System.out.println("123");
//        getBeanDeference(manager, "baseService1", applicationContext);
//        getBeanDeference(manager, "baseService2", applicationContext);
//        getBeanDeference(manager,"baseService", applicationContext);
//        Class<BeanConfiguration> beanConfigurationClass = BeanConfiguration.class;
//        Method serviceImpl2ofString = beanConfigurationClass.getMethod("serviceImpl2ofString");
//        Type genericReturnType = serviceImpl2ofString.getGenericReturnType();
//        System.out.println(manager);
//    }
//
//    private void getBeanDeference(Object filed, String filedName, ApplicationContext applicationContext) throws NoSuchFieldException, IllegalAccessException {
//        Field filedChild = filed.getClass().getDeclaredField(filedName);
//        Type type = filedChild.getGenericType();
//        Class<? extends Type> clazz = type.getClass();
//        String[] names = applicationContext.getBeanNamesForType(filedChild.getType());
//        List<Object> result = new ArrayList<>();
//        for(String name : names){
//            Object bean = applicationContext.getBean(name);
//            Type thisType = bean.getClass().getGenericSuperclass();
//            Class<?> superclass = bean.getClass().getSuperclass();
//            Type genericSuperclass = superclass.getGenericSuperclass();
//            if(type.equals(thisType)){
//                result.add(bean);
//            }
//
//        }
//        if(result.size() > 1)
//            throw new RuntimeException("found one more bean" + type);
//        else if(result.size() == 0)
//            throw new RuntimeException("not found bean : " + type);
//        ReflectionUtils.makeAccessible(filedChild);
//        filedChild.set(filed,result.get(0));
    }

    @Test
    public void getBean() throws NoSuchFieldException {
//        ResolvableType.forType();
//        ResolvableType.forType()
        Class<TestAutowired> serviceImpl2Class1 = TestAutowired.class;
        Type genericSuperclass = serviceImpl2Class1.getGenericSuperclass();
        Field baseService1 = serviceImpl2Class1.getDeclaredField("baseService1");
        Class<TestAutowired> testAutowiredClass = TestAutowired.class;
        Field baseServices = testAutowiredClass.getDeclaredField("baseServices");
        Field baseService2 = testAutowiredClass.getDeclaredField("baseService2");
        Field test2 = testAutowiredClass.getDeclaredField("test2");
        Type genericType1 = baseService1.getGenericType();
        Type genericType = baseServices.getGenericType();
        Type genericType2 = baseService2.getGenericType();
        Type genericType3 = test2.getGenericType();
        Field cache = testAutowiredClass.getDeclaredField("cache");
        Field testInterface1 = testAutowiredClass.getDeclaredField("testInterface1");
        Type[] types = genericTypeForField(cache);

        Class<ServiceImpl2> serviceImpl2Class2 = ServiceImpl2.class;
        Type genericSuperclass1 = serviceImpl2Class2.getGenericSuperclass();
        Class<?> componentType = serviceImpl2Class1.getComponentType();
        Test1[] test1s = new Test1[5];
        Type genericSuperclass3 = test1s.getClass().getGenericSuperclass();
        Test2[] test2s1 = new Test2[5];
        ResolvableType resolvableType1 = ResolvableType.forClass(test1s.getClass());
        boolean assignableFrom21 = resolvableType1.isAssignableFrom(test2s1.getClass());

        List<BaseService<Test2>> list = new ArrayList<>();
        List<BaseService<Test1>> test2s = new ArrayList<>();
        ResolvableType resolvableType = ResolvableType.forClass(list.getClass());
        boolean assignableFrom2 = resolvableType.isAssignableFrom(test2s.getClass());

        Class<?> componentType2 = list.getClass().getComponentType();
        Type[] genericInterfaces1 = list.getClass().getGenericInterfaces();
        Type genericSuperclass2 = list.getClass().getGenericSuperclass();
        Class<?> componentType1 = list.getClass().getComponentType();
        if(genericSuperclass1 instanceof ParameterizedTypeImpl){
            Type[] actualTypeArguments = ((ParameterizedTypeImpl) genericSuperclass1).getActualTypeArguments();
            System.out.println(actualTypeArguments);
        }
        if(genericSuperclass2 instanceof ParameterizedTypeImpl){
            Type[] actualTypeArguments = ((ParameterizedTypeImpl) genericSuperclass2).getActualTypeArguments();
            System.out.println(actualTypeArguments);
        }
        ResolvableType resolvableType12 = ResolvableType.forField(baseService1);
        ResolvableType resolvableType2 = ResolvableType.forField(testInterface1);
        ResolvableType resolvableType3 = ResolvableType.forClass(ServiceImpl2.class);
        boolean assignableFrom3 = resolvableType2.isAssignableFrom(ServiceImpl2.class);
        boolean assignableFrom4 = resolvableType2.isAssignableFrom(ServiceImpl1.class);
        boolean assignableFrom = resolvableType12.isAssignableFrom(ServiceImpl2.class);
        boolean assignableFrom1 = resolvableType12.isAssignableFrom(ServiceImpl1.class);
        Class<ServiceImpl2> serviceImpl2Class = ServiceImpl2.class;
        Type[] genericInterfaces = serviceImpl2Class.getGenericInterfaces();

        System.out.println(genericInterfaces);
    }

    public void isAssignableFrom(){

    }

    private Type[] genericTypeForField(Field field){
        Type genericType = field.getGenericType();
        if(genericType instanceof ParameterizedTypeImpl){
            return ((ParameterizedTypeImpl) genericType).getActualTypeArguments();
        }
        return null;
    }

    @Test
    public void genericTest() throws NoSuchFieldException, NoSuchMethodException {
//        Class<ServiceImpl1> serviceImpl1Class = ServiceImpl1.class;
//        Class<ServiceImpl2> serviceImpl2Class = ServiceImpl2.class;
//        // 将所有泛型对象 和 真实对象缓存
//        TypeVariable<Class<ServiceImpl1>>[] typeParameters1 = serviceImpl1Class.getTypeParameters();
//        Type genericSuperclass2 = serviceImpl2Class.getGenericSuperclass();
//        Type[] genericInterfaces2 = serviceImpl1Class.getGenericInterfaces();
//        Class<?> rawType = ((ParameterizedTypeImpl) genericSuperclass2).getRawType();
//        TypeVariable<? extends Class<?>>[] typeParameters = rawType.getTypeParameters();
//        Type genericSuperclass = serviceImpl2Class.getGenericSuperclass();
//        Type[] genericInterfaces = serviceImpl2Class.getGenericInterfaces();
//        Class<? super ServiceImpl2> superclass = serviceImpl2Class.getSuperclass();
//        Type genericSuperclass1 = superclass.getGenericSuperclass();
//
//        Type[] genericInterfaces1 = superclass.getGenericInterfaces();
//        Class<ServiceImpl1> serviceImpl1Class1 = ServiceImpl1.class;
//        GenericWrapper genericWrapper = new GenericWrapper(serviceImpl2Class);
////        Class<?> target = genericWrapper.getTarget();
//        GenericWrapper genericWrapper1 = new GenericWrapper(serviceImpl1Class1.getSuperclass());
//        boolean assignableFrom = genericWrapper.isAssignableFrom(TestInterface1.class);
//
//        Class<TestAutowired> serviceImpl2Class1 = TestAutowired.class;
////        Field testInterface1 = serviceImpl2Class1.getDeclaredField("testInterface1");
////        Type genericType1 = testInterface1.getGenericType();
////        Type genericType = testInterface1.getGenericType();
//        Map<TypeVariable, Class> typeVariableClassMap = ResolverTypeUtil.obtainGenericsForClass(ServiceImpl2.class);
////        ((ParameterizedTypeImpl)genericType)
//        ServiceImpl2<String,Object> serviceImpl2 = new ServiceImpl2<>();
//        Map<Class, Class[]> classMap = ResolverTypeUtil.obtainRealTypeForClass(ServiceImpl2.class);
//        Field baseService = TestAutowired.class.getDeclaredField("test1ObjectTestInterface2");
//        Field baseService3 = TestAutowired.class.getDeclaredField("baseService3");
//        Type genericType1 = baseService3.getGenericType();
//        ResolvableType resolvableType2 = ResolvableType.forField(baseService);
//        boolean assignableFrom2 = resolvableType2.isAssignableFrom(ServiceImpl2.class);
//        Type genericType = baseService.getGenericType();
//        boolean assignableFrom1 = ResolverTypeUtil.isAssignableFrom(baseService, ServiceImpl2.class);
//        ResolverTypeUtil.isAssignableFrom()
        Method testMethod1 = this.getClass().getDeclaredMethod("testMethod1");
        Method testMethod2 = this.getClass().getDeclaredMethod("testMethod2", ServiceImpl2.class);
        Type returnType = testMethod1.getGenericReturnType();
        Type parameterType = testMethod2.getGenericParameterTypes()[0];


        System.out.println(ServiceImpl2.class.isAssignableFrom(BaseService.class));
        System.out.println(BaseService.class.isAssignableFrom(ServiceImpl2.class));
        System.out.println(ResolverTypeUtil.isAssignableFrom(returnType,ServiceImpl2.class));
        System.out.println(ResolverTypeUtil.isAssignableFrom(returnType,parameterType));
        System.out.println(ResolverTypeUtil.isAssignableFrom(BaseService.class,parameterType));

//        System.out.println(assignableFrom1);
//        System.out.println(b);
    }

    public BaseServiceSon testMethod1(){
        return null;
    }



    public void testMethod2(ServiceImpl2<Test1,Test2> serviceImpl2){
    }

    public boolean isAssignableFrom(Type matchType, Class<?> originClass){
        Type genericSuperclass = originClass.getGenericSuperclass();
        if(genericSuperclass instanceof ParameterizedTypeImpl){
            Type[] actualTypeArguments = ((ParameterizedTypeImpl) genericSuperclass).getActualTypeArguments();
        }
        return false;
    }


    static class ResolverTypeUtil {

        private static final Map<Type, Map<TypeVariable,Class>> typeCache = new ConcurrentHashMap<>();

        private static final Map<Type,Map<Class,Class[]>> typeGenericsCache = new ConcurrentHashMap<>();

        public static boolean isAssignableFrom(Type origin, Type target){
            if(target instanceof Class){
                return isAssignableFrom(origin,(Class)target);
            }
            else if(origin instanceof Class){
                Type rawType = ((ParameterizedType) target).getRawType();
                if(rawType instanceof Class){
                    return ((Class<?>) origin).isAssignableFrom((Class) rawType);
                }
            }
            else if(origin instanceof ParameterizedType &&
                    target instanceof ParameterizedType){
                Type originRawType = ((ParameterizedType) origin).getRawType();
                Type targetRawType = ((ParameterizedType) target).getRawType();
                if(!((Class<?>)originRawType).isAssignableFrom((Class<?>) targetRawType)){
                    return false;
                }
                Map<Class, Class[]> classMap = obtainRealTypeForType(target);
                Class[] classes = classMap.get(originRawType);
                Type[] actualTypeArguments = ((ParameterizedType) origin).getActualTypeArguments();
                for(int i=0; i<actualTypeArguments.length; i++){
                    if(!actualTypeArguments[i].equals(classes[i])){
                        return false;
                    }
                }
                return true;
            }
            return false;
        }


        /**
         * 判断某个clazz是否源于某个属性(包含泛型判断)
         * @param element 属性
         * @param clazz 判断类
         * @return boolean
         */
        public static boolean isAssignableFrom(Type element, Class clazz){
            if(element instanceof Class){
                return ((Class<?>) element).isAssignableFrom(clazz);
            }
            else if(element instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) element).getRawType();
                if(rawType instanceof Class){
                    if(!((Class<?>) rawType).isAssignableFrom(clazz)){
                        return false;
                    }
                    Type[] actualTypeArguments = ((ParameterizedType) element).getActualTypeArguments();
                    Map<Class, Class[]> classMap = obtainRealTypeForType(clazz);
                    Class[] classes = classMap.get(rawType);
                    for(int i=0; i<actualTypeArguments.length; i++){
                        if(!actualTypeArguments[i].equals(classes[i])){
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }


        public static boolean typeIsMatchOfGenerics(Type type,Type[] generics){
            if(type instanceof Class){
                return false;
            }
            else if(type instanceof ParameterizedType){
                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                if(types.length != generics.length){
                    return false;
                }
                for(int i=0; i< types.length; i++){
                    if(!types[i].equals(generics[i])){
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        public static Map<Class, Class[]> obtainRealTypeForType(Type type){
            Map<Class, Class[]> classMap = typeGenericsCache.get(type);
            if(classMap == null){
                foreachGenericTypeForType(type);
                classMap = typeGenericsCache.get(type);
            }
            return classMap;
        }

        private static void foreachGenericTypeForType(Type originType){
            if(originType instanceof Class){
                foreachGenericTypeForType(originType, (Class) originType);
            }
            else if(originType instanceof ParameterizedType){
                Type rawType = ((ParameterizedType) originType).getRawType();
                Map<Class, Class[]> classMap = typeGenericsCache.computeIfAbsent(originType, k -> new HashMap<>());
                Type[] actualTypeArguments = ((ParameterizedType) originType).getActualTypeArguments();
                Class[] classes = new Class[actualTypeArguments.length];
                obtainGenericsForType(originType);
                for(int i=0; i<actualTypeArguments.length; i++){
                    if(actualTypeArguments[i] instanceof Class){
                        classes[i] = (Class) actualTypeArguments[i];
                    }
                    else if(actualTypeArguments[i] instanceof TypeVariable){
                        Map<TypeVariable, Class> mapper = obtainGenericsForType(originType);
                        classes[i] = mapper.get(actualTypeArguments[i]);
                    }
                }
                classMap.put((Class) rawType,classes);
                foreachGenericTypeForType(originType, (Class) rawType);
            }
            else {
            }
        }

        private static void foreachGenericTypeForType(Type originType, Class match){
            Type genericSuperclass = match.getGenericSuperclass();
            Type[] genericInterfaces = match.getGenericInterfaces();
            List<Type> types = new ArrayList<>(genericInterfaces.length + 1);
            if(genericInterfaces.length != 0){
                types.addAll(Arrays.asList(genericInterfaces));
            }
            if(genericSuperclass != null){
                types.add(genericSuperclass);
            }
            for(Type type : types){
                if(type instanceof ParameterizedType){
                    Type rawType = ((ParameterizedType) type).getRawType();
                    Map<Class, Class[]> classMap = typeGenericsCache.computeIfAbsent(originType, k -> new HashMap<>());
                    Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                    Class[] classes = new Class[actualTypeArguments.length];
                    for(int i=0; i<actualTypeArguments.length; i++){
                        if(actualTypeArguments[i] instanceof Class){
                            classes[i] = (Class) actualTypeArguments[i];
                        }
                        else if(actualTypeArguments[i] instanceof TypeVariable){
                            Map<TypeVariable, Class> mapper = obtainGenericsForType(originType);
                            TypeVariable actualTypeArgument = (TypeVariable) actualTypeArguments[i];
                            Class realType = mapper.get(actualTypeArgument);
                            if(realType == null){
                                GenericDeclaration genericDeclaration = actualTypeArgument.getGenericDeclaration();
                                TypeVariable<?>[] typeParameters = genericDeclaration.getTypeParameters();
                                Class[] superClasses = classMap.get(genericDeclaration);
                                if(superClasses != null && superClasses.length == typeParameters.length){
                                    for(int j=0; j<typeParameters.length; j++){
                                        if(typeParameters[j].equals(actualTypeArgument)){
                                            realType = superClasses[j];
                                        }
                                    }
                                }
                            }
                            classes[i] = realType;
                        }
                    }
                    classMap.put((Class) rawType,classes);
                    foreachGenericTypeForType(originType, (Class) rawType);
                }
                else if(type instanceof Class){
                    Map<Class, Class[]> classMap = typeGenericsCache.computeIfAbsent(originType, k -> new HashMap<>());
                    classMap.put((Class) type,null);
                }
            }
        }

        public static Map<TypeVariable,Class> obtainGenericsForType(Type type){
            Map<TypeVariable, Class> typeVariableClassMap = typeCache.get(type);
            if(typeVariableClassMap == null){
                Map<TypeVariable, Class> map = resolveGenerics(type, null);
                typeCache.put(type,map);
                return map;
            }
            return typeVariableClassMap;
        }

        private static Map<TypeVariable,Class> resolveGenerics(Type type, Map<TypeVariable,Class> mapper){
            if(mapper == null)
                mapper = new HashMap<>();
            if(type instanceof Class){
                Type genericSuperclass = ((Class) type).getGenericSuperclass();
                pullGenerics(genericSuperclass,mapper);
                Type[] genericInterfaces = ((Class) type).getGenericInterfaces();
                for(Type t : genericInterfaces){
                    pullGenerics(t,mapper);
                }
            }
            else {
                pullGenerics(type,mapper);
            }
            return mapper;
        }

        private static  void pullGenerics(Type type,Map<TypeVariable,Class> mapper){
            if(type instanceof ParameterizedType){
                Type rawType = ((ParameterizedType) type).getRawType();
                if(rawType instanceof Class){
                    resolveGenerics(rawType,mapper);
                    TypeVariable[] typeParameters = ((Class) rawType).getTypeParameters();
                    Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                    for(int i=0; i<typeParameters.length; i++){
                        if(actualTypeArguments[i] instanceof Class){
                            // cache
                            mapper.put(typeParameters[i], (Class<?>) actualTypeArguments[i]);
                        }
                    }
                }
            }
            // 无泛型类
            else if(type instanceof Class){

            }
        }

        public static void clearCache(){
            typeCache.clear();
            typeGenericsCache.clear();
        }
    }

}