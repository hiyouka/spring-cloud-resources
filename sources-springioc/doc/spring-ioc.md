## AnnotationConfigApplocationContext创建过程:  

![img](http://ww1.sinaimg.cn/large/007BVBG7gy1fzoibf7gh5j30p60673yo.jpg)
#### 1.无参构造器的调用：  
&emsp;&emsp;先是调用它的无参构造函数，初始化一些信息。  
&emsp;&emsp;无参构造函数中`new`了`AnnotatedBeanDefinitionReader`和`ClassPathBeanDefiitionScanner`赋值给`reader`context的`scanner`属性。  
&emsp;&emsp;***1)*** `new`AnnotatedBeanDefinitionReader对象：  
&emsp;&emsp;&emsp;&emsp;将该容器对象作为`BeanDefinitionRegistry`赋值给`registry`属性,并且`new`了一个`ConditionEvaluator`赋值给`conditionEvaluator`属性。之后调用`registerAnnotationConfigProcessors`方法将所有的anntaton处理器注册进容器。  
&emsp;&emsp;&emsp;&emsp;**1-1**) new ConditionEvaluator:   
&emsp;&emsp;&emsp;&emsp;这是spring对该类的描述`Internal class used to evaluate {@link Conditional} annotations.`。可以看出这个类是`@Conditional`注解的一个解析器。在创建该类的时候利用`deduceBeanFactory`给该对象初始化了一个`DefaultListableBeanFactory`,并且该类是从容器中获取的。  

![img](http://ww1.sinaimg.cn/large/007BVBG7gy1fzojh47uzbj30ud05u74g.jpg)
&emsp;&emsp;&emsp;&emsp;**1-2**) AnnotationConfigUtils.registerAnnotationConfigProcessors：  
&emsp;&emsp;&emsp;&emsp;该方法先是给容器的`beanFactory`初始化了`private Comparator<Object> dependencyComparator;`和`private AutowireCandidateResolver autowireCandidateResolver`两个属性；他们分别是用于bean的排序和解析自动注入`@Autowired`的；之后便开始注册一些spring内部的bean对象：
```php
    //spring检查容器中是否注入了这些bean 没有就创建简单的含有基本信息的BeanDefiintion对象注册
    // The bean name of the internally managed Configuration annotation processor.
	public static final String CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME =
			"org.springframework.context.annotation.internalConfigurationAnnotationProcessor";
	// The bean name of the internally managed Autowired annotation processor.
	public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME =
			"org.springframework.context.annotation.internalAutowiredAnnotationProcessor";
	// The bean name of the internally managed Required annotation processor.
	public static final String REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME =
			"org.springframework.context.annotation.internalRequiredAnnotationProcessor";
	// The bean name of the internally managed JSR-250 annotation processor.
	public static final String COMMON_ANNOTATION_PROCESSOR_BEAN_NAME =
			"org.springframework.context.annotation.internalCommonAnnotationProcessor";
	// The bean name of the internally managed JPA annotation processor.
	public static final String PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME =
			"org.springframework.context.annotation.internalPersistenceAnnotationProcessor";
	private static final String PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME =
			"org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor";
	// The bean name of the internally managed @EventListener annotation processor.
	public static final String EVENT_LISTENER_PROCESSOR_BEAN_NAME =
			"org.springframework.context.event.internalEventListenerProcessor";
	// The bean name of the internally managed EventListenerFactory.
	public static final String EVENT_LISTENER_FACTORY_BEAN_NAME =
			"org.springframework.context.event.internalEventListenerFactory";
			
    //注册过程如下 详见DefaultListableBeanFactory的registerBeanDefinition
    	if (beanDefinition instanceof AbstractBeanDefinition) {
			try {
				((AbstractBeanDefinition) beanDefinition).validate();
			}
			catch (BeanDefinitionValidationException ex) {
				//do some thing .... 
			}
		}
		BeanDefinition oldBeanDefinition;
		 // get beanDefinintion from cache
		oldBeanDefinition = this.beanDefinitionMap.get(beanName);
		if (oldBeanDefinition != null) { 
		    // if cache alredy has this beanName beanDefinition break
		    //  some warn message or throw exception
			this.beanDefinitionMap.put(beanName, beanDefinition);
		}
		else {
		// determine if create bean has started
		//(判断方法很简单benaFactory中的Set<String>alreadyCreated是否已经被填充过)
			if (hasBeanCreationStarted()) { 
            //lock beanDefinitionMap and put this beanDefinition to map
            //这里有个小细节他在添加元素进beanDefinitionNames时是直接创建了一个原先cache size+1的list
            //然后再将this beanDefinition name 放入list,最终改变beanDefinitionNames的为新创建list
            //if this beanDefinition is in manualSingletonNames,remove from list. why?
			}
			else {
				this.beanDefinitionMap.put(beanName, beanDefinition);
				this.beanDefinitionNames.add(beanName);
				this.manualSingletonNames.remove(beanName);
			}
			this.frozenBeanDefinitionNames = null;
		}
		if (oldBeanDefinition != null || containsSingleton(beanName)) {
		// 如果这个bean已经存在了单实例的对象则将其销毁
			resetBeanDefinition(beanName);
		}
```
&emsp;&emsp;&emsp;&emsp;至此`AnnotatedBeanDefinitionReaderd`对象创建完成  
&emsp;&emsp;***2)*** `new`ClassPathBeanDefinitionScanner对象：  
&emsp;&emsp;&emsp;&emsp;先是调用这个构造方法：  

