













public abstract class LoadBalancer {

	private static final int MAX_PROVIDERS = 10;
	private static final int INITIAL_DELAY = 1;
	private static final int INTERVAL = 120;
	private final static Logger logger = LogManager.getLogger(LoadBalancer.class);
	private final BlockingQueue queue;
	private Map<Provider, Boolean> providersMap;
	private Map<Provider, Integer> unresponsiveProviders;
	private ThreadPoolExecutor threadPool;

	LoadBalancer() {
		this.providersMap = new ConcurrentHashMap<>();
		this.unresponsiveProviders = new HashMap<>();
		this.heartBeatScheduler(INITIAL_DELAY, INTERVAL);

		this.queue = new ArrayBlockingQueue<Runnable>(MAX_PROVIDERS);
		this.threadPool = new ThreadPoolExecutor(MAX_PROVIDERS, MAX_PROVIDERS,
				0L, TimeUnit.MILLISECONDS, queue);
		threadPool.setRejectedExecutionHandler((r, executor) -> {
			throw new RejectedExecutionException("Maximum number of requests was reached. request is rejected!");
		});
	}

	List<Provider> getActiveProviders() {

		List<Provider> providers = providersMap.entrySet().stream()
				.filter(entry -> entry.getValue().equals(Boolean.TRUE))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
		return providers;
	}

	void includeProvider(Provider p) {
		providersMap.computeIfPresent(p, (key, value) -> Boolean.TRUE);
	}

	void excludeProvider(Provider p) {
		providersMap.computeIfPresent(p, (key, value) -> Boolean.FALSE);
	}

	protected void heartBeatCheck() {
		logger.info("=======================================");
		logger.info("Starting heart-beat check");
		for (Map.Entry<Provider, Boolean> p : providersMap.entrySet()) {

			Provider provider = p.getKey();
			Boolean isActive = p.getValue();
			boolean check = provider.check();

			if (check) {
				//if the provider is active now but wasn't previously increase the counter
				if (isActive.equals(Boolean.FALSE)) {
					logger.info(String.format("[%s] is responsive again!", provider.getName()));
					unresponsiveProviders.put(provider, unresponsiveProviders.getOrDefault(provider, 0) + 1);
					if (unresponsiveProviders.get(provider) >= 2) {
						//activate it back and remove it from dead providers
						includeProvider(provider);
						unresponsiveProviders.remove(provider);
						logger.info(String.format("[%s] is working again, adding it back", provider.getName()));
					}
				}
			} else if (isActive) {  //if it's not active in this iteration but included.
				logger.info(String.format("[%s] is not responsive, will be excluded", provider.getName()));
				excludeProvider(provider);
				unresponsiveProviders.put(provider, 0);
			}
		}
		int capacity = checkCapacity();
		logger.info(String.format("Updating loadbalancer capacity to: [%d]", capacity));

		//update load balancer capacity according to available providers
		this.threadPool.setCorePoolSize(capacity);
		logger.info("End of heart-beat check");
		logger.info("=======================================");

	}

	private int checkCapacity() {
		int maxCapacity = 0;
		for (Provider p : getActiveProviders()) {
			maxCapacity += p.getRequestCapacity();
		}
		return maxCapacity;
	}

	Future<String> executeProvider(Provider provider) {
		return this.threadPool.submit(provider::get);
	}

	public void registerProvider(Provider provider) throws MaxNumberOfProvidersReachedException {
		if (providersMap.size() < MAX_PROVIDERS) {
			this.providersMap.putIfAbsent(provider, Boolean.TRUE);
		} else {
			throw new MaxNumberOfProvidersReachedException();
		}
	}

	protected void heartBeatScheduler(int initialDelay, int interval) {
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(() -> this.heartBeatCheck(), initialDelay, interval, TimeUnit.SECONDS);
	}

	abstract public Future<String> get() throws InterruptedException, NoAvailableProvidersException;
}

