








public class RoundRobinLoadBalancer extends LoadBalancer {

	private final AtomicInteger position;

	public RoundRobinLoadBalancer() {
		super();
		this.position = new AtomicInteger(0);
	}

	@Override
	public Future<String> get() throws NoAvailableProvidersException {
		List<Provider> providerList = getActiveProviders();
		if (providerList.isEmpty()) {
			throw new NoAvailableProvidersException();
		}
		Provider target;

		synchronized (this.position) {
			if (this.position.get() > providerList.size() - 1) {
				this.position.set(0);
			}
			target = providerList.get(this.position.get());
			this.position.incrementAndGet();
		}
		return super.executeProvider(target);
	}
}

