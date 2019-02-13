### `DefaultListableBeanFactory`的getBean方法详解：  
&emsp;&emsp;&emsp;&emsp;这里讲的是根据类型获取bean因为它之后调用的也是根据名称获取bean，下图可以简单看出这个方法执行的步骤。首先是根据bean类型去解析bean获取nameBeanHolder，如果存在则返回bean的实例。如果没有就从父工厂中获取bean。  
![img](http://ww1.sinaimg.cn/large/007BVBG7gy1fzplp6k1tdj30rq07imxd.jpg)
&emsp;&emsp;&emsp;&emsp; resolveNamedBean方法：
    该方法先用`getBeanNamesForType`找出所有该类型的bean名称。方法如下:
    
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
        
&emsp;&emsp;&emsp;&emsp;接下来我们就来看下`doGetBeanNamesForType`到底做了什么吧：贴上源码  
        
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
&emsp;&emsp;&emsp;&emsp; 从源码可以看出他先是从所有的beanDefinintion中查找符合该类型的的单例bean和单例bean工厂对象,
            之后再从`manualSingletonNames`也就是前文提到的调用无参构造函数是创建的bean中做之前同样的操作。
             这边要注意一下再返回工厂bean名称时需要加上`&`    
            
&emsp;&emsp;&emsp;&emsp; 拿到bean的名称之后判断符合条件的bean名称的个数。  
&emsp;&emsp;&emsp;&emsp; 如果bean名称多于一个进行以下操作： 判断这些类上面又没有这两个注解:`@Primary`  `@Priority`  
&emsp;&emsp;&emsp;&emsp; `@Primary`判断：返回满足以下条件的beanName 
该beanName的定义信息被注册到该beanFactroy或者该beanFactoty的`parentBeanFactory`中 并且该beanName的定义信息为Primary  
&emsp;&emsp;&emsp;&emsp; `@Priority`判断： 遍历获取这些bean的`@Priority`的优先级代号(数字越小优先级越高)，返回优先级最高的bean名称；
由于代码判空了所以如果只有一个bean上有该注解 那就 等同于 `@Primary` 但是`@Priority`是一个类级注解,而Spring在获取`@Priority`值的时候
调用`AnnotationUtils.findAnnotation()`这个方法会将父类的注解一并返回也就是所如果这些bean是父子关系的话并且只有父类有`@Priority`的话
那们他们的优先级将是一样的，如果没有其他优先级高的bean spring就会抛出`NoUniqueBeanDefinitionException`异常。(还是Spring的`@Primary`好用😰)  
&emsp;&emsp;&emsp;&emsp; 找到bean的name之后自然是调用`getBean(name)`来获取实例对象了，实际操作在`doGetBean`方法中：  
&emsp;&emsp;&emsp;&emsp; 该方法先是判断该bean是否被注册(工厂bean需去除`&`),之后调用`getSingleton`试图获取bean实例:
```php

    //以下是getSingleton源码 
    Object singletonObject = this.singletonObjects.get(beanName);//get single instance from cache
    // if this bean not in cache and this bean currently in creation
	if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
		synchronized (this.singletonObjects) {// lock the cache
		//get instance from the early singleton cache
			singletonObject = this.earlySingletonObjects.get(beanName);
		// if also not get and can be create early refernces
			if (singletonObject == null && allowEarlyReference) {
				ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
				if (singletonFactory != null) { // use factoryBean create bean
					singletonObject = singletonFactory.getObject();
					this.earlySingletonObjects.put(beanName, singletonObject);
					this.singletonFactories.remove(beanName);
				}
			}
		}
	}
	return singletonObject;
```
&emsp;&emsp;&emsp;&emsp; 该方法试图获取bean对象，如果获取到的是factroyBean那就是用factoryBean创建对象，这里就不讲factroyBean创建对象的过程了。
获取到bean对象之后，应该是判断这个bean：直接贴源码  
```php

// Check if required type matches the type of the actual bean instance.
if (requiredType != null && !requiredType.isInstance(bean)) {
	try {
		T convertedBean = getTypeConverter().convertIfNecessary(bean, requiredType);
		if (convertedBean == null) {
			throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
		}
		return convertedBean;
	}
	catch (TypeMismatchException ex) {
	    //log and throw exception
		throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
	}
}
