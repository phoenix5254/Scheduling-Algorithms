public class RR extends Process {

    public static void rrScheduling() {
        int time = 0;
        int timeQuantum = Main.timeQuantum; // Default 2, can be changed

        // Create a list of processes with remaining burst times
        java.util.List<Process> processList = new java.util.ArrayList<>();
        for (int i = 0; i < Main.numProcess; i++) {
            Process p = new Process(Main.ProcessLink[i]);
            processList.add(p);
        }

        Queue<Process> readyQueue = new Queue<>();
        Queue<Process> displayQueue = new Queue<>();
        java.util.Set<Integer> enqueued = new java.util.HashSet<>();
        int completed = 0;

        while (completed < processList.size()) {
            // Add arrived processes to ready queue
            for (Process p : processList) {
                if (p.getArrivalTime() <= time && p.getRemainingBurstTime() > 0 && !enqueued.contains(p.getId())) {
                    readyQueue.Enqueue(p);
                    enqueued.add(p.getId());
                }
            }

            if (readyQueue.isEmpty()) {
                time++;
                continue;
            }

            // Dequeue process from ready queue
            Process current = (Process) readyQueue.Dequeue();

            // Set start time if first time
            if (current.getStartTime() == null) {
                current.setStartTime(time);
                current.setResponseTime(time - current.getArrivalTime());
            }

            // Execute for time quantum or remaining burst, whichever is smaller
            int executeTime = Math.min(timeQuantum, current.getRemainingBurstTime());
            for (int i = 0; i < executeTime; i++) {
                displayQueue.Enqueue(current);
            }

            time += executeTime;
            current.setRemainingBurstTime(current.getRemainingBurstTime() - executeTime);

            // If process not finished, re-enqueue
            if (current.getRemainingBurstTime() > 0) {
                readyQueue.Enqueue(current);
            } else {
                // Process completed
                completed++;
                int completionTime = time;
                current.setTurnAroundTime(completionTime - current.getArrivalTime());
                current.setWaitingTime(current.getTurnAroundTime() - current.getBurstTime());
                // Update Main.ProcessLink
                for (int i = 0; i < Main.numProcess; i++) {
                    if (Main.ProcessLink[i][Field.id.getValue()] == current.getId()) {
                        Main.ProcessLink[i][Field.startTime.getValue()] = current.getStartTime();
                        Main.ProcessLink[i][Field.waitingTime.getValue()] = current.getWaitingTime();
                        Main.ProcessLink[i][Field.responseTime.getValue()] = current.getResponseTime();
                        Main.ProcessLink[i][Field.turnAroundTime.getValue()] = current.getTurnAroundTime();
                        break;
                    }
                }
            }
        }

        displayQueue.displayGanttFromQueue(1, time);
    }

}
// Main
/*

  case 4: // MLQ
                        System.out.println("\n--- MLQ Results ---");
                        MLQ mlq = new MLQ();
                        mlq.mlqScheduling();
                        System.out.println(toStringAllProcesses(findAllAverages()));
                        break;
*/
