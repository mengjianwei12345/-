

/*
 * A class for balancing tasks across processes.
 */
public class LoadBalance
{
    /*
     * Balance a list of tasks across processes.
     *
     * @param processes: The number of process to balance across.
     * @param tasks: The tasks to balance across the processes.
     * @param partitions: An array for the balanced task workloads to be inserted into.
     * @return: The size of the largest partition.
     */
    public static int loadBalance(int processes, int[] tasks, int[] partitions)
    {
        int max_task = max(tasks);
        int workload_bound = (sum(tasks)/processes) + max_task; //initial bound
        int[] bounded_partition = boundedPartition(tasks, processes, workload_bound);
        while (bounded_partition == null) { //while the bound is too small
            bounded_partition = boundedPartition(tasks, processes, workload_bound+=max_task);
        }
        while (bounded_partition != null) { //while the bound is too large
            workload_bound = max(bounded_partition);
            bounded_partition = boundedPartition(tasks, processes, workload_bound);
        }
        // Bound is 1 less than the optimal bound at this point.
        while (bounded_partition == null) {
            //Should only loop once to increase the bound by one, this just silences null pointer warnings
            bounded_partition = boundedPartition(tasks, processes, workload_bound++);
        }
        System.arraycopy(bounded_partition, 0, partitions, 0, bounded_partition.length);
        return max(bounded_partition);
    }

    /*
     * Partition a list of jobs across a number of processes,
     * without letting a partition reach some given bound.
     *
     * @param jobs: The jobs to balance across the processes.
     * @param processes: The number of processes to balance across.
     * @param bound: The non-inclusive maximum value that a partition's workload can reach.
     * @return: The partitioned jobs, or null if it is not possible to partition them with the given bound.
     */
    private static int[] boundedPartition(int[] jobs, int processes, int bound) {
        int[] partitions = new int[processes];
        int partitioned_elements = 0;
        for (int i = 0; i < processes; i++){
            for (int j = partitioned_elements; j < jobs.length; j++) {
                int curr_job = jobs[j];
                if (partitions[i] + curr_job < bound) { // Adding the job does not exceed the bound.
                    partitions[i] += curr_job;
                    partitioned_elements += 1;
                }
                else {
                    break; // Reached the bound, move onto to the next partition.
                }
            }
        }
        if (partitioned_elements < jobs.length) { // If we didn't partition all the jobs. (bound too low)
            return null;
        }
        else {
            return partitions;
        }
    }

    /*
     * Sum a list of numbers.
     *
     * @param values: A list of numbers to sum.
     * @return: The sum of the list of numbers.
     */
    private static int sum(int[] values) {
        int total = 0;
        for (int value : values) {
            total += value;
        }
        return total;
    }

    /*
     * Get the max value of a list.
     *
     * @param values: The list to get the max value of.
     * @return: The max value in the list.
     */
    private static int max(int[] values) {
        int curr_max = 0;
        for (int value : values) {
            if (value > curr_max) {
                curr_max = value;
            }
        }
        return curr_max;
    }
 }

