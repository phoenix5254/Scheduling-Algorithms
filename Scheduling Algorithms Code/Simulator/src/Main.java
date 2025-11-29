
import java.util.InputMismatchException;

public class Main {
    static java.util.Scanner scanner = new java.util.Scanner(System.in);
    public static void main(String[] args) throws Exception {
        System.out.println("Scheduler Simulation Started");
        mainPage();
       
    }
    static int[][] ProcessLink;// 2D array to store processes
    static final int attributes = 9; // Number of attributes in Process
    static int numProcess = -1;
    static int choice=0; // to track which scheduler is selected

    int getNumProcess() {return numProcess;}
    public static int [][] getProcessLink(){ return ProcessLink; }

    static void NumOfProcess() {
        do {
            try {
                System.out.println("Enter the number of processes:");
                numProcess = scanner.nextInt();
                // Throws exception if negative number entered
                if (numProcess < 0) {
                    throw new MyException("Number of processes cannot be negative");
                }
            } catch (MyException me){
                System.out.println(me.getMessage());
            }catch (InputMismatchException e) {
                System.out.println("Invalid input. Only integers are allowed. Please enter a number.");
                scanner.next();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.next();
            }
        } while (numProcess < 0);
        ProcessLink = new int[numProcess][attributes]; // Initialize ProcessLink array to hold process data
    }

    static boolean isPriority() {
        boolean isvalid = false;
        do {
            try {
                System.out.println("Will the Process have a priority? (Y/N)");
                char priority = scanner.next().charAt(0);
                // Throws exception if invalid input entered
                if (priority == 'Y' || priority == 'y') {
                    isvalid = true;
                    break;
                } else if (priority == 'N' || priority == 'n') {
                    break;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Only characters Y or N are allowed. Please enter a valid character.");
                scanner.next();
            }
        } while (!isvalid);
        return isvalid;
    }

    static void priority(int numProcess, boolean isPriority) {// Get process details from user
        int highPri = 0;
        int pID=1;
        for (int x = 0; x < numProcess; x++) {
            if (isPriority == true) {
                try {// try catch block for priority input
                    do {
                        System.out.println("NOTE:\n Processes with a lower value have a higher priority.");
                        System.out.println("Enter the priority for process "+ (pID) + ":");
                        highPri = scanner.nextInt();
                        if (highPri <= 0) {
                            throw new MyException("Priority cannot be negative and must start from 1");
                        }
                        ;
                    } while (highPri <= 0);
                } catch(MyException me){
                    System.out.println(me.getMessage());
                }catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next();
                }
            }
            boolean isValid = false;
            do {
                    try {
                    // if there is no priority, set highPri to 0 for all non priority processes
                    System.out.println("Enter the arrival time for process " + (pID) + ":");
                    int at = scanner.nextInt();
                    System.out.println("Enter the burst time for process " + (pID) + ":");
                    int bt = scanner.nextInt();
                    if (at < 0 || bt < 0) {
                        pID--;
                        throw new MyException("Arrival time and/or Burst time cannot be negative");
                    }
                    // Store process details in ProcessLink array
                    ProcessLink[x][Field.id.getValue()] = pID;
                    ProcessLink[x][Field.arrivalTime.getValue()] = at;
                    ProcessLink[x][Field.burstTime.getValue()] = bt;
                    ProcessLink[x][Field.priority.getValue()] = highPri;
                    isValid = true; 
                    pID++;
                } catch (MyException me) { System.out.println(me.getMessage()); scanner.next(); 
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }while(isValid!=true); // continue loop until valid input is entered
        }

    }

    static int[][] sort() {// Sort based on Arrival Time to enqueue (bubble sort)
        int n = ProcessLink.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ProcessLink[j][Field.arrivalTime.getValue()] > ProcessLink[j + 1][Field.arrivalTime
                        .getValue()]) {
                    // swap ProcessLink[j] and ProcessLink[j+1]
                    int[] temp = ProcessLink[j];
                    ProcessLink[j] = ProcessLink[j + 1];
                    ProcessLink[j + 1] = temp;
                }
            }
        }
        return ProcessLink;
    }
    static String toStringAllProcesses() {
        StringBuilder sb = new StringBuilder();
        sb.append("Process:\n");
        if (ProcessLink == null) {
            sb.append("No processes.\n");
            return sb.toString();
        }
        for (int p = 0; p < ProcessLink.length; p++) {
            sb.append(ProcessLink[p][Field.id.getValue()] + ") ");
            sb.append("Arrival Time: " + ProcessLink[p][Field.arrivalTime.getValue()] + ", ");
            sb.append("Burst Time: " + ProcessLink[p][Field.burstTime.getValue()] + ", ");
            sb.append("Priority: " + ProcessLink[p][Field.priority.getValue()] + ", ");
            sb.append("Waiting Time: " + ProcessLink[p][Field.waitingTime.getValue()] + ", ");
            sb.append("Turn Around Time: " + ProcessLink[p][Field.turnAroundTime.getValue()] + ", ");
            sb.append("Start Time: " + ProcessLink[p][Field.startTime.getValue()] + ", ");
            sb.append("Response Time: " + ProcessLink[p][Field.responseTime.getValue()] + ",");
            sb.append("\n");
        }
        return sb.toString();
    }
    static void selectScheduler(){       
        do {
            try {
                System.out.println("Select a Scheduling Algorithm:");
                System.out.println("1. First-Come, First-Served (FCFS)");
                System.out.println("2. Shortest Job First (SJF)");
                System.out.println("3. Priority Scheduling");
                System.out.println("4. Round Robin (RR)");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1: // FCFS
                        
                        break;
                    case 2:// SJF
                        SJF sjf= new SJF(ProcessLink);
                        sjf.sjfScheduling();
                        break;
                    case 3:// Priority Scheduling
                        
                        break;
                    case 4: // Round Robin
                        
                        break;
                    default: // Invalid choice
                        System.out.println("Invalid choice. Please select a valid option between 0 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Only integers are allowed. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        } while (choice < 1 || choice > 4);
        System.out.println("Simulation completed.");
    }
    static void mainPage(){
        NumOfProcess();
        priority(numProcess,isPriority());
        ProcessLink=sort();
        System.out.println(toStringAllProcesses());
        selectScheduler();


    }

}