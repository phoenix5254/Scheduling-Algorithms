
public class MLQ extends Process
{
    public void mlqScheduling() {
        // Backup original ProcessLink
        int[][] originalProcessLink = new int[Main.numProcess][Main.attributes];
        for (int i = 0; i < Main.numProcess; i++) {
            originalProcessLink[i] = Main.ProcessLink[i].clone();
        }

        // Check if user wanted priorities
        if (!Main.usePriorityFlag) {
            // If no priorities were used, run FCFS on all processes
            FCFS fcfs = new FCFS();
            fcfs.fcfsScheduling();
            return;
        }

        // Traditional MLQ behavior: Divide processes into high priority (priority > 0) and low priority (priority == 0)
        java.util.List<int[]> highPriProcesses = new java.util.ArrayList<>();
        java.util.List<int[]> lowPriProcesses = new java.util.ArrayList<>();
        for (int i = 0; i < Main.numProcess; i++) {
            if (Main.ProcessLink[i][Field.priority.getValue()] > 0) {
                highPriProcesses.add(Main.ProcessLink[i].clone());
            } else {
                lowPriProcesses.add(Main.ProcessLink[i].clone());
            }
        }

        // Create combined display queue
        Queue<Process> combinedDisplayQueue = new Queue<>();

        // Run RR on high priority processes
        if (!highPriProcesses.isEmpty()) {
            Main.numProcess = highPriProcesses.size();
            Main.ProcessLink = new int[Main.numProcess][Main.attributes];
            for (int i = 0; i < highPriProcesses.size(); i++) {
                Main.ProcessLink[i] = highPriProcesses.get(i);
            }
            RR.rrScheduling();
            // Update highPriProcesses with results
            for (int i = 0; i < highPriProcesses.size(); i++) {
                highPriProcesses.set(i, Main.ProcessLink[i].clone());
            }
        }

        // Run FCFS on low priority processes
        if (!lowPriProcesses.isEmpty()) {
            Main.numProcess = lowPriProcesses.size();
            Main.ProcessLink = new int[Main.numProcess][Main.attributes];
            for (int i = 0; i < lowPriProcesses.size(); i++) {
                Main.ProcessLink[i] = lowPriProcesses.get(i);
            }
            FCFS fcfs = new FCFS();
            fcfs.fcfsScheduling();
            // Update lowPriProcesses with results
            for (int i = 0; i < lowPriProcesses.size(); i++) {
                lowPriProcesses.set(i, Main.ProcessLink[i].clone());
            }
        }


        // Restore original ProcessLink size and merge results
        Main.numProcess = originalProcessLink.length;
        Main.ProcessLink = new int[Main.numProcess][Main.attributes];
        
        // First, copy all original process data
        for (int i = 0; i < Main.numProcess; i++) {
            Main.ProcessLink[i] = originalProcessLink[i].clone();
        }
        
        // Then update with calculated values from high priority processes (RR results)
        for (int[] updatedProcess : highPriProcesses) {
            int processId = updatedProcess[Field.id.getValue()];
            for (int i = 0; i < Main.numProcess; i++) {
                if (Main.ProcessLink[i][Field.id.getValue()] == processId) {
                    Main.ProcessLink[i] = updatedProcess.clone();
                    break;
                }
            }
        }
        
        // Then update with calculated values from low priority processes (FCFS results)
        for (int[] updatedProcess : lowPriProcesses) {
            int processId = updatedProcess[Field.id.getValue()];
            for (int i = 0; i < Main.numProcess; i++) {
                if (Main.ProcessLink[i][Field.id.getValue()] == processId) {
                    Main.ProcessLink[i] = updatedProcess.clone();
                    break;
                }
            }
        }




        // For Gantt, since individual schedulers have their own, we might need to combine queues
        // For now, display a simple message or combine manually
        //System.out.println("MLQ Scheduling completed. Gantt chart display needs manual combination.");
    }
}
