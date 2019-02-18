##AnnotationConfigApplicationContext容器创建(基于Spring 5.0.7)
![img](https://ws1.sinaimg.cn/large/007BVBG7gy1g06wm84mcpj30p60673yo.jpg)
### 无参构造器：  
&emsp;&emsp;先是调用它的无参构造函数，初始化一些信息。  
&emsp;&emsp;无参构造函数中`new`了`AnnotatedBeanDefinitionReader`和`ClassPathBeanDefiitionScanner`赋值给`reader`context的`scanner`属性。  
#### `new`AnnotatedBeanDefinitionReader对象：  
&emsp;&emsp;&emsp;&emsp;将该容器对象作为`BeanDefinitionRegistry`赋值给`registry`属性,并且`new`了一个`ConditionEvaluator`赋值给`conditionEvaluator`属性。之后调用`registerAnnotationConfigProcessors`方法将所有的annotation处理器注册进容器。  
##### new ConditionEvaluator:   
&emsp;&emsp;&emsp;&emsp;这是spring对该类的描述`Internal class used to evaluate {@link Conditional} annotations.`。可以看出这个类是`@Conditional`注解的一个解析器。在创建该类的时候利用`deduceBeanFactory`给该对象初始化了一个`DefaultListableBeanFactory`,并且该类是从容器中获取的。  

![img](https://ws1.sinaimg.cn/large/007BVBG7gy1g06wn4k0pjj30ud05u74g.jpg)
##### AnnotationConfigUtils.registerAnnotationConfigProcessors：  
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
#### `new`ClassPathBeanDefinitionScanner对象：  
&emsp;&emsp;&emsp;&emsp;先是调用这个构造方法：  

![img](https://ws1.sinaimg.cn/large/007BVBG7gy1g06wpn4k0kj30ps07ddg0.jpg)  
&emsp;&emsp;&emsp;&emsp;这里可以看出有3步操作`registerDefaultFilters`,`setEnvironment`,`setResourceLoader`  
##### registerDefaultFilters方法:    
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
##### setEnvironment方法:   
&emsp;&emsp;&emsp;&emsp;一样看名字我们就能知道这是设置环境信息的。就是将之前创建`AnnotatedBeanDefinitionReader`对象时获取的`StandardEnvironment`设置给该对象的`environment`属性。  
##### setResourceLoader方法:  
&emsp;&emsp;&emsp;&emsp;该方法分别给该对象的`resourcePatternResolver`,`metadataReaderFactory`,`componentsIndex`属性初始化。`resourcePatternResolver`对象其实就是容器对象.... `metadataReaderFactory`是一个从容器`resourceCaches`属性拷贝过来的`ConcurrentHashMap`。`resourcePatternResolver`可能是在加载`META-INF/spring.components`这个配置文件吧。具体我也不太清楚。  
&emsp;&emsp;&emsp;&emsp;**至此spring容器的无参构造函数终于时调用完成了(😓)这只是简单的一步而且很多地方即使是知道了它在干什么还是不清楚他为什么这么做如果有更了解的大佬还望指教**  
### register：  
&emsp;&emsp;将Config类注册进来，其实就是调用之前创建的`AnnotatedBeanDefinitionReader`对象的`register`方法将我们所传入的配置类注册到容器当中。我们可以直接看`AnnotatedBeanDefinitionReader`对象的`doRegisterBean`方法：  
&emsp;&emsp;该方法先是创建了Config对象的定义信息`AnnotatedGenericBeanDefinition`。之后调用以下方法`shouldSkip`,`resolveScopeMetadata`,`generateBeanName`,`processCommonDefinitionAnnotations`,`applyScopedProxyMode`,`registerBeanDefinition`  
  
![img](https://ws1.sinaimg.cn/large/007BVBG7gy1g06wq1o62gj30y00guwfe.jpg)
#### shouldSkip方法：  
&emsp;&emsp;&emsp;&emsp;该方法先通过`isAnnotated`判断有没有`@Conditional`注解如果有则判断该类是否符合注入要求。  
&emsp;&emsp;&emsp;&emsp; 我们先来看下他是如何判断有没有该注解的：  
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

#### resolveScopeMetadata方法:
&emsp;&emsp;&emsp;&emsp;获取该bean的scope(这里就不细讲spring bean的作用域了不懂的自行百度),ScopeMetadata对象的值默认为`singleton`,所以如果该类没有`@Scope`注解默认为单例的。  

#### generateBeanName方法: 
&emsp;&emsp;&emsp;&emsp;获取该bean的`@Component`注解标注的beanName,如果没有默认为类sortName。
获取类上的`@Component`注解步骤与第一步时获取`@Conditional`注解类似(递归获取注解-排除java注解)这里就不细讲了。

#### processCommonDefinitionAnnotations方法:
 &emsp;&emsp;&emsp;&emsp;对`@Lazy`,`@Primary`,`@DependsOn`,`@Role`,`@Description`的解析(这里提一下如果`@Lazy`没有的话默认是false,之前看到有人说默认懒加载显然时不正确的~)。丰富beanDefinition。比较简单不详细分析了,贴下源码:
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
#### applyScopedProxyMode方法: 
&emsp;&emsp;&emsp;&emsp;判断是否需要创建代理对象。如果需要调用`ScopedProxyCreator.createScopedProxy`方法创建。(待补全代理对象的创建过程...)  

#### registerBeanDefinition方法: 
&emsp;&emsp;&emsp;&emsp;从方法名可以看出这部是真正的注册beanDefinition。真正调用的是容器中的BeanFactory(这里是`DefaultListableBeanFactory`)的`registerBeanDefinition`方法。首先验证beanDefinition的信息(具体我也没看懂在干什么)。之后判断该beanDefinition是否被注册过(若注册过符合条件覆盖之前的beanDefinition)。之后就是第一次注册该bean的操作(和调用无参构造函数注册过程一致<a href="#divtop">详情</a>。)
&emsp;&emsp;&emsp;&emsp;  

**`register`方法分析到这里就结束了，其实做的事情就是将配置类注册到容器当中。**  

### refresh：  
&emsp;&emsp;`refresh`是最重要的一步，进行了容器刷新以及bean创建。执行步骤比较多。
```markdown
    prepareRefresh();
    // Tell the subclass to refresh the internal bean factory.
    ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
    // Prepare the bean factory for use in this context.
    prepareBeanFactory(beanFactory);
    try {
        // Allows post-processing of the bean factory in context subclasses.
        postProcessBeanFactory(beanFactory);
        // Invoke factory processors registered as beans in the context.
        invokeBeanFactoryPostProcessors(beanFactory);
        // Register bean processors that intercept bean creation.
        registerBeanPostProcessors(beanFactory);
        // Initialize message source for this context.
        initMessageSource();
        // Initialize event multicaster for this context.
        initApplicationEventMulticaster();
        // Initialize other special beans in specific context subclasses.
        onRefresh();
        // Check for listener beans and register them.
        registerListeners();
        // Instantiate all remaining (non-lazy-init) singletons.
        finishBeanFactoryInitialization(beanFactory);
        // Last step: publish corresponding event.
        finishRefresh();
    }
    catch (BeansException ex) {
        // Destroy already created singletons to avoid dangling resources.
        destroyBeans();
        // Reset 'active' flag.
        cancelRefresh(ex);
    }
    finally {
        // Reset common introspection caches in Spring's core, since we
        // might not ever need metadata for singleton beans anymore...
        resetCommonCaches();
    }
```
#### prepareRefresh()：
&emsp;&emsp;&emsp;&emsp; 进行容器的预刷新工作。这步还是比较简单的。先是将容器置为启动状态。之后调用`initPropertySources`(该方法为空方法，提供给子类的覆盖)
最后调用`validateRequiredProperties`来验证是否包含一些必要参数(这里必要参数依旧为空)。  

#### obtainFreshBeanFactory():
&emsp;&emsp;&emsp;&emsp; 对`beanFactory`进行刷新工作。先是调用`refreshBeanFactory`,使用CAS判断工厂是否已经刷新(已刷新抛异常),之后给工厂bean一个序列化id，
并将工厂对象放入缓存(由序列化id映射)。最后返回工厂bean(调用容器无参构造函数创建的`DefaultListableBeanFactory`对象);  

#### prepareBeanFactory(beanFactory):
进行`beanFactory`的准备工作:
<ol>
    <li>
        给`beanFactory`添加类加载器，表达式解析器，属性编辑器注册器，
        `ApplicationContextAwareProcessor`。
    </li>
    <li>
        忽略某些类的自动注入(这些接口大多为Spring为实现类注入bean的功能接口，
        例如：ApplicationContextAware)。
    </li>
    <li>
        指定`BeanFactory`,`ResourceLoader`,`ApplicationEventPublisher`,`ApplicationContext`
        这些类型自动注入时的类(除`beanFactory`为当前`beanFactory`其余都为当前容器) 
    </li>
    <li>
        为工厂注入一些环境配置信息(beanName分别为environment、systemProperties、systemEnvironment)
    </li>
</ol>

#### postProcessBeanFactory:
&emsp;&emsp;&emsp;&emsp;该方法时BeanFactory初始化之后再进行后续的一些BeanFactory操作。对于`AnnotationConfigApplicationContext`这是父类的一个空方法。在SpringBoot创建的另外两个web容器的时候(`AnnotationConfigServletWebServerApplicationContext`、`AnnotationConfigReactiveWebServerApplicationContext`)会重写该方法。以后可能会出个SpringBoot原理分析系列详细会讲到这两个容器的创建及准备。

#### invokeBeanFactoryPostProcessors:
`invokeBeanFactoryPostProcessors`方法比较关键。该方法做了以下步骤：
<ol>
    <li>执行ConfigurationClassPostProcessor,这个处理器主要来解析配置类(分为完整配置类和精简配置类，这里只详解带`@Configuration`注解的完整配置类)。</li>
    <li>执行其他BeanFactoryPostProcessor。(分为BeanDefinitionRegistryPostProcessor和BeanFactoryPostProcessor两种接口)</li>
</ol>  

##### 执行ConfigurationClassPostProcessor：  
 该方法主要执行了步骤：
 <ol>
    <li>`ConfigurationClassParser`的parse方法
        <ol>
            <li>
                获取`@PropertySource`注解信息(之后所有的获取注解信息都是分析`shouldSkip`提到的searchWithGetSemantics方法完成的)，使用processPropertySource解析添加配置文件信息。处理过程大致是先创建PropertySource(创建的时候调用loadProperties读取配置文件信息)。之后将该配置文件信息添加到beanFactory的`environment`bean对象中去。
            </li>
            <li>
                获取`@ComponentScans`注解信息(若未获取到则为配置类的目录)。使用`ComponentScanAnnocationParser`来解析需要注册的bean，之后调用`ClassPathBeanDefinitionScanner`的doScan来将beanDefinition注册进容器。
            </li>
        </ol>
    </li>
    <li>`ConfigurationClassBeanDefinitionReader`的loadBeanDefinitions</li>
</ol>
