





public class Algorithm1 implements Algorithm {

	public long startTime;
	public long endTime;
	public long runTime;

	public RequestHandlePlan selectServer(Request r) {
		startTime = System.nanoTime();
		Server s;
		Controller[] controllers = ServerFactory.getInstance().getControllers();
		int totalLoad = 0;
		double previous = 0;
		List<Double> ratioArray = new ArrayList<Double>();
		List<Integer> loadArray = new ArrayList<Integer>();

		for (Controller item : controllers) {
			int load = item.getLoad();
			loadArray.add(load); 
			totalLoad += load;
		}
		for (Iterator<Integer> i = loadArray.iterator(); i.hasNext();) {
			Integer load = i.next();
			if (totalLoad > 0) {
				ratioArray.add((double) (load / totalLoad) + previous);
				previous += load / totalLoad;
			} else {
				ratioArray.add(previous);
			}

		}
		double rand = Math.random();
		int index = 0;
		int flag = 0;
		Controller assignedController = controllers[0];
		for (Iterator<Double> i = ratioArray.iterator(); i.hasNext();) {
			Double ratio = i.next();
			if (rand < ratio) {
				index = ratioArray.indexOf(ratio);
				assignedController = controllers[index];
				flag = 1;
				break;
			}
			flag = 0;
		}
		if (flag == 0)
			System.out.println("Ratios not set correctly\n");
		int totalServLoad = 0;
		double[] servRatioArr = new double[6];
		for (Server server : assignedController.getAllServers()) {
			totalServLoad += server.getLoad();
		}
		previous = 0;
		int i = 0;
		for (Server server : assignedController.getAllServers()) {
			if (totalServLoad > 0) {
				servRatioArr[i++] = server.getLoad() / totalServLoad + previous;
				previous += server.getLoad() / totalServLoad;
			} else {
				servRatioArr[i++] = previous;
			}
		}
		rand = Math.random();
		flag = 0;
		s = assignedController.getAllServers().get(0);
		for (i = 0; i < 6; i++) {
			if (rand < servRatioArr[i]) {
				s = assignedController.getAllServers().get(i);
				flag = 1;
				break;
			}
			flag = 0;
		}
		if (flag == 0)
			System.out.println("Ratios not set correctly\n");
		endTime = System.nanoTime();
		runTime = endTime - startTime;
		RequestHandlePlan rhp = new RequestHandlePlan(s.getController(), s, r,
				runTime); 
		return rhp;
	}
}