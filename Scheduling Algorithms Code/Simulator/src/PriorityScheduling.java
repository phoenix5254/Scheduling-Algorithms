public class PriorityScheduling extends Process {
    int time = 0;

    public void PriorityScheduler() {
        // --- 1. Initialization and Setup ---

        // Use a list of Process objects to manage state (remaining burst time, start
        // time)
        java.util.List<Process> processList = new java.util.ArrayList<>();

        // Convert the static ProcessLink array into a list of mutable Process objects.
        for (int i = 0; i < Main.numProcess; i++) {
            // Use the constructor that loads data from the existing ProcessLink row
            Process p = new Process(Main.ProcessLink[i]);
            processList.add(p);
        }

        int completedCount = 0;
        Queue<Process> displayQueue = new Queue<>();

        // --- 2. Main Scheduling Loop ---

        while (completedCount < processList.size()) {

            // A. Find the Highest Priority Ready Process (Manual Selection)
            int minPriority = Integer.MAX_VALUE;
            Process selectedProcess = null;

            for (Process p : processList) {
                // Check if the process has arrived AND still has remaining burst time
                if (p.getBurstTime() > 0 && p.getArrivalTime() <= time) {
                    // Preemptive check: Smaller number is HIGHER priority
                    if (p.getPriority() < minPriority) {
                        minPriority = p.getPriority();
                        selectedProcess = p;
                        displayQueue.Enqueue(p);

                    }
                    // Optional: Tie-breaker (e.g., using FCFS for equal priority)
                    // If p.getPriority() == minPriority, do nothing, as the process
                    // that was found first (and thus arrived earlier or has a lower index) wins.
                }
            }

            // 1. Handle Idle Time and check if a process was selected
            if (selectedProcess == null) {
                // ... (Your existing idle time logic goes here) ...
                continue;
            }

            // 2. Set Start Time (First time the CPU runs it)
            if (selectedProcess.getStartTime() == 0) { // Assuming 0 indicates uninitialized
                selectedProcess.setStartTime(time);

                // Calculate Response Time
                selectedProcess.setResponseTime(selectedProcess.getStartTime() - selectedProcess.getArrivalTime());
                super.addToProcessList(selectedProcess); // Initial update
            }

            // 3. Enqueue the process for the current time unit (records Pn for time 'time')
            // We MUST enqueue a copy to prevent subsequent mutations (like BT=0) from
            // affecting history.
            // Assuming your Process class has a copy constructor:
            Process processUnitToDisplay = new Process(selectedProcess);
            displayQueue.Enqueue(processUnitToDisplay);

            // 4. Execute for 1 time unit and advance clock
            int remainingBurst = selectedProcess.getBurstTime() - 1;
            selectedProcess.setBurstTime(remainingBurst); // Update remaining burst time
            time++; // Advance clock by 1
            // D. Completion Check
            if (remainingBurst == 0) {
                completedCount++;

                int completionTime = time;

                // Get the original burst time from the static array for correct TAT/WT
                // calculation
                int originalBurstTime = Main.ProcessLink[selectedProcess.getId() - 1][Field.burstTime.getValue()];

                // Turnaround Time = Completion Time - Arrival Time
                int tat = completionTime - selectedProcess.getArrivalTime();
                selectedProcess.setTurnAroundTime(tat);

                // Waiting Time = Turnaround Time - Original Burst Time
                int wt = tat - originalBurstTime;
                selectedProcess.setWaitingTime(wt);

                // Final update the static ProcessLink array with completion metrics
                super.addToProcessList(selectedProcess);
            }

            // The loop repeats, and the selection logic (A) checks for preemption
            // at the new time unit.
        }

        // --- 3. Display Gantt Chart ---
        displayQueue.displayGanttFromQueue(1, time);
    }
}