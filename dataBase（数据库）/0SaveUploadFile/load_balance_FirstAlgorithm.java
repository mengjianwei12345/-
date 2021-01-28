






public class FirstAlgorithm implements Algorithm {

	private int maxThreshold;
	private int maxNumberOfDraws;
	private int queries = 0;
	private int migrations = 0;
	
	@Override
	public void assignProcess(List<Processor> processors, Process process) {
		queries = 0;
		migrations = 0;
		
		Processor firstProcessor = getFirstProcessor(processors, process); // reference to processor on which process arrives
		Processor drawnProcessor;
		boolean processorFound = false;
		
		firstProcessor.addProcess(new Process(1, 3, process.getFirstProcessorId())); // algorithm is defined as new process which is executed on first processor
		
		for(int i = 0; i < maxNumberOfDraws; i++) {
			queries++;
			drawnProcessor = drawProcessor(processors, process.getFirstProcessorId());
			if(checkIfProcessorIsUnderMaxThreshold(drawnProcessor, maxThreshold)) {
				drawnProcessor.addProcess(process);
				processorFound = true;
				break;
			}
		}
		
		if(!processorFound)
			firstProcessor.addProcess(process);
		
		migrations++;
	}
	
	public void setMaxThreshold(int maxThreshold) {
		this.maxThreshold = maxThreshold;
	}
	
	public void setMaxNumberOfDraws(int maxNumberOfDraws) {
		this.maxNumberOfDraws = maxNumberOfDraws;
	}

	public int getQueries() {
		return queries;
	}

	public int getMigrations() {
		return migrations;
	}
}
