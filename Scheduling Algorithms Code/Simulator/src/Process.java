
public abstract class Process {
    // Attributes
    protected static int id = 0;
    protected int arrivalTime;
    protected int burstTime;
    protected int priority;
    protected int waitingTime;
    protected int turnAroundTime;
    protected int responseTime;
    protected Integer startTime; // accepts null value for uninitialized start time

    
    static java.util.Scanner scanner = new java.util.Scanner(System.in);
    
    static int timeUnit = 0;


    // Constructors
    public Process() {
    }

    public Process(int arivalT, int burstT) {
        id++;// auto increment process id
        this.arrivalTime = arivalT;
        this.burstTime = burstT;
        this.priority = 0;
        setStartTime(null);
    }

    public Process(int arivalT, int burstT, int priority) {
        id++;// auto increment process id
        this.arrivalTime = arivalT;
        this.burstTime = burstT;
        this.priority = priority;
        setStartTime(null);
    }

    public Process(int[][] p) {
        id++;// auto increment process id
        this.arrivalTime = p[0][Field.arrivalTime.getValue()];
        this.burstTime = p[0][Field.burstTime.getValue()];
        this.priority = p[0][Field.priority.getValue()];
        setStartTime(null);
    }

    public Process(Process p) {
        this.arrivalTime = p.arrivalTime;
        this.burstTime = p.burstTime;
        this.priority = p.priority;
        this.waitingTime = p.waitingTime;
        this.turnAroundTime = p.turnAroundTime;
        this.responseTime = p.responseTime;
        this.startTime = p.startTime;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        Process.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }     

    // Other Methods
    public void ViewProcessByObject() {
        System.out.println("Process ID: " + id + " Arrival Time: " + arrivalTime + " Burst Time: " + burstTime+ " Priority: " + priority);
    }

    public void addToProcessList(<t> process) {
      for (int i = 0; i < Main.numProcess; i++) {
         if (Main.ProcessLink[i][Field.id.getValue()] == process.getId()) {
            Main.ProcessLink[i][Field.turnAroundTime.getValue()] = process.getTurnAroundTime();
            Main.ProcessLink[i][Field.waitingTime.getValue()] = process.getWaitingTime();
            Main.ProcessLink[i][Field.responseTime.getValue()] = process.getResponseTime();
         }
      }
   }
}
