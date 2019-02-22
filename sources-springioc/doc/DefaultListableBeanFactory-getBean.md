### `DefaultListableBeanFactory`的getBean方法详解：  
&emsp;&emsp;&emsp;&emsp;这里讲的是根据类型获取bean因为它之后调用的也是根据名称获取bean，下图可以简单看出这个方法执行的步骤。首先是根据bean类型去解析bean获取nameBeanHolder，如果存在则返回bean的实例。如果没有就从父工厂中获取bean。  
![img](https://ws1.sinaimg.cn/large/007BVBG7gy1g06wr1adkhj30rq07imxd.jpg)
### resolveNamedBean：
这个方法的目的是根据类型获取beanNames：

1. getBeanNamesForType：
    1. 先从缓存中获取该类型的所有beanName。
    2. 如果1没有获取到执行doGetBeanNamesForType：
        1. 遍历所有的bean查找符合该类型的的单例bean和单例bean工厂对象。
        2. 从`manualSingletonNames`(容器创建时无参构造函数初始化的bean)中获取符合的单例bean和单例bean工厂对象。（这边要注意在返回工厂bean名称时需要加上`&`)
        3. 返回所有该对象或对象工厂的集合。
        
    3. 将获取的beanNames放入缓存中。
2. 筛选出这些beanName中不在beanDefinitionMap集合中或者允许自动注入的bean。
3. 如果1中获取的beanNames大于一个，获取需要的beanName：(在这一步的时候已经调用getBean(beanName获取了对象))
    1. determinePrimaryCandidate来解析`@Primary`信息。如果只有一个beanName的定义信息被注册到该beanFactory或者该beanFactory的`parentBeanFactory`中 并且该beanName的定义信息为Primary。返回该beanName。
    2. 如果1没获取到determineHighestPriorityCandidate获取。遍历获取这些bean的`@Priority`的优先级代号(数字越小优先级越高)，返回优先级最高的bean名称。
4. 获取到beanName后通过beanName返回包含该对象的NamedBeanHolder。
    
resolveNamedBean的操作源码：
```php
    
    //if not allow cache all bean or allowEagerInit ? 这个条件看不懂
    //isConfigurationFrozen if is true mean this context init finish
    //get bean name from beanDefinitionNames and manualSingletonNames
	if (!isConfigurationFrozen() || type == null || !allowEagerInit) {
		return doGetBeanNamesForType(ResolvableType.forRawClass(type), includeNonSingletons, allowEagerInit);
	}
	// if allow cache all bean get bean name from cache first(isConigurationFrozen is true)
	Map<Class<?>, String[]> cache = // includeNonSingletons : 是否包含非单例对象
			(includeNonSingletons ? this.allBeanNamesByType : this.singletonBeanNamesByType);
	String[] resolvedBeanNames = cache.get(type);
	if (resolvedBeanNames != null) {
		return resolvedBeanNames;
	}
	// if can not get this bean type from cache ,get bean name from beanDefinitionNames and manualSingletonNames
	resolvedBeanNames = doGetBeanNamesForType(ResolvableType.forRawClass(type), includeNonSingletons, true);
	// here is some safe check:  such as whether or not this bean classLoader to the beanFatory classLoader(具体我也不太清楚)
	if (ClassUtils.isCacheSafe(type, getBeanClassLoader())) {
		cache.put(type, resolvedBeanNames); // put this beanname to cache
	}
	return resolvedBeanNames;
```  

`doGetBeanNamesForType`操作源码： 
        
```php
    
    List<String> result = new ArrayList<>();
    // Check all bean definitions.
    for (String beanName : this.beanDefinitionNames) {
    	// Only consider bean as eligible if the bean name
    	// is not defined as alias for some other bean.
    	if (!isAlias(beanName)) {
    		try {
    			RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
    			// Only check bean definition if it is complete.
    			if (!mbd.isAbstract() && (allowEagerInit ||
    					(mbd.hasBeanClass() || !mbd.isLazyInit() || isAllowEagerClassLoading()) &&
    							!requiresEagerInitForType(mbd.getFactoryBeanName()))) {
    				// In case of FactoryBean, match object created by FactoryBean.
    				boolean isFactoryBean = isFactoryBean(beanName, mbd);
    				BeanDefinitionHolder dbd = mbd.getDecoratedDefinition();
    				boolean matchFound =
    						(allowEagerInit || !isFactoryBean ||
    								(dbd != null && !mbd.isLazyInit()) || containsSingleton(beanName)) &&
    						(includeNonSingletons ||
    								(dbd != null ? mbd.isSingleton() : isSingleton(beanName))) &&
    						isTypeMatch(beanName, type);
    				if (!matchFound && isFactoryBean) {
    					// In case of FactoryBean, try to match FactoryBean instance itself next.
    					beanName = FACTORY_BEAN_PREFIX + beanName;
    					matchFound = (includeNonSingletons || mbd.isSingleton()) && isTypeMatch(beanName, type);
    				}
    				if (matchFound) {
    					result.add(beanName);
    				}
    			}
    		}
    		catch (CannotLoadBeanClassException ex) {
    		}
    		catch (BeanDefinitionStoreException ex) {
    		}
    	}
    }
    // Check manually registered singletons too.
    for (String beanName : this.manualSingletonNames) {
    	try {
    		// In case of FactoryBean, match object created by FactoryBean.
    		if (isFactoryBean(beanName)) {
    			if ((includeNonSingletons || isSingleton(beanName)) && isTypeMatch(beanName, type)) {
    				result.add(beanName);
    				// Match found for this bean: do not match FactoryBean itself anymore.
    				continue;
    			}
    			// In case of FactoryBean, try to match FactoryBean itself next.
    			beanName = FACTORY_BEAN_PREFIX + beanName;
    		}
    		// Match raw bean instance (might be raw FactoryBean).
    		if (isTypeMatch(beanName, type)) {
    			result.add(beanName);
    		}
    	}
    	catch (NoSuchBeanDefinitionException ex) {
    	}
    }
    return StringUtils.toStringArray(result);
```
### getBean(beanName) 

