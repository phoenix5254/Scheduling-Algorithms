public class MLQ extends Process
{
    public static void mlqScheduling(Queue<Process> processes, int timeQuantum) {
        Queue<Process> rrQueue = new Queue<>();    // High priority queue (Round Robin)
        Queue<Process> fcfsQueue = new Queue<>();  // Low priority queue (FCFS)

        int totalProcesses = processes.getCount();
        int completedProcesses = 0;
        int currentTime = 0;

        while (completedProcesses < totalProcesses) {
            // Move processes from the initial queue to the ready queues if they have arrived
            while (!processes.isEmpty() && processes.peek().getArrivalTime() <= currentTime) {
                Process p = processes.Dequeue();
                if (p.getPriority() == 1) { // Priority 1 for Round Robin
                    rrQueue.Enqueue(p);
                } else { // Other priorities for FCFS
                    fcfsQueue.Enqueue(p);
                }
            }

            if (!rrQueue.isEmpty()) {
                Process p = rrQueue.Dequeue();

                if (p.getStartTime() == null) {
                    p.setStartTime(currentTime);
                    p.setResponseTime(p.getStartTime() - p.getArrivalTime());
                }

                if (p.getRemainingBurstTime() <= timeQuantum) {
                    // Process will finish in this time slice
                    currentTime += p.getRemainingBurstTime();
                    p.setRemainingBurstTime(0);
                    p.setTurnAroundTime(currentTime - p.getArrivalTime());
                    p.setWaitingTime(p.getTurnAroundTime() - p.getBurstTime());
                    p.addToProcessList(p); // Update process details
                    completedProcesses++;
                    System.out.println("Process ID: " + p.getId() + 
                 " | Arrival: " + p.getArrivalTime() + 
                 " | Burst: " + p.getBurstTime() + 
                 " | Priority: " + p.getPriority() + 
                 " | Start: " + p.getStartTime() + 
                 " | Waiting: " + p.getWaitingTime() + 
                 " | Turnaround: " + p.getTurnAroundTime() + 
                 " | Response: " + p.getResponseTime());
                } else {
                    // Process will not finish, run for a full time quantum
                    currentTime += timeQuantum;
                    p.setRemainingBurstTime(p.getRemainingBurstTime() - timeQuantum);

                    // Before re-queueing, check for new arrivals that might have come during the quantum
                    while (!processes.isEmpty() && processes.peek().getArrivalTime() <= currentTime) {
                        Process newP = processes.Dequeue();
                        if (newP.getPriority() == 1) {
                            rrQueue.Enqueue(newP);
                        } else {
                            fcfsQueue.Enqueue(newP);
                        }
                    }

                    rrQueue.Enqueue(p); // Add the process back to the queue
                }
            } else if (!fcfsQueue.isEmpty()) {
                Process p = fcfsQueue.Dequeue();

                if (currentTime < p.getArrivalTime()) {
                    currentTime = p.getArrivalTime();
                }

                p.setStartTime(currentTime);
                p.setResponseTime(p.getStartTime() - p.getArrivalTime());
                currentTime += p.getBurstTime();
                p.setRemainingBurstTime(0);

                p.setTurnAroundTime(currentTime - p.getArrivalTime());
                p.setWaitingTime(p.getTurnAroundTime() - p.getBurstTime());
                p.addToProcessList(p); // Update process details
                completedProcesses++;
                System.out.println("Process ID: " + p.getId() + 
                 " | Arrival: " + p.getArrivalTime() + 
                 " | Burst: " + p.getBurstTime() + 
                 " | Priority: " + p.getPriority() + 
                 " | Start: " + p.getStartTime() + 
                 " | Waiting: " + p.getWaitingTime() + 
                 " | Turnaround: " + p.getTurnAroundTime() + 
                 " | Response: " + p.getResponseTime());
            } else {
                // If no process is in the ready queues, advance time to the next arrival
                if (!processes.isEmpty()) {
                    currentTime = processes.peek().getArrivalTime();
                } else {
                    // All processes are done, exit loop
                    break;
                }
            }
        }

        // Update Main.ProcessLink
        for (int i = 0; i < Main.numProcess; i++) {
            Main.ProcessLink[i] = Main.getProcessLink()[i];
        }
    }


}