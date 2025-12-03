package scheduler;

public class FCFS extends Process {

    public FCFS() { super(); }

    public void fcfsScheduling() {

        int time = 0;
        int totalBusy = 0;

        double totalWT = 0;
        double totalTAT = 0;
        double totalRT = 0;

        Queue<Process> displayQueue = new Queue<>();

        for (int i = 0; i < Main.numProcess; i++) {

            int pid = Main.ProcessLink[i][Field.id.getValue()];
            int arrival = Main.ProcessLink[i][Field.arrivalTime.getValue()];
            int burst = Main.ProcessLink[i][Field.burstTime.getValue()];

            totalBusy += burst;

            if (time < arrival) {
                time = arrival;
            }

            int start = time;
            int completion = start + burst;

            int tat = completion - arrival;
            int wt  = start - arrival;
            int rt  = wt;

            time = completion;

            totalWT  += wt;
            totalTAT += tat;
            totalRT  += rt;

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

        double cpuUtil = ((double) totalBusy / time) * 100.0;
        double throughput = (double) Main.numProcess / time;

        double avgWT  = totalWT  / Main.numProcess;
        double avgTAT = totalTAT / Main.numProcess;
        double avgRT  = totalRT  / Main.numProcess;

        System.out.println("\n===== FCFS Scheduling Results =====");
        System.out.println(Main.toStringAllProcesses());

        System.out.printf("Average Waiting Time: %.2f\n", avgWT);
        System.out.printf("Average Turnaround Time: %.2f\n", avgTAT);
        System.out.printf("Average Response Time: %.2f\n", avgRT);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtil);
        System.out.printf("Throughput: %.4f processes/unit time\n", throughput);

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
