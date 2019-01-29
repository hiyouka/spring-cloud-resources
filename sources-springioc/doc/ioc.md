#####实现spring的ioc容器思路：
&emsp;&emsp;
      1.  容器的初始化操作:(参照spring的prepareRefresh方法)<br/>
&emsp;&emsp;&emsp;给容器(ApplicationContext)添加以下属性:<br/>
```
        //设置容器的基本信息:(prepareRefresh()方法)
        this.startupDate = System.currentTimeMillis();
        this.closed.set(false);
        this.active.set(true);
        //这个方法是和spring一致的是个空方法 便于之后继承实现自定义容器可以增强容器的初始化
        initPropertySources();
        //(运行环境信息)信息设置
        environment
````
&emsp;&emsp;
      2. BeanFactory的对象创建操作:<br/>
&emsp;&emsp;&emsp;这是对应的spring的方法:<br/>
````
        //spring中该方法就是返回一个设置了序列化id的DefaultListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
````
&emsp;&emsp;
      3. BeanFactory预处理操作:<br/>
&emsp;&emsp;&emsp;给刚创建的beanFactory中添加一些属性以便之后操作
````
    //在spring的预处理中给beanFactory添加了
class AbstractApplicationContext{
    public void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    		// Tell the internal bean factory to use the context's class loader etc.
    		beanFactory.setBeanClassLoader(getClassLoader());
    		beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));
    		beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));
    
    		// Configure the bean factory with context callbacks.
    		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
    		beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
    		beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class);
    		beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
    		beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
    		beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
    		beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);
    
    		// BeanFactory interface not registered as resolvable type in a plain factory.
    		// MessageSource registered (and found for autowiring) as a bean.
    		beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
    		beanFactory.registerResolvableDependency(ResourceLoader.class, this);
    		beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
    		beanFactory.registerResolvableDependency(ApplicationContext.class, this);
    
    		// Register early post-processor for detecting inner beans as ApplicationListeners.
    		beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));
    
    		// Detect a LoadTimeWeaver and prepare for weaving, if found.
    		if (beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
    			beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
    			// Set a temporary ClassLoader for type matching.
    			beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
    		}
    
    		// Register default environment beans.
    		if (!beanFactory.containsLocalBean(ENVIRONMENT_BEAN_NAME)) {
    			beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());
    		}
    		if (!beanFactory.containsLocalBean(SYSTEM_PROPERTIES_BEAN_NAME)) {
    			beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());
    		}
    		if (!beanFactory.containsLocalBean(SYSTEM_ENVIRONMENT_BEAN_NAME)) {
    			beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());
    		}
    	}
}

````      
    首先 bean 的生命周期如下：
        1.