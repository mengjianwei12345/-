




/**
 * A {@link Process} appears on {@link CPU} <i>x</i>. It is handled the same way
 * as in algorithm #2, but additionally CPUs with usage lower than some minimal
 * threshold <i>r</i> ask randomly chosen CPUs for usage and if it exceeds
 * <i>p</i>, the asking CPU takes a portion of its processes.
 */
public final class Third extends Algorithm {
    private Double lowerThreshold;
    private Double upperThreshold;
    private Double sharePortion;

    public Third(Double lowerThreshold, Double upperThreshold, Double sharePortion) {
        this.lowerThreshold = lowerThreshold; // r
        this.upperThreshold = upperThreshold; // p
        this.sharePortion = sharePortion;
    }

    @Override
    public boolean handleProcess(CPU master, Process process) {
        Machine machine = master.getMaster();
        Set<CPU> CPUSet = new HashSet<>(machine.getCPUSet());
        CPUSet.remove(master);

        if(master.getUsage() > upperThreshold) {
            // try every cpu
            while(!CPUSet.isEmpty()) {
                // randomly chosen CPU
                CPU randomCPU = CPU.randomCPU(CPUSet);
                if(randomCPU != null) {
                    CPUSet.remove(randomCPU);
                    machine.increaseUsageRequestCount();
                    // ask for usage
                    if(randomCPU.getUsage() <= upperThreshold) {
                        machine.increaseRelocationCount();
                        // send to that random CPU if usage is below the specified threshold
                        randomCPU.addProcess(process);
                        return true;
                    }
                }
            }
            // all the chosen CPUs have usage above the threshold, so the process isn't relocated
            return false;
        }
        else {
            master.addProcess(process);
            return true;
        }
    }

    @Override
    public void step(CPU master) {
        Machine machine = master.getMaster();
        Set<CPU> CPUSet = new HashSet<>(machine.getCPUSet());
        CPUSet.remove(master);

        if(master.getUsage() < lowerThreshold) {
            // try every cpu
            while(!CPUSet.isEmpty()) {
                // randomly chosen CPU
                CPU randomCPU = CPU.randomCPU(CPUSet);
                if(randomCPU != null) {
                    CPUSet.remove(randomCPU);
                    machine.increaseUsageRequestCount();
                    // ask for usage
                    if(randomCPU.getUsage() > upperThreshold) {
                        Set<Process> processPortion = randomCPU.getProcessPortion(sharePortion);
                        machine.increaseRelocationCount(processPortion.size());
                        master.addProcesses(processPortion);
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Algorithm #3";
    }
}