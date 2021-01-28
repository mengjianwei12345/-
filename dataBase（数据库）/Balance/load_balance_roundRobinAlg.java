












/**
 *
 * @author Yoseph
 */
public final class roundRobinAlg {
    Queue<Integer> rrQ;
    Set<Integer> name;
    Iterator<Integer> iterator;
    
    private roundRobinAlg (Map<Integer, Integer[]> process, int q) {
        this.rrQ = new LinkedList<>();
	this.name = process.keySet();
	this.iterator = name.iterator();
        int t = 0;
        itForward();
        roundRobin(rrQ, process, t, q);
	output(process);
    }    
    
    private void roundRobin(Queue<Integer> rrQ, Map<Integer, Integer[]> process, int t, int q){
        Integer now;
        Integer burst;
	while(!rrQ.isEmpty()){
            now = rrQ.remove();
            burst = (process.get(now))[1];
            if(burst - q < 0) {
		t += burst;
                process.get(now)[1] = 0;
		process.get(now)[2] = t;
            } else {
		t += q;
		process.get(now)[1] -= q;
		if(process.get(now)[1] == 0) {
                    process.get(now)[2] = t;
		} else {
                    rrQ.add(now);
		}
            }
	}
    }

    private static String getQ(String inputFile) throws IOException {
        FileReader file = null;
        String lastLine;
	try {
            file = new FileReader(inputFile);
	} catch (FileNotFoundException e) {
            err.println("File is not found" + e);
        }       
	try (BufferedReader bufferedReader = new BufferedReader(file)) {
            String strLine = null, tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                 strLine = tmp;
            }
            lastLine = strLine;
        }      	
       return lastLine;
    }
        
    private void itForward(){
	while(iterator.hasNext()) {
            rrQ.add(iterator.next());
	}        
    }

    public static Map <Integer, Integer[]> getData(String inputFile) throws IOException {
        Map<Integer, Integer[]> process = new LinkedHashMap<>();
	FileReader file = null;

	try {
            file = new FileReader(inputFile);
	} catch (FileNotFoundException e) {
            err.println("File is not found" + e);
        }
	try (BufferedReader bufferedReader = new BufferedReader(file)) {
            String line;
            try {
                String[] input;
                bufferedReader.readLine();
                Integer[] data;
                int count = 0;               
                while ((line = bufferedReader.readLine()) != null) {
                    input = line.split(" ");    
                    if(line.length() == 1){
                        bufferedReader.readLine();
                    }else {                      
                        data = new Integer[4];
                        data[0] = Integer.parseInt(input[2]);
                        data[1] = Integer.parseInt(input[2]);
                        count++;
                        process.put(count, data);
                    }
                }
            } catch (IOException e) {
                err.print("Error from getData" + e);
            }
	}	
	return process;
    }        
        
    private static void output(Map<Integer, Integer[]> process) {
	int processes = 0;
	int x = 0;
	int y = 0;
        int i;

	Iterator<Integer[]> iterator = process.values().iterator();
	while (iterator.hasNext()) {
            Integer[] wt = iterator.next();
            wt[3] = wt[2] - wt[0];
            processes++;
            x += wt[2];
            y += wt[3];
        }	
	for(i = 1; i <= processes; i++) {
            out.println("Process: " + (i));
            out.println("TAT: "+(process.get(i))[2]);
            out.println("WT: " + (process.get(i))[3] + "\n");
        }		
    }
}
