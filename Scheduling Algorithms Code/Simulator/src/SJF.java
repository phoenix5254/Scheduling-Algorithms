
public class SJF extends Process {
   private int[][] sjf;
   private int id;

   Queue<Process> queue = new Queue<Process>();

   public SJF() {
      super();
   }

   public SJF(int at, int bt) {
      super(at, bt);
      sjf = Main.ProcessLink; // Copying Main.ProcessLink to sjf array
   }

   public SJF(int at, int bt, int id) {
      super(at, bt);
      this.id = id;
      sjf = Main.ProcessLink; // Copying Main.ProcessLink to sjf array
   }

   public SJF(SJF obj) {
      super(obj);
   }

   public SJF(int[][] ProcessLink) {
      super(Main.ProcessLink);
      sjf = Main.ProcessLink;
   }

   // inside SJF class
   @Override
   public int getId() {
      // if this SJF object's id field was set via constructor, return it; otherwise
      // fallback to super
      if (this.id != 0)
         return this.id;
      return super.getId();
   }

   public void sjfScheduling() {
      // Build a mutable list of processes from Main.ProcessLink
      java.util.List<int[]> procList = new java.util.ArrayList<>();
      for (int i = 0; i < Main.numProcess; i++) {
         int at = Main.ProcessLink[i][Field.arrivalTime.getValue()];
         int bt = Main.ProcessLink[i][Field.burstTime.getValue()];
         int pid = Main.ProcessLink[i][Field.id.getValue()];
         procList.add(new int[] { pid, at, bt }); 
      }

      int remaining = procList.size();
      int time = 0;

      // Use your Queue<Process> for display/history
      Queue<Process> displayQueue = new Queue<>();

      while (remaining > 0) {
         // collect ready processes (arrived <= time)
         java.util.List<int[]> ready = new java.util.ArrayList<>();
         for (int[] row : procList) {
            if (row != null && row[1] <= time) {
               ready.add(row);
            }
         }

         if (ready.isEmpty()) {
            // advance time to next arrival 
            int nextArrival = Integer.MAX_VALUE;
            for (int[] row : procList) {
               if (row != null && row[1] < nextArrival) {
                  nextArrival = row[1];
               }
            }
            if (nextArrival == Integer.MAX_VALUE) {
               // no more processes (shouldn't happen because remaining>0), but safe-guard
               break;
            }
            time = Math.max(time + 1, nextArrival); // move to next arrival
            continue;
         }

         // Choose shortest job (min burst). Tie-breaker: earlier arrival, then lower
         // pid.
         int[] chosen = ready.get(0);
         for (int[] current : ready) {
            if (current[2] < chosen[2] ||
                  (current[2] == chosen[2] && current[1] < chosen[1]) ||
                  (current[2] == chosen[2] && current[1] == chosen[1] && current[0] < chosen[0])) {
               chosen = current;
            }
         }

         // Create an SJF process object using chosen values.
         // Note: this constructor sets this.id (SJF.id) so getId() override (below) will
         // return it.
         SJF proc = new SJF(chosen[1], chosen[2], chosen[0]);
         proc.setStartTime(time);

         // enqueue for display (do NOT immediately Dequeue - we want history)
         displayQueue.Enqueue(proc);

         // run process (non-preemptive SJF)
         time += proc.getBurstTime();
         int completion = time;
         int tat = completion - proc.getArrivalTime();
         int wt = proc.getStartTime() - proc.getArrivalTime(); // waiting before start
         int rt = proc.getStartTime() - proc.getArrivalTime(); // for non-preemptive, response == wait before start

         proc.setTurnAroundTime(tat);
         proc.setWaitingTime(wt);
         proc.setResponseTime(rt);

         // update Main.ProcessLink using your utility method
         addToProcessList(proc);

         // remove chosen from procList (mark null) and decrement remaining
         for (int i = 0; i < procList.size(); i++) {
            int[] r = procList.get(i);
            if (r != null && r[0] == chosen[0]) {
               procList.set(i, null);
               remaining--;
               break;
            }
         }
      }

      // Print the queued processes (displayQueue uses your Queue implementation)
      if (displayQueue.getFront() != null) {
         // iterate nodes via getFront() and next-node chain
         Node<Process> cur = displayQueue.getFront();
         while (cur != null) {
            Process p = cur.getData();
            // Use your Process.toStringAllProcesses() or custom printing for each proc:
            // I assume you have an instance method or use Main.ProcessLink for full table.
            // We'll print the per-process stats we've computed:
            System.out.println("Process ID: " + p.getId()
                  + " | Arrival: " + p.getArrivalTime()
                  + " | Burst: " + p.getBurstTime()
                  + " | Start: " + p.getStartTime()
                  + " | Waiting: " + p.getWaitingTime()
                  + " | Turnaround: " + p.getTurnAroundTime()
                  + " | Response: " + p.getResponseTime());
            cur = cur.getNextNode();
         }
      } else {
         System.out.println("There are no processes in the queue.");
      }
   }

   public void addToProcessList(SJF process) {
      for (int i = 0; i < Main.numProcess; i++) {
         if (Main.ProcessLink[i][Field.id.getValue()] == process.getId()) {
            Main.ProcessLink[i][Field.turnAroundTime.getValue()] = process.getTurnAroundTime();
            Main.ProcessLink[i][Field.waitingTime.getValue()] = process.getWaitingTime();
            Main.ProcessLink[i][Field.responseTime.getValue()] = process.getResponseTime();
         }
      }
   }
}
