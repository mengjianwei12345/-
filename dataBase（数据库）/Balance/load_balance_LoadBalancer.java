

public class LoadBalancer {

    private List<String> serverList;
    private String singleServer;
    private int load;

    public LoadBalancer(){}

    public LoadBalancer(IpPool ipPool, int variant) {
        Set<String> servers = ipPool.ipMap.keySet();
        serverList = new ArrayList<>();
        try {
            //First case: will simply pass the requests sequentially in rotation to each of the hosts in the list
            if (variant == 1) {
                Iterator<String> iterator = servers.iterator();
                while (iterator.hasNext()) {
                    String server = iterator.next();
                    Float weight = ipPool.ipMap.get(server);
                    if (weight != null && weight > 0) {
                        for (int i = 0; i < weight; i++) {
                            serverList.add(server);
                        }
                    }
                }
            }
            //Second case: will either take the first host that has a load under 0.75 or if all hosts in the list are above 0.75, it will take the one with the lowest load.
            else if (variant == 2) {
                Iterator<String> iterator = servers.iterator();
                ArrayList<Float> weights = new ArrayList<>();
                boolean isSingleServerHasFound = false;
                while (iterator.hasNext() && !isSingleServerHasFound) {
                    String server = iterator.next();
                    Float weight = ipPool.ipMap.get(server);
                    weights.add(weight);
                    if (weight != null && weight > 0) {
                        for (int i = 0; i < weight; i++) {
                            if (weights.get(i) < 0.75f) {
                                singleServer = server;
                                isSingleServerHasFound = true;
                                break;
                            }

                        }
                        if (weights.indexOf(Collections.min(weights)) > 0.75f) {
                            singleServer = server;
                            isSingleServerHasFound = true;
                        }
                    }
                }
            }
            else {
                System.out.println("Invalid variant!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

//    public void handleRequest(Request request) {
//        LoadBalancer loadBalancer = new LoadBalancer(request.getIpPool(), request.getVariant());
//    }


    public List<String> getServerList() {
        return serverList;
    }

    public String getSingleServer(){
        return singleServer;
    }
}