







public class RandomStrategy implements LoadBalancerStrategy {

    private final String NAME = "Random";

    @Override
    public Server selectServer(final ServerPool serverPool) {
        List<Server> serverList = serverPool.getAvailableServers();
        return serverList.get(new Random().nextInt(serverList.size()));
    }

    @Override
    public String getName() {
        return NAME;
    }
}
