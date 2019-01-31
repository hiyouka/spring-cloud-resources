
 ````
    //序列化id映射到工厂实例
	private static final Map<String, Reference<DefaultListableBeanFactory>> serializableFactories =
			new ConcurrentHashMap<>(8);

	//是否允许使用相同名称重新注册不同的定义
	private boolean allowBeanDefinitionOverriding = true;

	// Whether to allow eager class loading even for lazy-init beans
	private boolean allowEagerClassLoading = true;

	/** Optional OrderComparator for dependency Lists and arrays */
	private Comparator<Object> dependencyComparator;

	/** Resolver to use for checking if a bean definition is an autowire candidate */
	//@Autowire解析器
	private AutowireCandidateResolver autowireCandidateResolver = new SimpleAutowireCandidateResolver();

	/** Map from dependency type to corresponding autowired value */
	private final Map<Class<?>, Object> resolvableDependencies = new ConcurrentHashMap<>(16);

	/** Map of bean definition objects, keyed by bean name */ //bean定义信息集合
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

	/** Map of singleton and non-singleton bean names, keyed by dependency type */
	//根据类型映射到所有的非单例和单例bean名称
	private final Map<Class<?>, String[]> allBeanNamesByType = new ConcurrentHashMap<>(64);

	/** Map of singleton-only bean names, keyed by dependency type */
	// 根据类型映射到所有的单例bean名称
	private final Map<Class<?>, String[]> singletonBeanNamesByType = new ConcurrentHashMap<>(64);

	/** List of bean definition names, in registration order */
	// 所有的bean定义信息名称(经过注册顺序排序的)
	private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

	/** List of names of manually registered singletons, in registration order */
	// 所有手动注册的单例bean名称(即spring自己创建的)
	private volatile Set<String> manualSingletonNames = new LinkedHashSet<>(16);

	/** Cached array of bean definition names in case of frozen configuration */
    // 冻结配置下 缓存的bean定义的信息名称
	private volatile String[] frozenBeanDefinitionNames;

	/** Whether bean definition metadata may be cached for all beans */
	//是否可以为所有bean缓存bean定义元数据
	private volatile boolean configurationFrozen = false;
	
	
------------------------------------------------------------------------------------------------------------------	
	// extends from AbstractAutowireCapableBeanFactory
		/** Strategy for creating bean instances */
	// 创建bean实例的策略
	private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

	/** Resolver strategy for method parameter names */
	//方法参数名称的解析器策略
	private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

	/** Whether to automatically try to resolve circular references between beans */
	//是否自动尝试解析bean之间的循环引用
	private boolean allowCircularReferences = true;

	/**
	 Whether to resort to injecting a raw bean instance in case of circular reference,
	 even if the injected bean eventually got wrapped.
	 */
	 //是否在循环引用的情况下求助于注入原始bean实例，即使注入的bean最终被包裹。
	private boolean allowRawInjectionDespiteWrapping = false;

	/**
	 * Dependency types to ignore on dependency check and autowire, as Set of
	 * Class objects: for example, String. Default is none.
	 */
	 //自动装配忽略的类型
	private final Set<Class<?>> ignoredDependencyTypes = new HashSet<>();

	/**
	 * Dependency interfaces to ignore on dependency check and autowire, as Set of
	 * Class objects. By default, only the BeanFactory interface is ignored.
	 */
	 //自动装配忽略的接口类型
	private final Set<Class<?>> ignoredDependencyInterfaces = new HashSet<>();

	/**
	 * The name of the currently created bean, for implicit dependency registration
	 * on getBean etc invocations triggered from a user-specified Supplier callback.
	 */
	 //当前创建的bean的名称，用于隐式依赖项注册从用户指定的供应商回调触发的getBean等调用。
	private final NamedThreadLocal<String> currentlyCreatedBean = new NamedThreadLocal<>("Currently created bean");

	/** Cache of unfinished FactoryBean instances: FactoryBean name --> BeanWrapper */
	//未完成的FactoryBean实例的缓存：FactoryBean名称
	private final Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>(16);

	/** Cache of filtered PropertyDescriptors: bean Class -> PropertyDescriptor array */
	//过滤的PropertyDescriptors的缓存：bean类 - > PropertyDescriptor数组
	private final ConcurrentMap<Class<?>, PropertyDescriptor[]> filteredPropertyDescriptorsCache =
			new ConcurrentHashMap<>(256);
			
