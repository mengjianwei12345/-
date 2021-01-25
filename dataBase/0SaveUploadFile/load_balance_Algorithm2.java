
//
//
//
//


public class Algorithm2 implements Algorithm {

	public long startTime;
	public long endTime;
	public long runTime;

	public RequestHandlePlan selectServer(Request r) {
		Server s = null; //assigned server
		Controller assignedController = r.getDefaultController();
		Vector<Server> servers = assignedController.getAllServers();
		
		int i = 0;
		servers = new Algorithm2().bubbleSort(servers, assignedController); //sort by distance to controller
		
		boolean valid = false;
		while(valid == false)
		{
			if((servers.elementAt(i).getLoad() + r.getLoad()) > servers.elementAt(i).getCapacity())
			{
				i++;
			}
			else
			{
				valid = true;
				s = servers.elementAt(i);
			}
		}
		if(valid == false) //if default is full
		{
			assignedController = assignedController.getDefaultBackupController1();
			servers = assignedController.getAllServers();
			servers = new Algorithm2().bubbleSort(servers, assignedController); //sorted
			
			while(valid == false)
			{
				if((servers.elementAt(i).getLoad() + r.getLoad()) > servers.elementAt(i).getCapacity())
				{
					i++;
				}
				else
				{
					valid = true;
					s = servers.elementAt(i);
				}
			}
		}
		if(valid == false) //if backup 1 is full
		{
			assignedController = assignedController.getDefaultBackupController2();
			servers = assignedController.getAllServers();
			servers = new Algorithm2().bubbleSort(servers, assignedController); //sorted
			
			while(valid == false)
			{
				if((servers.elementAt(i).getLoad() + r.getLoad()) > servers.elementAt(i).getCapacity())
				{
					i++;
				}
				else
				{
					valid = true;
					s = servers.elementAt(i);
				}
			}
		}
		// if all backups are full, send back null Server
		
		endTime = System.nanoTime();
		runTime = endTime - startTime;
		RequestHandlePlan rhp = new RequestHandlePlan(s.getController(), s, r,
				runTime); 
		return rhp;
	}
	public Vector<Server> bubbleSort(Vector<Server> servers, Controller c)
	{
		boolean swapped = true;
		int i;
		while(swapped)  //bubble sort by distance to controller
		{
			swapped = false;
			for(i = 0; i< servers.size() -1; i++)
			{
				if(servers.elementAt(i).getDistance(c) > servers.elementAt(i+1).getDistance(c))
				{
					Server temp = servers.get(i);
					servers.setElementAt(servers.elementAt(i+1), i);
					servers.setElementAt(temp, i+1);
					
					swapped = true;
				}
			}
		}
		return servers;
	}
}
