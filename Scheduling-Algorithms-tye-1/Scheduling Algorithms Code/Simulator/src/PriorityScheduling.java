//PreemptivePriority.java
//Implementation of the Pre-Emptive Priority Scheduling Algorithm
//Lower priority number = higher schdeuling priority

public class PriorityScheduling extends Process {
    private int[][] procArr;


public void PriorityScheduler() {
    // Builds a list of processes
    java.util.List<int[]> procList = new java.util.ArrayList<>();
    for (int i = 0; i < Main.numProcess; i++) {
        int pid = Main.ProcessLink[i][Field.id.getValue()];
        int at = Main.ProcessLink[i][Field.arrivalTime.getValue()];
        int bt = Main.ProcessLink[i][Field.burstTime.getValue()];
        int pri = Main.ProcessLink[i][Field.priority.getValue()];
        procList.add(new int[] { pid, at, bt, pri, bt }); // last element = remaining time
    }

    int time = 0;
    int remaining = procList.size(); // number of unfinished processes
    Queue<Process> displayQueue = new Queue<>();
    java.util.Set<Integer> started = new java.util.HashSet<>(); // track pids that already started

    // helper to find Main.ProcessLink row index by pid
    java.util.function.IntUnaryOperator findIndex = (pid) -> {
        for (int i = 0; i < Main.numProcess; i++) {
            if (Main.ProcessLink[i][Field.id.getValue()] == pid) return i;
        }
        return -1;
    };

    // Loops until all processes are finished
    while (remaining > 0) {
        // Collecting the processes that have arrived
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
            if (cand[3] < chosen[3] ||
                    (cand[3] == chosen[3] && cand[1] < chosen[1]) ||
                    (cand[3] == chosen[3] && cand[1] == chosen[1] && cand[0] < chosen[0])) {
                chosen = cand;
            }
        }

        int pid = chosen[0];
        int mainIdx = findIndex.applyAsInt(pid);
        if (mainIdx == -1) {
            // defensive: skip if mapping broken
            time++;
            continue;
        }

        // first time process runs -> record response/start times (only once)
        if (!started.contains(pid)) {
            int response = time - chosen[1];
            Main.ProcessLink[mainIdx][Field.responseTime.getValue()] = response;
            Main.ProcessLink[mainIdx][Field.startTime.getValue()] = time;
            started.add(pid);
        }

        // Create process object representing the current execution unit and record Gantt
        Process procUnit = new Process(Main.ProcessLink[mainIdx]);
        displayQueue.Enqueue(procUnit);

        // Run for 1 time unit (preemptive)
        chosen[4]--; // Decrease the remaining time
        time++;

        // If process is finished after this unit:
        if (chosen[4] == 0) {
            int completion = time;
            int tat = completion - chosen[1];
            int wt = tat - chosen[2];
            int rt = Main.ProcessLink[mainIdx][Field.responseTime.getValue()];

            // Save metrics into process object (single final object)
            Process finalProc = new Process(Main.ProcessLink[mainIdx]);
            finalProc.setTurnAroundTime(tat);
            finalProc.setWaitingTime(wt);
            finalProc.setResponseTime(rt);

            // Update the Main.ProcessLink table for reporting (if needed)
            addToProcessList(finalProc);

            // Mark process as finished in procList
            for (int i = 0; i < procList.size(); i++) {
                int[] r = procList.get(i);
                if (r != null && r[0] == chosen[0]) {
                    procList.set(i, null);
                    remaining--;
                    break;
                }
            }
        }
    }

    System.out.println("\n===== Priority Timeline Events =====");
    displayQueue.displayGanttFromQueue(1, time);
    System.out.println("Details of Processes:");
}
}