----------------------------------------------------------------------------------------------------------------------------	
	//extends from AbstractBeanFactory
	
	/** Parent bean factory, for bean inheritance support */
	@Nullable
	private BeanFactory parentBeanFactory;

	/** ClassLoader to resolve bean class names with, if necessary */
	@Nullable
	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	/** ClassLoader to temporarily resolve bean class names with, if necessary */
	@Nullable
	private ClassLoader tempClassLoader;

	/** Whether to cache bean metadata or rather reobtain it for every access */
	//是否缓存bean元数据，或者为每次访问重新获取它
	private boolean cacheBeanMetadata = true;

	/** Resolution strategy for expressions in bean definition values */
	@Nullable
	private BeanExpressionResolver beanExpressionResolver;

	/** Spring ConversionService to use instead of PropertyEditors */
	@Nullable
	private ConversionService conversionService;

	/** Custom PropertyEditorRegistrars to apply to the beans of this factory */
	//自定义PropertyEditorRegistrars以应用于此工厂的bean
	private final Set<PropertyEditorRegistrar> propertyEditorRegistrars = new LinkedHashSet<>(4);

	/** Custom PropertyEditors to apply to the beans of this factory */
	自定义PropertyEditors应用于此工厂的bean
	private final Map<Class<?>, Class<? extends PropertyEditor>> customEditors = new HashMap<>(4);

	/** A custom TypeConverter to use, overriding the default PropertyEditor mechanism */
	@Nullable
	private TypeConverter typeConverter;

	/** String resolvers to apply e.g. to annotation attribute values */
	private final List<StringValueResolver> embeddedValueResolvers = new LinkedList<>();

	/** BeanPostProcessors to apply in createBean */
	// 创建bean使用的beanPostProcess
	private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

	/** Indicates whether any InstantiationAwareBeanPostProcessors have been registered */
	//指示是否已注册任何InstantiationAwareBeanPostProcessors
	private boolean hasInstantiationAwareBeanPostProcessors;

	/** Indicates whether any DestructionAwareBeanPostProcessors have been registered */
	//指示是否已注册任何DestructionAwareBeanPostProcessors
	private boolean hasDestructionAwareBeanPostProcessors;

	/** Map from scope identifier String to corresponding Scope */
	//从范围标识符String映射到相应的Scope
	private final Map<String, Scope> scopes = new LinkedHashMap<>(8);

	/** Security context used when running with a SecurityManager */
	@Nullable
	private SecurityContextProvider securityContextProvider;

	/** Map from bean name to merged RootBeanDefinition */
	//从bean名称映射到合并的RootBeanDefinition
	private final Map<String, RootBeanDefinition> mergedBeanDefinitions = new ConcurrentHashMap<>(256);

	/** Names of beans that have already been created at least once */
	//已经创建至少一次的bean的名称
	private final Set<String> alreadyCreated = Collections.newSetFromMap(new ConcurrentHashMap<>(256));

	/** Names of beans that are currently in creation */
	//当前正在创建的bean的名称
	private final ThreadLocal<Object> prototypesCurrentlyInCreation =
			new NamedThreadLocal<>("Prototype beans currently in creation");

----------------------------------------------------------------------------------------------------------------------
    // extends from FactoryBeanRegistrySupport
    /** Cache of singleton objects created by FactoryBeans: FactoryBean name --> object */
    //FactoryBeans创建的单例对象的缓存：FactoryBean名称
	private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>(16);


-----------------------------------------------------------------------------------------------------------------------
    // extends from DefaultSingletonBeanRegistry
    /** Cache of singleton objects: bean name --> bean instance */
    //单例对象的缓存：bean名称 --> bean 实例
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

	/** Cache of singleton factories: bean name --> ObjectFactory */
	//单例工厂的缓存：bean名称--> ObjectFactory
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

	/** Cache of early singleton objects: bean name --> bean instance */
	//早期单例对象的缓存：bean名称 - > bean实例
	private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

	/** Set of registered singletons, containing the bean names in registration order */
	// 按注册顺序的已被注册的单例bean名称
	private final Set<String> registeredSingletons = new LinkedHashSet<>(256);

	/** Names of beans that are currently in creation */
	//正在创建的bean的名称
	private final Set<String> singletonsCurrentlyInCreation =
			Collections.newSetFromMap(new ConcurrentHashMap<>(16));

	/** Names of beans currently excluded from in creation checks */
	// 目前从创建检查中被排除的bean名称
	private final Set<String> inCreationCheckExclusions =
			Collections.newSetFromMap(new ConcurrentHashMap<>(16));

	/** List of suppressed Exceptions, available for associating related causes */
	//被抑制的异常列表，可用于关联相关原因
	private Set<Exception> suppressedExceptions;

	/** Flag that indicates whether we're currently within destroySingletons */
	//指示我们当前是否在destroySingletons中的标志
	private boolean singletonsCurrentlyInDestruction = false;

	/** Disposable bean instances: bean name --> disposable instance */
	//一次性bean实例：bean名称 - >一次性实例
	private final Map<String, Object> disposableBeans = new LinkedHashMap<>();

	/** Map between containing bean names: bean name --> Set of bean names that the bean contains */
	//包含bean名称之间的映射：bean name  - > bean包含的bean名称集
	private final Map<String, Set<String>> containedBeanMap = new ConcurrentHashMap<>(16);

	/** Map between dependent bean names: bean name --> Set of dependent bean names */
	//依赖bean名称之间的映射：bean name  - >依赖bean名称的集合
	private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

	/** Map between depending bean names: bean name --> Set of bean names for the bean's dependencies */
	//依赖bean名称之间的映射：bean name  - > bean依赖项的bean名称集
	private final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);


-------------------------------------------------------------------------------------------------------------------
    //extends from SimpleAliasRegistry
    /** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	/** Map from alias to canonical name */
	private final Map<String, String> aliasMap = new ConcurrentHashMap<>(16);
````
