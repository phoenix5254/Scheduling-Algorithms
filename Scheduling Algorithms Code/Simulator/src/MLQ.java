import java.util.HashMap;
import java.util.Map;

public class MLQ {
    // Three queues for the three levels
    private Queue<Process> highPriorityQueue;
    private Queue<Process> mediumPriorityQueue;
    private Queue<Process> lowPriorityQueue;

    // Time quantum for Round Robin in the medium priority queue
    private int timeQuantum;

    // To store original burst times for calculations
    private Map<Integer, Integer> originalBurstTimes;

    public MLQ(int timeQuantum) {
        this.highPriorityQueue = new Queue<>();
        this.mediumPriorityQueue = new Queue<>();
        this.lowPriorityQueue = new Queue<>();
        this.timeQuantum = timeQuantum;
        this.originalBurstTimes = new HashMap<>();
    }

    public void addProcess(Process process) {
        if (process == null) {
            return;
        }
        originalBurstTimes.put(process.getId(), process.getBurstTime());
        switch (process.getPriority()) {
            case 1:
                highPriorityQueue.Enqueue(process);
                break;
            case 2:
                mediumPriorityQueue.Enqueue(process);
                break;
            case 3:
                lowPriorityQueue.Enqueue(process);
                break;
            default:
                System.out.println("Invalid priority for process " + process.getId());
                break;
        }
    }

    public void schedule() {
        int currentTime = 0;

        while (!highPriorityQueue.isEmpty() || !mediumPriorityQueue.isEmpty() || !lowPriorityQueue.isEmpty()) {
            // Process high priority queue (FCFS)
            if (!highPriorityQueue.isEmpty()) {
                Process p = highPriorityQueue.Dequeue();
                if (p == null) continue;

                if (p.getStartTime() == null) {
                    p.setStartTime(currentTime);
                    p.setResponseTime(p.getStartTime() - p.getArrivalTime());
                }

                System.out.println("Executing process " + p.getId() + " from high priority queue.");
                currentTime += p.getBurstTime();
                
                p.setTurnAroundTime(currentTime - p.getArrivalTime());
                p.setWaitingTime(p.getTurnAroundTime() - originalBurstTimes.get(p.getId()));
                p.addToProcessList(p);
            }
            // Process medium priority queue (Round Robin)
            else if (!mediumPriorityQueue.isEmpty()) {
                Process p = mediumPriorityQueue.Dequeue();
                if (p == null) continue;

                if (p.getStartTime() == null) {
                    p.setStartTime(currentTime);
                    p.setResponseTime(p.getStartTime() - p.getArrivalTime());
                }

                System.out.println("Executing process " + p.getId() + " from medium priority queue.");
                if (p.getBurstTime() > timeQuantum) {
                    currentTime += timeQuantum;
                    p.setBurstTime(p.getBurstTime() - timeQuantum);
                    mediumPriorityQueue.Enqueue(p); // Re-queue the process
                } else {
                    currentTime += p.getBurstTime();
                    p.setBurstTime(0);
                    p.setTurnAroundTime(currentTime - p.getArrivalTime());
                    p.setWaitingTime(p.getTurnAroundTime() - originalBurstTimes.get(p.getId()));
                    p.addToProcessList(p);
                }
            }
            // Process low priority queue (FCFS)
            else if (!lowPriorityQueue.isEmpty()) {
                Process p = lowPriorityQueue.Dequeue();
                if (p == null) continue;

                if (p.getStartTime() == null) {
                    p.setStartTime(currentTime);
                    p.setResponseTime(p.getStartTime() - p.getArrivalTime());
                }
                
                System.out.println("Executing process " + p.getId() + " from low priority queue.");
                currentTime += p.getBurstTime();

                p.setTurnAroundTime(currentTime - p.getArrivalTime());
                p.setWaitingTime(p.getTurnAroundTime() - originalBurstTimes.get(p.getId()));
                p.addToProcessList(p);
            }
        }
    }
}


/*
CASE
     System.out.println("\n--- MLQ Results ---");
                        MLQ mlq = new MLQ(2); // Time quantum for medium priority queue is 2
                        for (int[] processData : ProcessLink) {
                            Process p = new Process(processData);
                            mlq.addProcess(p);
*/