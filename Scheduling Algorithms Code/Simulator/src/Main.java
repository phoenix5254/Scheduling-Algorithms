import java.util.InputMismatchException;

public class Main {
    static java.util.Scanner scanner = new java.util.Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Scheduler Simulation Started");
        mainPage();
        scanner.close();
    }

    static int[][] ProcessLink; // 2D array to store processes
    static final int attributes = 9; // Number of attributes in Process
    static int numProcess = -1;
    static int choice = 0; // to track which scheduler is selected
    static int timeQuantum = 2; // Default time quantum for MLQ (can be changed as needed)

    int getNumProcess() {
        return numProcess;
    }

    public static int[][] getProcessLink() {
        return ProcessLink;
    }

    static void NumOfProcess() {
        do {
            try {
                System.out.print("Enter the number of processes: ");
                numProcess = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (numProcess < 0) {
                    throw new MyException("Number of processes cannot be negative");
                }
                break;
            } catch (MyException me) {
                System.out.println(me.getMessage());
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Only integers are allowed. Please enter a number.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        } while (true);

        ProcessLink = new int[numProcess][attributes];
    }

    static boolean isPriority() {
        while (true) {
            try {
                System.out.print("Will the Process have a priority? (Y/N): ");
                String token = scanner.nextLine().trim();
                if (token.length() == 0)
                    throw new Exception();
                char c = token.charAt(0);
                c = Character.toUpperCase(c);
                if (c == 'Y')
                    return true;
                if (c == 'N')
                    return false;
                throw new Exception();
            } catch (Exception e) {
                System.out
                        .println("Invalid input. Only characters Y or N are allowed. Please enter a valid character.");
            }
        }
    }

    static void priority(int numProcessLocal, boolean usePriority) {
        int pID = 1;

        for (int x = 0; x < numProcessLocal; x++) {
            int highPri = 0;

            if (usePriority) {
                while (true) {
                    try {
                        System.out.println("NOTE: Processes with a larger value has a higher priority.");
                        System.out.print("Enter the priority for process " + pID + ": ");
                        highPri = scanner.nextInt();
                        scanner.nextLine();
                        if (highPri <= 0) {
                            throw new MyException("Priority cannot be negative or zero; must start from 1");
                        }
                        break;
                    } catch (MyException me) {
                        System.out.println(me.getMessage());
                        scanner.nextLine();
                    } catch (InputMismatchException ime) {
                        System.out.println("Invalid input. Please enter an integer for priority.");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.nextLine();
                    }
                }
            } else {
                highPri = 0;
            }

            // arrival and burst time loop
            while (true) {
                try {
                    System.out.print("Enter the arrival time for process " + pID + ": ");
                    int at = scanner.nextInt();
                    System.out.print("Enter the burst time for process " + pID + ": ");
                    int bt = scanner.nextInt();
                    scanner.nextLine();

                    if (at < 0 || bt < 0) {
                        throw new MyException("Arrival time and/or Burst time cannot be negative");
                    }

                    // Store process details
                    ProcessLink[x][Field.id.getValue()] = pID;
                    ProcessLink[x][Field.arrivalTime.getValue()] = at;
                    ProcessLink[x][Field.burstTime.getValue()] = bt;
                    ProcessLink[x][Field.priority.getValue()] = highPri;

                    // defensive/default initializations
                   ProcessLink[x][Field.waitingTime.getValue()] = 0;
                   ProcessLink[x][Field.turnAroundTime.getValue()] = 0;
                   ProcessLink[x][Field.startTime.getValue()] = 0;
                   ProcessLink[x][Field.responseTime.getValue()] = 0; 


                    pID++;
                    break;
                } catch (MyException me) {
                    System.out.println(me.getMessage());
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid input. Please enter integers for arrival and burst times.");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                }
            }
        }
    }

 /*  static int[][] sort() {
        if (ProcessLink == null || ProcessLink.length <= 1)
            return ProcessLink;
        int n = ProcessLink.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ProcessLink[j][Field.arrivalTime.getValue()] > ProcessLink[j + 1][Field.arrivalTime.getValue()]) {
                    int[] temp = ProcessLink[j];
                    ProcessLink[j] = ProcessLink[j + 1];
                    ProcessLink[j + 1] = temp;
                }
            }
        }
        return ProcessLink;
    }
    */

    static String toStringAllProcesses(float[] avg) {
        StringBuilder sb = new StringBuilder();
        sb.append("Process:\n");
        if (ProcessLink == null || ProcessLink.length == 0) {
            sb.append("No processes.\n");
            return sb.toString();
        }
        for (int p = 0; p < ProcessLink.length; p++) {
            sb.append("\nProcess ID:"+ProcessLink[p][Field.id.getValue()]);
            sb.append(" | Arrival Time: " + ProcessLink[p][Field.arrivalTime.getValue()]);
            sb.append(" | Burst Time: " + ProcessLink[p][Field.burstTime.getValue()]);
            sb.append(" | Priority: " + ProcessLink[p][Field.priority.getValue()]);
            sb.append(" | Waiting Time: " + ProcessLink[p][Field.waitingTime.getValue()]);
            sb.append(" | Turn Around Time: " + ProcessLink[p][Field.turnAroundTime.getValue()]);
            sb.append(" | Start Time: " + ProcessLink[p][Field.startTime.getValue()]);
            sb.append(" | Response Time: " + ProcessLink[p][Field.responseTime.getValue()]);
            sb.append("\n");
        }
        if (avg != null && avg.length >= 3) {
            sb.append(String.format("\nThe average waiting time is: %.2f%n", avg[0]));
            sb.append(String.format("The average turn around time is: %.2f%n", avg[1]));
            sb.append(String.format("The average response time is: %.2f%n", avg[2]));
            sb.append(String.format("CPU Utilization: %.2f%n", avg[3]));
            sb.append(String.format("Throughput: %.4f%n", avg[4]));
        }
        return sb.toString();
    }

    static String toStringAllProcesses() {
        return toStringAllProcesses(null);
    }

    public static float[] findAllAverages() {
        if (numProcess <= 0 || ProcessLink == null || ProcessLink.length == 0) {
            return new float[] { 0f, 0f, 0f, 0f, 0f };
        }
        float avgWT = 0f, avgTT = 0f, avgRT = 0f;
        for (int i = 0; i < numProcess; i++) {
            avgWT += ProcessLink[i][Field.waitingTime.getValue()];
            avgTT += ProcessLink[i][Field.turnAroundTime.getValue()];
            avgRT += ProcessLink[i][Field.responseTime.getValue()];
        }
        avgWT /= numProcess;
        avgTT /= numProcess;
        avgRT /= numProcess;
         // Round to 2 decimal places
        avgWT = Math.round(avgWT * 100f) / 100f;
        avgTT = Math.round(avgTT * 100f) / 100f;
        avgRT = Math.round(avgRT * 100f) / 100f;
        float[] result= find_Util_Throughput();
        return new float[] { avgWT, avgTT, avgRT, result[0], result[1] };
    }
   public static float[] find_Util_Throughput() {

    float totalBurst = 0;
    float totalFinishTime = 0;

    for (int i = 0; i < numProcess; i++) {
        totalBurst += ProcessLink[i][Field.burstTime.getValue()];
        totalFinishTime = Math.max(totalFinishTime, ProcessLink[i][Field.turnAroundTime.getValue()]);
    }

    float cpuUtil = (totalBurst / totalFinishTime) * 100f;
    float throughput = numProcess / totalFinishTime;

    cpuUtil = Math.round(cpuUtil * 100f) / 100f;
    throughput = Math.round(throughput * 100f) / 100f;

    return new float[]{cpuUtil, throughput};
}


   
    static void selectScheduler() {
        if (numProcess <= 0) {
            System.out.println("No processes to schedule. Exiting scheduler selection.");
            return;
        }

        do {
            try {
                System.out.println("\nSelect a Scheduling Algorithm:");
                System.out.println("1. First-Come, First-Served (FCFS)");
                System.out.println("2. Shortest Job First (SJF)");
                System.out.println("3. Priority Scheduling");
                System.out.println("4. MLQ");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: // FCFS
                        FCFS fcfs = new FCFS();
                        fcfs.fcfsScheduling();
                        System.out.println("\n--- FCFS Results ---");
                        System.out.println(toStringAllProcesses(findAllAverages()));
                        break;
                    case 2: // SJF
                        System.out.println("\n--- SJF Results ---");
                        SJF sjf = new SJF(ProcessLink);
                        sjf.sjfScheduling();
                        System.out.println(toStringAllProcesses(findAllAverages()));
                        break;
                    case 3: // Priority Scheduling
                        System.out.println("\n--- Priority Scheduling Results ---");
                        PriorityScheduling.runPriorityScheduling();
                        System.out.println(toStringAllProcesses(findAllAverages()));
                        break;
                    case 4: // MQ
                        System.out.println("\n--- MLQ Results ---");
                        Queue<Process> mlqProcessQueue = new Queue<>();
                        for (int i = 0; i < numProcess; i++) {
                            Process proc = new Process();
                            proc.setId(ProcessLink[i][Field.id.getValue()]);
                            proc.setArrivalTime(ProcessLink[i][Field.arrivalTime.getValue()]);
                            proc.setBurstTime(ProcessLink[i][Field.burstTime.getValue()]);
                            proc.setPriority(ProcessLink[i][Field.priority.getValue()]);
                            mlqProcessQueue.Enqueue(proc);
                        }
                        MLQ.mlqScheduling(mlqProcessQueue, timeQuantum);
                        System.out.println(toStringAllProcesses(findAllAverages()));
                        break;
                    case 5:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Only integers are allowed. Please enter a number.");
                scanner.nextLine();
            }
        } while (true);
    }

    static void mainPage() {
        NumOfProcess();
        if (numProcess > 0) {
            priority(numProcess, isPriority());
            System.out.println("\nInitial Input:");
            System.out.println(toStringAllProcesses());
            selectScheduler();
        } else {
            System.out.println("No processes entered. Exiting.");
        }
        System.out.println("Simulation completed.");
    }
}