&emsp;&emsp;&emsp;&emsp;该方法通过beanName获取bean，实际操作在`doGetBean`方法中：  
1. 该方法先是判断该bean是否被注册(工厂bean需去除`&`),之后调用`getSingleton`试图获取bean实例:
    1. 从单例对象缓存中获取bean。
    2. 若1未获取到并且该对象正在创建则锁定单例缓存从早期创建的对象中获取bean(待补全详细)。
    3. 若2依旧未获取到并且允许提前创建早期对象则获取该bean的单例工厂，存在则创建并返回。 该方法试图获取bean对象。
2. 如果`getSingleton`未获取到。将bean置为创建状态。之后创建bean，创建过程将bean分成了单例，多例和其他三种情况来创建。


#### createBean创建对象：

##### 调用resolveBeforeInstantiation:

调用来获取bean对象。遍历BeanPostProcessors如果是InstantiationAwareBeanPostProcessor接口调用处理器的postProcessBeforeInstantiation来获取对象，如果获取到了则调用处理器的postProcessAfterInitialization获取对象，并返回。
    
    
##### doCreateBean:
 如果resolveBeforeInstantiation未获取到对象使用doCreateBean创建对象：
1. createBeanInstance创建未初始化对象。如果beanDefinition没有FactoryMethodName则调用instantiateBean来创建对象，如果有则调用instantiateUsingFactoryMethod来创建对象。之后封装成BeanWrapper返回。
2. applyMergedBeanDefinitionPostProcessors调用。该方法找出MergedBeanDefinitionPostProcessor调用postProcessMergedBeanDefinition。(CommonAnnotationBeanPostProcessor,AutowiredAnnotationBeanPostProcessor,RequiredAnnotationBeanPostProcessor,ApplicationListenerDetector)AutowiredAnnotationBeanPostProcessor处理器为bean的beanDefinition添加了@Autowired和@Value注解属性，给该处理器的injectionMetadataCache属性添加需要注入信息对象的InjectionMetadata。 ApplicationListenerDetector往它的singletonNames属性中添加了该beanDefinition。
3. 调用addSingletonFactory往工厂的singletonFactories属性中添加了该对象的早期对象来解决循环引用问题。
4. 调用populateBean方法:
    1. 查找所有的InstantiationAwareBeanPostProcessor。(ConfigurationClassPostProcessor)，如果有处理器postProcessAfterInstantiation方法返回false则直接return`populateBean`方法。不对bean进行初始化修改。
    2. 调用InstantiationAwareBeanPostProcessor的postProcessPropertyValues。AutowiredAnnotationBeanPostProcessor执行doResolveDependency来给当前处理的bean注入`@Autowired`和`@Value`的属性，取出之前缓存的该对象InjectionMetadata，调用inject来设置`@Autowired`依赖属性(期间调用doResolveDependency来获取真正需要注入的属性)。
    3. doResolveDependency操作详解：
        1. 先通过findAutowireCandidates方法获取所有的需要注入类型class文件(如果工厂中该对象已经被创建则返回对象)如果查找到的Candidates大于1则使用 determineAutowireCandidate 来进行过滤beanName。
        2. 首先使用determinePrimaryCandidate来查找(对象是否有@Primary注解)
        3. determineHighestPriorityCandidate来查找(遍历获取这些bean的`@Priority`的优先级代号(数字越小优先级越高)，返回优先级最高的bean名称。)
        4. 最后遍历所有候选者找出beanName和注入beanName相同的。若有返回该beanName。
        5. 最后根据beanName获取bean对象(相互依赖的情况下会获取早期对象设置)。
    4. applyPropertyValues()
5. 调用initializeBean：
    1. 如果该类实现了Aware接口(BeanNameAware,BeanClassLoaderAware,BeanFactoryAware)，给该类设置名称，类加载器，工厂等属性。
    2. applyBeanPostProcessorsBeforeInitialization来执行所有bean后置处理器的postProcessBeforeInitialization。遍历过程中如果有一个处理器返回了null将会终止处理，返回最后处理对象。
    3. invokeInitMethods执行初始化方法先执行实现InitializingBean接口的afterPropertiesSet方法，之后执行init方法。
    4. applyBeanPostProcessorsAfterInitialization执行后置处理器postProcessAfterInitialization方法，和2逻辑相同。  
    

###总结: 
整个bean的创建和初始化过程还是相当繁琐的步骤。在整个bean的生命周期中处处使用了缓存。不仔细看就会晕头转向。通过分析也能完全了解一个bean的创建生命周期。详细的demo可以去看博主的[github](https://github.com/hiyouka/spring-cloud-resources "github")。支持我的小伙伴可以给博主的github点下star。
