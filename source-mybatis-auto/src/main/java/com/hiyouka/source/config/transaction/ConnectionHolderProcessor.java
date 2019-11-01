package com.hiyouka.source.config.transaction;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Configuration
@ConditionalOnClass({BeanFactoryTransactionAttributeSourceAdvisor.class,
                    DataSourceTransactionManager.class})
public class ConnectionHolderProcessor implements Ordered{

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    private final Map<DataSource,ConnectionHolder> cache = new HashMap<>();

    @Autowired(required = false)
    void setTransactionAdvisorOrder(BeanFactoryTransactionAttributeSourceAdvisor advisor){
        advisor.setOrder(Ordered.LOWEST_PRECEDENCE-2);
    }


    public void setResourceIfNecessary(){
        if(cache.get(obtainDataSource()) == null){
            ConnectionHolder resource = (ConnectionHolder) TransactionSynchronizationManager
                    .getResource(obtainDataSource());
            if(resource != null){
                cache.put(obtainDataSource(),resource);
            }
        }
    }

    public void bindResourceIfNecessary(){
        DataSource dataSource = obtainDataSource();
        ConnectionHolder resource = (ConnectionHolder) TransactionSynchronizationManager
                .getResource(dataSource);
        if(resource == null){
            ConnectionHolder connectionHolder = cache.get(dataSource);
            if(connectionHolder != null)
                TransactionSynchronizationManager.bindResource(obtainDataSource(),connectionHolder);
        }
    }

    private DataSource obtainDataSource() {
        DataSource dataSource = dataSourceTransactionManager.getDataSource();
        Assert.state(dataSource != null, "No DataSource set");
        return dataSource;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Configuration
    @ConditionalOnMissingBean(AbstractAdvisorAutoProxyCreator.class)
    @org.springframework.context.annotation.EnableAspectJAutoProxy
    public static class EnableAspectJAutoProxy{
//        @Value("${spring.aop.proxy-target-class}")
//        private boolean proxyTargetClass;
//
//        @Autowired(required = false)
//        void setEnableAspectJAutoProxy(BeanDefinitionRegistry registry){
//            AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);
//            if (proxyTargetClass) {
//                AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
//            }
//
//        }
//
//        @ConditionalOnProperty(prefix = "spring.aop", havingValue = "true", matchIfMissing = false)
//

    }


    @Configuration
    @Aspect
    class ConnectionHolderHoldOn implements Ordered{

        @Before("@annotation(com.hiyouka.source.config.transaction.ConnectionHolderOperation))")
        public void setConnectionHolder(JoinPoint joinPoint){
            ConnectionHolderOperation connectionHolder = ((MethodSignature) joinPoint.getSignature())
                    .getMethod().getAnnotation(ConnectionHolderOperation.class);
            if(ConnectionHolderOperation.HolderAction.CACHE.equals(connectionHolder.value())){
                setResourceIfNecessary();
            }
        }

        // 保证在BeanFactoryTransactionAttributeSourceAdvisor后执行
        @Override
        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE-1;
        }
    }

    @Configuration
    @Aspect
    class ConnectionHolderBind implements Ordered{

        // 保证比BeanFactoryTransactionAttributeSourceAdvisor先执行
        @Override
        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE-3;
        }

        @Before("@annotation(com.hiyouka.source.config.transaction.ConnectionHolderOperation) ")
        public void bindConnectionHolder(JoinPoint joinPoint){
            ConnectionHolderOperation connectionHolder = ((MethodSignature) joinPoint.getSignature())
                    .getMethod().getAnnotation(ConnectionHolderOperation.class);
//            ((MethodSignature) joinPoint.getSignature()).getMethod().
            if(ConnectionHolderOperation.HolderAction.BIND.equals(connectionHolder.value())){
                bindResourceIfNecessary();
            }
        }
    }

}