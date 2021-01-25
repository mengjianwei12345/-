




/**
 * A {@link Process} appears on {@link CPU} <i>x</i>, which requests the usage of
 * another randomly chosen CPU. If the usage is lower than some threshold <i>p</i>,
 * the process is added there. Otherwise, it chooses again, attempting this
 * up to <i>z</i> times. If every chosen CPU has a usage above <i>p</i>,
 * the process is added to <i>x</i>.
 */
public final class First extends Algorithm {
    /**
     * The usage threshold according to which the algorithm chooses a CPU.
     */
    private Double threshold;

    /**
     * Maximum number of attemps until the CPU with sufficient usage has been found.
     */
    private Integer attemptCount;

    /**
     * Class constructor.
     *
     * @param threshold usage threshold according to which the algorithm
     *                  chooses a CPU
     * @param attemptCount attemps until the CPU with sufficient usage
     *                     has been found
     */
    public First(Double threshold, Integer attemptCount) {
        this.threshold = threshold;
        this.attemptCount = attemptCount;
    }

    @Override
    public boolean handleProcess(CPU master, Process process) {
        Machine machine = master.getMaster();
        Set<CPU> CPUSet = new HashSet<>(machine.getCPUSet());
        CPUSet.remove(master);

        // make z attemps
        for(int i = 0; i < attemptCount; i++) {
            // randomly chosen CPU
            CPU randomCPU = CPU.randomCPU(CPUSet);
            if(randomCPU != null) {
                CPUSet.remove(randomCPU);
                machine.increaseUsageRequestCount();
                // ask for usage
                if(randomCPU.getUsage() < threshold) {
                    machine.increaseRelocationCount();
                    // send to that random CPU if usage is below the specified threshold
                    randomCPU.addProcess(process);
                    return true;
                }
            }
        }
        /* all the chosen CPUs have usage above the threshold, so the
        process isn't relocated and is executed in the default cpu,
        which is master */
        master.addProcess(process);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Algorithm #1";
    }
}