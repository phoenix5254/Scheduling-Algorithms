public class Process {
    // unique id generator (static), and per-instance id
    private static int nextId = 0;       // used only to generate new ids
    protected int id;                    // instance-level id

    protected int arrivalTime;
    protected int burstTime;
    protected int remainingBurstTime;  // I added this 
    protected int priority;
    protected int waitingTime;
    protected int turnAroundTime;
    protected int responseTime;
    protected Integer startTime; // accepts null value for uninitialized start time

    static java.util.Scanner scanner = new java.util.Scanner(System.in);
    static int timeUnit = 0;

    // Constructors

    // default: no new id created (rare)
    public Process() { }

    // create a new Process and auto-generate an id
    public Process(int arrivalT, int burstT) {
        this.id = ++nextId;
        this.arrivalTime = arrivalT;
        this.burstTime = burstT;
        this.remainingBurstTime = burstT;
        this.priority = 0;
        setStartTime(null);
    }

    // create a new Process and auto-generate an id with priority
    public Process(int arrivalT, int burstT, int priority) {
        this.id = ++nextId;
        this.arrivalTime = arrivalT;
        this.burstTime = burstT;
        this.remainingBurstTime = burstT;
        this.priority = priority;
        setStartTime(null);
    }

    // construct a Process from an existing ProcessLink row (no new id generated)
    // pRow is a single row from Main.ProcessLink: {id, arrival, burst, ...} or similar
    public Process(int[] pRow) {
        // assume pRow uses Field indices; caller should provide correct row
        this.id = pRow[Field.id.getValue()];
        this.arrivalTime = pRow[Field.arrivalTime.getValue()];
        this.burstTime = pRow[Field.burstTime.getValue()];
        this.remainingBurstTime = this.burstTime;
        this.priority = pRow[Field.priority.getValue()];
        this.waitingTime = pRow[Field.waitingTime.getValue()];
        this.turnAroundTime = pRow[Field.turnAroundTime.getValue()];
        this.responseTime = pRow[Field.responseTime.getValue()];
        this.startTime = pRow[Field.startTime.getValue()]; 
        // ensure nextId never regresses below existing ids
        if (this.id > nextId) nextId = this.id;
    }

    // construct a Process when you already know the intended id (useful for SJF)
    public Process(int arrivalT, int burstT, int givenId, boolean useGivenId) {
        if (useGivenId) {
            this.id = givenId;
            if (this.id > nextId) nextId = this.id;
        } else {
            this.id = ++nextId;
        }
        this.arrivalTime = arrivalT;
        this.burstTime = burstT;
        this.remainingBurstTime = burstT;
        this.priority = 0;
        setStartTime(null);
    }

    // copy constructor (keeps same id)
    public Process(Process p) {
        this.id = p.id;
        this.arrivalTime = p.arrivalTime;
        this.burstTime = p.burstTime;
        this.remainingBurstTime = p.remainingBurstTime;
        this.priority = p.priority;
        this.waitingTime = p.waitingTime;
        this.turnAroundTime = p.turnAroundTime;
        this.responseTime = p.responseTime;
        this.startTime = p.startTime;
    }

    // Getters and Setters (id is instance-level)
    public int getId() { return id; }

    // You rarely need setId, but make it available
    public void setId(int id) {
        this.id = id;
        if (id > nextId) nextId = id;
    }

    public int getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(int arrivalTime) { this.arrivalTime = arrivalTime; }

    public int getBurstTime() { return burstTime; }
    public void setBurstTime(int burstTime) { this.burstTime = burstTime; }

    public int getRemainingBurstTime() { return remainingBurstTime; }
    public void setRemainingBurstTime(int remainingBurstTime) { this.remainingBurstTime = remainingBurstTime; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }

    public int getTurnAroundTime() { return turnAroundTime; }
    public void setTurnAroundTime(int turnAroundTime) { this.turnAroundTime = turnAroundTime; }

    public int getResponseTime() { return responseTime; }
    public void setResponseTime(int responseTime) { this.responseTime = responseTime; }

    public Integer getStartTime() { return startTime; }
    public void setStartTime(Integer startTime) { this.startTime = startTime; }

    /**
     * Update Main.ProcessLink row for the given Process process.
     * Matches by process ID (Field.id). Writes:
     *   - arrivalTime, burstTime (optional; safe to set)
     *   - startTime, waitingTime, responseTime, turnAroundTime
     */
    public void addToProcessList(Process process) {
        if (process == null || Main.ProcessLink == null) return;
        int pid = process.getId();
        for (int i = 0; i < Main.numProcess; i++) {
            if (Main.ProcessLink[i][Field.id.getValue()] == pid) {
                Main.ProcessLink[i][Field.arrivalTime.getValue()] = process.getArrivalTime();
                Main.ProcessLink[i][Field.burstTime.getValue()] = process.getBurstTime();
                Main.ProcessLink[i][Field.startTime.getValue()] = process.getStartTime() == null ? 0 : process.getStartTime();
                Main.ProcessLink[i][Field.waitingTime.getValue()] = process.getWaitingTime();
                Main.ProcessLink[i][Field.responseTime.getValue()] = process.getResponseTime();
                Main.ProcessLink[i][Field.turnAroundTime.getValue()] = process.getTurnAroundTime();
                break;
            }
        }
    }
}