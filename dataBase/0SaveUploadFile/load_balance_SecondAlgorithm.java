






public class SecondAlgorithm implements Algorithm {

	private int maxThreshold;
	private int queries;
	private int migrations;
	
	@Override
	public void assignProcess(List<Processor> processors, Process process) {
		queries = 0;
		migrations = 0;
		
		Processor firstProcessor = getFirstProcessor(processors, process);
		boolean processorFound = false;
		
		firstProcessor.addProcess(new Process(1, 3, process.getFirstProcessorId()));
		
		if(checkIfProcessorIsUnderMaxThreshold(firstProcessor, maxThreshold)) {
			firstProcessor.addProcess(process);
			processorFound = true;
		}
		
		if(!processorFound) {
			Processor drawnProcessor;
			
			do {
				queries++;
				drawnProcessor = drawProcessor(processors, process.getFirstProcessorId());
			} while(!checkIfProcessorIsUnderMaxThreshold(drawnProcessor, maxThreshold));
			
			drawnProcessor.addProcess(process);
			migrations++;
		}
	}

	public void setMaxThreshold(int maxThreshold) {
		this.maxThreshold = maxThreshold;
	}

	public int getQueries() {
		return queries;
	}

	public int getMigrations() {
		return migrations;
	}
}
