public class PriorityScheduling extends Process {

    public PriorityScheduling() { }

    // --------------------------
    // Sort by priority DESC
    // If same priority → arrival ASC
    // --------------------------
    public static int[][] sortForPriority() {
        int n = Main.ProcessLink.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {

                int[] a = Main.ProcessLink[j];
                int[] b = Main.ProcessLink[j + 1];

                int pa = a[Field.priority.getValue()];
                int pb = b[Field.priority.getValue()];

                int aa = a[Field.arrivalTime.getValue()];
                int ab = b[Field.arrivalTime.getValue()];

                // Sort rule:
                // 1. Higher priority FIRST
                // 2. If same priority → earlier arrival FIRST
                if (pa < pb || (pa == pb && aa > ab)) {
                    Main.ProcessLink[j] = b;
                    Main.ProcessLink[j + 1] = a;
                }
            }
        }
        return Main.ProcessLink;
    }

    // --------------------------
    // Non-preemptive Priority Scheduling
    // --------------------------
    public static void runPriorityScheduling() {

        // Step 1: Sort
        sortForPriority();

        Process[] proc = new Process[Main.numProcess];
        for (int i = 0; i < Main.numProcess; i++) {
            proc[i] = new Process(Main.ProcessLink[i]);
        }

        int time = 0;

        for (Process p : proc) {

            if (time < p.getArrivalTime()) {
                time = p.getArrivalTime();
            }

            p.setStartTime(time);

            p.setWaitingTime(time - p.getArrivalTime());

            p.setResponseTime(p.getStartTime() - p.getArrivalTime());

            p.setTurnAroundTime(p.getWaitingTime() + p.getBurstTime());

            time += p.getBurstTime();

            p.addToProcessList(p);
        }

        System.out.println("\n--- Priority Scheduling Results ---");
        for (Process p : proc) {
            System.out.println(
                "Process ID: " + p.getId() +
                " | Arrival: " + p.getArrivalTime() +
                " | Burst: " + p.getBurstTime() +
                " | Priority: " + p.getPriority() +
                " | Start: " + p.getStartTime() +
                " | Waiting: " + p.getWaitingTime() +
                " | Turnaround: " + p.getTurnAroundTime() +
                " | Response: " + p.getResponseTime()
            );
        }
    }
}
