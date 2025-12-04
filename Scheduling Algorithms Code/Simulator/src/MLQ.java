

public class MLQ extends Process
{
 // Three queues for the three levels
    private Queue<Process> highPriorityQueue;
    private Queue<Process> mediumPriorityQueue;
    private Queue<Process> lowPriorityQueue;

    // Time quantum for Round Robin in the medium priority queue
    private int timeQuantum;
    private int id;

    public MLQ(int timeQuantum) {
        this.highPriorityQueue = new Queue<>();
        this.mediumPriorityQueue = new Queue<>();
        this.lowPriorityQueue = new Queue<>();
        this.timeQuantum = timeQuantum;
    }

    public MLQ(int[][] processLink) {
        super(processLink);
    }

    @Override
    public int getId() {
        if (this.id != 0) {
            return this.id;
        }
        return super.getId();
    }

    public void addProcess(Process process) {
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
        while (!highPriorityQueue.isEmpty() || !mediumPriorityQueue.isEmpty() || !lowPriorityQueue.isEmpty()) {
            // Process high priority queue (FCFS)
            if (!highPriorityQueue.isEmpty()) {
                Process p = highPriorityQueue.Dequeue();
                if (p == null) continue;
                System.out.println("Executing process " + p.getId() + " from high priority queue.");
                // Simulate execution
                try {
                    Thread.sleep(p.getBurstTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Process medium priority queue (Round Robin)
            else if (!mediumPriorityQueue.isEmpty()) {
                Process p = mediumPriorityQueue.Dequeue();
                if (p == null) continue;
                System.out.println("Executing process " + p.getId() + " from medium priority queue.");
                if (p.getBurstTime() > timeQuantum) {
                    try {
                        Thread.sleep(timeQuantum);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    p.setBurstTime(p.getBurstTime() - timeQuantum);
                    mediumPriorityQueue.Enqueue(p); // Re-queue the process
                } else {
                    try {
                        Thread.sleep(p.getBurstTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Process low priority queue (FCFS)
            else if (!lowPriorityQueue.isEmpty()) {
                Process p = lowPriorityQueue.Dequeue();
                if (p == null) continue;
                System.out.println("Executing process " + p.getId() + " from low priority queue.");
                try {
                    Thread.sleep(p.getBurstTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    
}


                  /* case 4: //MLQ
                        MLQ mlq = new MLQ(10); // Time quantum of 10
                        for (int[] processData : ProcessLink) {
                            mlq.addProcess(new MLQ(new int[][]{processData}));
                        }
                        mlq.schedule();
                        
                        
                        break;
                    */