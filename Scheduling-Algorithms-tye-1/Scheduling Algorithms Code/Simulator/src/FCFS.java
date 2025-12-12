public class FCFS extends Process {
    public FCFS() { super(); }

    public void fcfsScheduling() {

        int time = 0;


        Queue<Process> displayQueue = new Queue<>();

        for (int i = 0; i < Main.numProcess; i++) {

            int pid = Main.ProcessLink[i][Field.id.getValue()];
            int arrival = Main.ProcessLink[i][Field.arrivalTime.getValue()];
            int burst = Main.ProcessLink[i][Field.burstTime.getValue()];



            if (time < arrival) {
                time = arrival;
            }

            int start = time;
            int completion = start + burst;

            int tat = completion - arrival;
            int wt  = start - arrival;
            int rt  = wt;

            time = completion;


            Main.ProcessLink[i][Field.startTime.getValue()] = start;
            Main.ProcessLink[i][Field.turnAroundTime.getValue()] = tat;
            Main.ProcessLink[i][Field.waitingTime.getValue()] = wt;
            Main.ProcessLink[i][Field.responseTime.getValue()] = rt;

            Process p = new FCFS();
            p.setId(pid);
            p.setArrivalTime(arrival);
            p.setBurstTime(burst);
            p.setStartTime(start);
            p.setTurnAroundTime(tat);
            p.setWaitingTime(wt);
            p.setResponseTime(rt);
            for (int j = 0; j < burst; j++) {
            displayQueue.Enqueue(p);
            }
        }
        System.out.println("\n===== FCFS Timeline Events =====");
        displayQueue.displayGanttFromQueue(1, time);
        System.out.println("Details of Processes:");
       
    }
}