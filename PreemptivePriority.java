//PreemptivePriority.java
//Implementation of the Pre-Emptive Priority Scheduling Algorithm
//Lower priority number = higher schdeuling priority

public class PreemptivePriority extends Process
{
    private int[][] procArr;
    private int id;

    //Copy process list from Main.java
    public PriorityPreemptive(int[][] ProcessLink) 
    {
        super(Main.ProcessLink);
        procArr = Main.ProcessLink;
    }

    //Main scheduling method
    public void priorityPreemptiveScheduling() 
    {
    	//Builds a list of processes
        java.util.List<int[]> procList = new java.util.ArrayList<>();
        for (int i = 0; i < Main.numProcess; i++) {
            int pid = Main.ProcessLink[i][Field.id.getValue()];
            int at = Main.ProcessLink[i][Field.arrivalTime.getValue()];
            int bt = Main.ProcessLink[i][Field.burstTime.getValue()];
            int pri = Main.ProcessLink[i][Field.priority.getValue()];
            procList.add(new int[]{pid, at, bt, pri, bt}); // last element = remaining time
        }

        int time = 0;
        int remaining = procList.size(); //number of unfinished processes
        Queue<Process> displayQueue = new Queue<>();

        //Loops until all processes are finished
        while (remaining > 0) 
        {
        	//Collecting the processes that have arrived
            java.util.List<int[]> ready = new java.util.ArrayList<>();
            for (int[] row : procList) {
                if (row != null && row[1] <= time && row[4] > 0) {
                    ready.add(row);
                }
            }

            if (ready.isEmpty()) {
                time++;
                continue;
            }

            // Choose highest priority (lowest pri value)
            int[] chosen = ready.get(0);
            for (int[] cand : ready) {
                if (cand[3] < chosen[3] || //smaller priority = higher
                   (cand[3] == chosen[3] && cand[1] < chosen[1]) || 
                   (cand[3] == chosen[3] && cand[1] == chosen[1] && cand[0] < chosen[0])) {
                    chosen = cand;
                }
            }

            // Create process object for tracking
            PriorityPreemptive proc = new PriorityPreemptive(procArr);
            proc.setId(chosen[0]);
            if (proc.getStartTime() == null) {
                proc.setStartTime(time);
            }

            // Run for 1 time unit (preemptive)
            chosen[4]--; //Decrease the remaining time
            time++;

            //If process is finished after this unit: 
            if (chosen[4] == 0) {
                int completion = time;
                int tat = completion - chosen[1];
                int wt = tat - chosen[2];
                int rt = proc.getStartTime() - chosen[1];

                //Save metrics into process object
                proc.setTurnAroundTime(tat);
                proc.setWaitingTime(wt);
                proc.setResponseTime(rt);

                //Update the Main.ProcessLink table for reporting
                addToProcessList(proc);

                //Mark process as finished in procList
                for (int i = 0; i < procList.size(); i++) {
                    int[] r = procList.get(i);
                    if (r != null && r[0] == chosen[0]) {
                        procList.set(i, null);
                        remaining--;
                        break;
                    }
                }
            }

            displayQueue.Enqueue(proc);
        }

        // print results
        Node<Process> cur = displayQueue.getFront();
        while (cur != null) {
            Process p = cur.getData();
            System.out.println("Process ID: " + p.getId()
                    + " | Arrival: " + p.getArrivalTime()
                    + " | Burst: " + p.getBurstTime()
                    + " | Start: " + p.getStartTime()
                    + " | Waiting: " + p.getWaitingTime()
                    + " | Turnaround: " + p.getTurnAroundTime()
                    + " | Response: " + p.getResponseTime());
            cur = cur.getNextNode();
        }
    }

    //Updates the results back into the Main.ProcessLink
    public void addToProcessList(PriorityPreemptive process) {
        for (int i = 0; i < Main.numProcess; i++) {
            if (Main.ProcessLink[i][Field.id.getValue()] == process.getId()) {
                Main.ProcessLink[i][Field.turnAroundTime.getValue()] = process.getTurnAroundTime();
                Main.ProcessLink[i][Field.waitingTime.getValue()] = process.getWaitingTime();
                Main.ProcessLink[i][Field.responseTime.getValue()] = process.getResponseTime();
            }
        }
    }


}
