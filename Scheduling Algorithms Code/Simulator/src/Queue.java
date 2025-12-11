import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Queue<t extends Process> {

    private Node<t> Front;
    private Node<t> Rear;

    private static int count = 0;

    public Queue() {
        Front = null;
        Rear = null;
    }

    public Node<t> getFront() {
        return Front;
    }

    public Node<t> getRear() {
        return Rear;
    }

    public void setFront(Node<t> n) {
        Front = n;
    }

    public void setRear(Node<t> n) {
        Rear = n;
    }

    public int getCount() {
        return count;
    }

    public void Enqueue(t process) {// Add a new process to the queue
        try {
            Node<t> temp;
            temp = new Node<t>(process);
            if (temp != null) {
                if (Front == null) {
                    Front = temp;
                    Rear = temp;
                } else {
                    Rear.setNextNode(temp);
                    Rear = temp;
                }
                count++;
            }
        } catch (OutOfMemoryError e) {
            System.err.println("Error: Node could not be created.\n Memory is full.");
        }
    }

    public Process Dequeue() {// Remove a process from the queue
        if (Front == null) {
            return null;
        }
        Process dataToReturn = Front.getData();
        if (Front == Rear) {
            Front = null;
            Rear = null;
        } else {
            Front = Front.getNextNode();
        }
        count--;
        return dataToReturn;
    }

    public Process QueueFront() {
        if (Front == null) {
            System.out.println("The Queue is empty, cannot return value(s).");
            return null;
        } else {
            return Front.getData();
        }
    }

    public Process peek() {
        if (Front == null) {
            System.out.println("The Queue is empty, cannot peek.");
            return null;
        } else {
            return Front.getData();
        }
    }

    public boolean isEmpty() {
        return Front == null; // return true in null else returns falsw
    }

    public void displayGanttFromQueue(int choice, int totalTimeUnits) {
        if (Front == null) {
            System.out.println("The Queue is empty. Nothing to display.");
            return;
        }

        final int boxWidth = 5; // width of each process box inside the bars: | P1 |
        final int segWidth = boxWidth + 1; // distance between two '|' boundaries

        StringBuilder gantt = new StringBuilder();
        StringBuilder top = new StringBuilder();
        StringBuilder bottom = new StringBuilder();

        List<Integer> boundaryPositions = new ArrayList<>();

        int currentTime = 0;

        Node<t> cur = Front;

        System.out.println("\n--- CPU Execution Sequence (Time Unit = 1) ---");

        while (cur != null) {

            // record where this boundary starts
            boundaryPositions.add(gantt.length());

            // left boundary
            gantt.append("|");

            // process ID centered inside the box
            Process p = cur.getData();
            String id = "P" + p.getId();
            int leftPad = (boxWidth - id.length()) / 2;
            int rightPad = boxWidth - id.length() - leftPad;

            gantt.append(" ".repeat(leftPad))
                    .append(id)
                    .append(" ".repeat(rightPad));

            // advance time
            currentTime += choice;

            cur = cur.getNextNode();
        }

        // final boundary
        boundaryPositions.add(gantt.length());
        gantt.append("|");

        // borders sized exactly to match the gantt bar
        top.append("-".repeat(gantt.length()));
        bottom.append("-".repeat(gantt.length()));

        // ==============================
        // BUILD TIME MARKER LINE
        // ==============================
        char[] timeLine = new char[gantt.length()];
        Arrays.fill(timeLine, ' ');

        // time 0 under the first boundary
        put(timeLine, boundaryPositions.get(0), "0");

        int t = 0;

        // times under each boundary
        for (int i = 1; i < boundaryPositions.size(); i++) {
            t += choice; // each segment increases by choice
            put(timeLine, boundaryPositions.get(i), String.valueOf(t));
        }

        // PRINT SEQUENCE
        System.out.println(top.toString());
        System.out.println(gantt.toString());
        System.out.println(bottom.toString());
        System.out.println(new String(timeLine));
        System.out.println("\n **Total Time Units Displayed**: **" + currentTime + "**");
    }

    private void put(char[] arr, int pos, String s) {
    // Center the string at the given position
    int start = pos - s.length() / 2;
    for (int i = 0; i < s.length(); i++) {
        int idx = start + i;
        if (idx >= 0 && idx < arr.length) {
            arr[idx] = s.charAt(i);
        }
    }
}

    public Main get(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

}