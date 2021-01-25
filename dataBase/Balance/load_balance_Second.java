




/**
 * A {@link Process} appears on {@link CPU} <i>x</i>. If the usage of <i>x</i>
 * exceeds some threshold <i>p</i>, the process is sent to a randomly chosen CPU
 * <i>y</i> with usage lower than <i>p</i> (if <i>y</i>'s usage is greater than
 * <i>p</i>, a CPU is chosen again). If the usage doesn't exceed said threshold,
 * the process is sent to <i>x</i>.
 */
public final class Second extends Algorithm {
    private Double threshold;

    public Second(Double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean handleProcess(CPU master, Process process) {
        Machine machine = master.getMaster();
        Set<CPU> CPUSet = new HashSet<>(machine.getCPUSet());
        CPUSet.remove(master);

        if(master.getUsage() > threshold) {
            // try every cpu
            while(!CPUSet.isEmpty()) {
                // randomly chosen CPU
                CPU randomCPU = CPU.randomCPU(CPUSet);
                if(randomCPU != null) {
                    CPUSet.remove(randomCPU);
                    machine.increaseUsageRequestCount();
                    // ask for usage
                    if(randomCPU.getUsage() <= threshold) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Algorithm #2";
    }
}