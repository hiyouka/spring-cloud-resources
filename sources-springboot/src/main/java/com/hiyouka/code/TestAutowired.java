package com.hiyouka.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Component("manager")
@Order(-2)
// for seed framework
@hiyouka.seedframework.beans.annotation.Component("manager")
public class TestAutowired {

    @Autowired
    @hiyouka.seedframework.beans.annotation.Autowired
    private RedisTemplate<Object,Object> redisTemplate;

//    @Autowired
//    private TestAutowired testAutowired;

//    @Autowired
//    private BaseService<Test2> baseService1;
//
//    @Autowired
//    private BaseService<Object> baseService2;
//
//    @Autowired
//    private BaseService baseService3;
//
//    @Autowired
//    private List<BaseService> baseServices;
//
//    @Autowired
//    private Map<String,BaseService> cache;
//
//    @Autowired
//    private ServiceImpl2<String,Object>  serviceImpl2;
//
//    private BaseService baseService4;
//
//    @Autowired
//    private BeanFactory beanFactory;

//    @Value("#{1}")
//    private String testValue;

    @Value("${spring.aop.auto}")
    @hiyouka.seedframework.beans.annotation.Value("${spring.aop.auto}")
    private String key;

    @Autowired
    @hiyouka.seedframework.beans.annotation.Autowired
    private BaseService<Test2> baseService2;

//    @Autowired
//    protected BaseService baseService;

    @Autowired
    private TestAutowired testAutowiredl;

    @Autowired
    private TestAutowired testAutowired;

    public TestAutowired(RedisTemplate<Object,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void test(){
//        baseServices.size();
//        baseService1.test(null);
//        Class<? extends ServiceImpl2> aClass = serviceImpl2.getClass();
//        System.out.println(cache);
        System.out.println("213");
//        baseService2.test();
//        baseService.test();
    }

    public void methodTest(BaseService<Test1> baseService,BaseService baseService2){

    }

}