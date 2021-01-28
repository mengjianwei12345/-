


public class Robin
{
	public static float waitingTimeRobin(int[] arrival, int[] run, int q)
	{
		int counter =1;
		int wait[] = new int[arrival.length];
		float average_wait_time=0;
		int allArrived = 0;

		Queue<Integer> queue = new LinkedList<Integer>();
		int current_time = arrival[0];
		int exec_time[] = new int[arrival.length];

		for(int i=0;i<arrival.length;i++)
		{
			exec_time[i] = run[i];
			if(current_time==arrival[i])
			{
				queue.add(i);
				counter = i+1;
			}
		}

		if(counter>=arrival.length)
			allArrived = 1;

		while(!queue.isEmpty()||counter<arrival.length-1)
		{
			if(queue.isEmpty())
			{
				queue.add(counter);
				current_time = arrival[counter];
				counter++;
			}
				

			int i = queue.remove();
			
			int start_time = current_time;
			int time =0;

			if(exec_time[i]>q)
				time = q;
			else
				time = exec_time[i];


			exec_time[i] = exec_time[i] - time;
			int stop_time = current_time + time;

			
			for(int element : queue)
				wait[element] = wait[element] + time;

			if(allArrived == 0)
			{
				while(arrival[counter]>=start_time && arrival[counter]<=stop_time)
				{
				queue.add(counter);
				wait[counter] = stop_time - arrival[counter];

				if(counter+1<arrival.length)
					counter++;
				else
				{
					allArrived = 1;
					break;
				}
				}
			}
	
			if(exec_time[i]!=0)
				queue.add(i);

			current_time = current_time + time;
		}

		for(int i=0;i<arrival.length;i++)
			average_wait_time = average_wait_time + wait[i];

		average_wait_time = average_wait_time/arrival.length;
		return average_wait_time;

	}
	public static void main()throws IOException
    {

        int arrival[]=new int[] {0, 1, 4};
        int run[]    =new int[] {5,2,3}; 
        int q=3;
        float awt = waitingTimeRobin(arrival,run,q);
        System.out.println(awt);
        
    }
}