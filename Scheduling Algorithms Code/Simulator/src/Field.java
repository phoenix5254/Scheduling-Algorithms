
public enum Field {
    id (0),
    arrivalTime (1),
    burstTime (2),
    priority (3),
    waitingTime (4),
    turnAroundTime (5),
    startTime (6),
    responseTime (7);

    private int value;

    Field(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}