![img](http://ww1.sinaimg.cn/large/007BVBG7gy1fzom0bqbtgj30ps07ddg0.jpg)  
&emsp;&emsp;&emsp;&emsp;这里可以看出有3步操作`registerDefaultFilters`,`setEnvironment`,`setResourceLoader`  
&emsp;&emsp;&emsp;&emsp;**2-1**) registerDefaultFilters方法:    
&emsp;&emsp;&emsp;&emsp;从名字我们不难看出这是创建默认过滤器的方法；    
&emsp;&emsp;&emsp;&emsp;实际上是往该对象中添加了一个匹配`@Component`注解的AnnotationTypeFilter。spring对该类的解释如下：  
&emsp;&emsp;&emsp;&emsp;`A simple filter which matches classes with a given annotation,checking inherited annotations as well.`

```php
    
    //代码如下：
		this.includeFilters.add(new AnnotationTypeFilter(Component.class));
		ClassLoader cl = ClassPathScanningCandidateComponentProvider.class.getClassLoader();
		try {
			this.includeFilters.add(new AnnotationTypeFilter(
					((Class<? extends Annotation>) ClassUtils.forName("javax.annotation.ManagedBean", cl)), false));
		}
		try {
			this.includeFilters.add(new AnnotationTypeFilter(
					((Class<? extends Annotation>) ClassUtils.forName("javax.inject.Named", cl)), false));
		}
```
&emsp;&emsp;&emsp;&emsp;**2-2**) setEnvironment方法:   
&emsp;&emsp;&emsp;&emsp;一样看名字我们就能知道这是设置环境信息的。就是将之前创建`AnnotatedBeanDefinitionReader`对象时获取的`StandardEnvironment`设置给该对象的`environment`属性。  
&emsp;&emsp;&emsp;&emsp;**2-3**) setResourceLoader方法:  
&emsp;&emsp;&emsp;&emsp;该方法分别给该对象的`resourcePatternResolver`,`metadataReaderFactory`,`componentsIndex`属性初始化。`resourcePatternResolver`对象其实就是容器对象.... `metadataReaderFactory`是一个从容器`resourceCaches`属性拷贝过来的`ConcurrentHashMap`。`resourcePatternResolver`可能是在加载`META-INF/spring.components`这个配置文件吧。具体我也不太清楚。  
&emsp;&emsp;&emsp;&emsp;**至此spring容器的无参构造函数终于时调用完成了(😓)这只是简单的一步而且很多地方即使是知道了它在干什么还是不清楚他为什么这么做如果有更了解的大佬还望指教**  
#### 2.register方法调用：  
&emsp;&emsp;该方法就是调用之前创建的`AnnotatedBeanDefinitionReader`对象的`register`方法将我们所传入的配置类注册到容器当中。我们可以直接看`AnnotatedBeanDefinitionReader`对象的`doRegisterBean`方法：  
&emsp;&emsp;该方法先是创建了一个`AnnotatedGenericBeanDefinition`对象。之后通过`resolveScopeMetadata`方法设置类的定义信息如scope，代理信息，lazy，primary。之后就是将该类注册到容器中。我们来看下这些方法`shouldSkip`,`resolveScopeMetadata`,`processCommonDefinitionAnnotations`,`applyScopedProxyMode`,`registerBeanDefinition`  
  
![img](http://ww1.sinaimg.cn/large/007BVBG7gy1fzonkylu48j30y00guwfe.jpg)
&emsp;&emsp;&emsp;&emsp;***1)*** shouldSkip方法：  
&emsp;&emsp;&emsp;&emsp;该方法先通过`isAnnotated`判断有没有`@Conditional`注解如果有则判断该类是否符合注入要求。  
&emsp;&emsp;&emsp;&emsp;**1-2**) 我们先来看下他是如何判断有没有该注解的：  
&emsp;&emsp;&emsp;&emsp;首先是`searchWithGetSemantics`方法来查出该类所有注解。`searchWithGetSemanticsInAnnotations`来做判断。如果该注解不是java包中的注解。则判断它是否是`@Conditional`注解或者任何时候都忽略的process。之后递归调用`searchWithGetSemantics`来看元注解有没有包含`@Conditional`的。以下为判断源码：  
```php
  
    Class<? extends Annotation> currentAnnotationType = annotation.annotationType();
if (!AnnotationUtils.isInJavaLangAnnotationPackage(currentAnnotationType)) {
	if (currentAnnotationType == annotationType ||
			currentAnnotationType.getName().equals(annotationName) ||
			processor.alwaysProcesses()) {
		T result = processor.process(element, annotation, metaDepth);
		if (result != null) {
			if (processor.aggregates() && metaDepth == 0) {
				processor.getAggregatedResults().add(result);
			}
			else {
				return result;
			}
		}
	}
	// Repeatable annotations in container?
	else if (currentAnnotationType == containerType) {
		for (Annotation contained : getRawAnnotationsFromContainer(element, annotation)) {
			T result = processor.process(element, contained, metaDepth);
			if (result != null) {
				// No need to post-process since repeatable annotations within a
				// container cannot be composed annotations.
				processor.getAggregatedResults().add(result);
			}
		}
	}
}
```
&emsp;&emsp;&emsp;&emsp;**1-3**)  
&emsp;&emsp;&emsp;&emsp;***2)*** resolveScopeMetadata方法：
