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

            displayQueue.Enqueue(p);
        }

        printTimeline(displayQueue);
    }

    private void printTimeline(Queue<Process> displayQueue) {
        System.out.println("\n===== FCFS Timeline Events =====");

        Node<Process> cur = displayQueue.getFront();
        while (cur != null) {
            Process p = cur.getData();
            System.out.println(
                    "P" + p.getId() +
                    " | Arrival: " + p.getArrivalTime() +
                    " | Burst: " + p.getBurstTime() +
                    " | Start: " + p.getStartTime() +
                    " | Waiting: " + p.getWaitingTime() +
                    " | Turnaround: " + p.getTurnAroundTime() +
                    " | Response: " + p.getResponseTime()
            );
            cur = cur.getNextNode();
        }
    }
}