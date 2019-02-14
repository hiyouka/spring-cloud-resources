## AnnotationConfigApplocationContext创建过程:  

![img](http://ww1.sinaimg.cn/large/007BVBG7gy1fzoibf7gh5j30p60673yo.jpg)
#### 1.无参构造器的调用：  
&emsp;&emsp;先是调用它的无参构造函数，初始化一些信息。  
&emsp;&emsp;无参构造函数中`new`了`AnnotatedBeanDefinitionReader`和`ClassPathBeanDefiitionScanner`赋值给`reader`context的`scanner`属性。  
&emsp;&emsp;***1)*** `new`AnnotatedBeanDefinitionReader对象：  
&emsp;&emsp;&emsp;&emsp;将该容器对象作为`BeanDefinitionRegistry`赋值给`registry`属性,并且`new`了一个`ConditionEvaluator`赋值给`conditionEvaluator`属性。之后调用`registerAnnotationConfigProcessors`方法将所有的annotation处理器注册进容器。  
&emsp;&emsp;&emsp;&emsp;**1-1**) new ConditionEvaluator:   
&emsp;&emsp;&emsp;&emsp;这是spring对该类的描述`Internal class used to evaluate {@link Conditional} annotations.`。可以看出这个类是`@Conditional`注解的一个解析器。在创建该类的时候利用`deduceBeanFactory`给该对象初始化了一个`DefaultListableBeanFactory`,并且该类是从容器中获取的。  

![img](http://ww1.sinaimg.cn/large/007BVBG7gy1fzojh47uzbj30ud05u74g.jpg)
&emsp;&emsp;&emsp;&emsp;**1-2**) AnnotationConfigUtils.registerAnnotationConfigProcessors：  
&emsp;&emsp;&emsp;&emsp;该方法先是给容器的`beanFactory`初始化了`private Comparator<Object> dependencyComparator;`和`private AutowireCandidateResolver autowireCandidateResolver`两个属性；他们分别是用于bean的排序和解析自动注入`@Autowired`的；之后便开始注册一些spring内部的bean对象：
<a name="divtop"></a>
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
&emsp;&emsp;将Config类注册进来，其实就是调用之前创建的`AnnotatedBeanDefinitionReader`对象的`register`方法将我们所传入的配置类注册到容器当中。我们可以直接看`AnnotatedBeanDefinitionReader`对象的`doRegisterBean`方法：  
&emsp;&emsp;该方法先是创建了Config对象的定义信息`AnnotatedGenericBeanDefinition`。之后调用以下方法`shouldSkip`,`resolveScopeMetadata`,`generateBeanName`,`processCommonDefinitionAnnotations`,`applyScopedProxyMode`,`registerBeanDefinition`  
  
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
&emsp;&emsp;&emsp;&emsp;***2)*** resolveScopeMetadata方法: 获取该bean的scope(这里就不细讲spring bean的作用域了不懂的自行百度),ScopeMetadata对象的值默认为`singleton`,所以如果该类没有`@Scope`注解默认为单例的。  

&emsp;&emsp;&emsp;&emsp;***3)*** generateBeanName方法: 获取该bean的`@Component`注解标注的beanName,如果没有默认为类sortName。
获取类上的`@Component`注解步骤与第一步时获取`@Conditional`注解类似(递归获取注解-排除java注解)这里就不细讲了。

&emsp;&emsp;&emsp;&emsp;***4)*** processCommonDefinitionAnnotations方法: 对`@Lazy`,`@Primary`,`@DependsOn`,`@Role`,`@Description`的解析(这里提一下如果`@Lazy`没有的话默认是false,之前看到有人说默认懒加载显然时不正确的~)。丰富beanDefinition。比较简单不详细分析了,贴下源码:
```markdown

    AnnotationAttributes lazy = attributesFor(metadata, Lazy.class);
    if (lazy != null) {
        abd.setLazyInit(lazy.getBoolean("value"));
    }
    else if (abd.getMetadata() != metadata) {
        lazy = attributesFor(abd.getMetadata(), Lazy.class);
        if (lazy != null) {
            abd.setLazyInit(lazy.getBoolean("value"));
        }
    }
    if (metadata.isAnnotated(Primary.class.getName())) {
        abd.setPrimary(true);
    }
    AnnotationAttributes dependsOn = attributesFor(metadata, DependsOn.class);
    if (dependsOn != null) {
        abd.setDependsOn(dependsOn.getStringArray("value"));
    }
    if (abd instanceof AbstractBeanDefinition) {
        AbstractBeanDefinition absBd = (AbstractBeanDefinition) abd;
        AnnotationAttributes role = attributesFor(metadata, Role.class);
        if (role != null) {
            absBd.setRole(role.getNumber("value").intValue());
        }
        AnnotationAttributes description = attributesFor(metadata, Description.class);
        if (description != null) {
            absBd.setDescription(description.getString("value"));
        }
    }
```
&emsp;&emsp;&emsp;&emsp;***5)*** applyScopedProxyMode方法: 判断是否需要创建代理对象。如果需要调用`ScopedProxyCreator.createScopedProxy`方法创建。(待补全代理对象的创建过程...)  

&emsp;&emsp;&emsp;&emsp;***6)*** registerBeanDefinition方法: 从方法名可以看出这部是真正的注册beanDefinition。真正调用的是容器中的BeanFactory(这里是`DefaultListableBeanFactory`)的`registerBeanDefinition`方法。首先验证beanDefinition的信息(具体我也没看懂在干什么)。之后判断该beanDefinition是否被注册过(若注册过符合条件覆盖之前的beanDefinition)。之后就是第一次注册该bean的操作:和调用无参构造函数注册过程一致<a href="#divtop">详情</a>。
&emsp;&emsp;&emsp;&emsp;**`register`方法分析到这里就结束了，其实做的事情就是将配置类注册到容器当中。**  