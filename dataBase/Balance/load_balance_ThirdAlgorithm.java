








public class ThirdAlgorithm implements Algorithm {

	private int maxThreshold;
	private int minThreshold;
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
		
		takeProcesses(processors);
	}

	public void setMaxThreshold(int maxThreshold) {
		this.maxThreshold = maxThreshold;
	}
	
	public void setMinThreshold(int minThreshold) {
		this.minThreshold = minThreshold;
	}
	
	// purpose of this function is to balance the processors loads in the system
	private void takeProcesses(List<Processor> processors) {
		List<Processor> processorsUnderThreshold = getProcessorsUnderThreshold(processors);
		Processor currentProcessor;
		Processor randomProcessor;
		int migratedProcesses;
		
		for(int i = 0; i < processorsUnderThreshold.size(); i++) {
			currentProcessor = processorsUnderThreshold.get(i);
			
			// the current processor will "ask" randomly selected processors about its loads
			// and take random processes to lighten them
			for(int j = 0; j < processors.size()/4; j++) {
				randomProcessor = processors.get(new Random().nextInt(processors.size() - 1));
				
				if(!checkIfProcessorIsUnderMaxThreshold(randomProcessor, maxThreshold)) {
					migratedProcesses = currentProcessor.takeProcessesFromProcessor(randomProcessor, maxThreshold - minThreshold);
					migrations += migratedProcesses;
				}
				
				// if current processor exceeded max threshold value loop breaks
				// the processor can't take more processes from overloaded processors
				if(!checkIfProcessorIsUnderMaxThreshold(currentProcessor, maxThreshold)) 
					break;
			}
		}
	}

	private List<Processor> getProcessorsUnderThreshold(List<Processor> processors) {
		List<Processor> processorsUnderThreshold = new ArrayList<Processor>();
		Processor currentProcessor;
		
		for(int i = 0; i < processors.size(); i++) {
			currentProcessor = processors.get(i);
			if(checkIfProcessorUnderMinThreshold(currentProcessor)) 
				processorsUnderThreshold.add(currentProcessor);
		}
		
		return processorsUnderThreshold;
	}

	private boolean checkIfProcessorUnderMinThreshold(Processor processor) {
		return processor.getCurrentLoad() < minThreshold;
	}

	public int getQueries() {
		return queries;
	}

	public int getMigrations() {
		return migrations;
	}
}
