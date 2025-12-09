public class Queue <t extends Process> {

    private Node<t> Front;
    private Node<t> Rear;

    private static int count=0;


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
    public int getCount(){ return count;}


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
        return Front == null; //return true in null else returns falsw
    }
   public void displayGanttFromQueue(int choice, int totalTimeUnits) {
    if (Front == null) {
        System.out.println("The Queue is empty. Nothing to display.");
        return;
    }

    // 1. Initialize variables
    StringBuilder topBorder = new StringBuilder();
    StringBuilder ganttBar = new StringBuilder();
    StringBuilder bottomBorder = new StringBuilder();
    StringBuilder timeMarkers = new StringBuilder();
    int cumulativeTime = 0;
    
    // Define a fixed width for each process segment for perfect alignment.
    // Width 4 gives us space for "| Px |" or "| Pxx |" and the time marker below.
    final int segmentWidth = 4; 
    
    // Start at time 0
    timeMarkers.append("0"); 

    // Use a temporary pointer to traverse the queue
    Node<t> current = Front; 

    System.out.println("\n--- CPU Execution Sequence (Time Unit = 1) ---");
    
    // 2. Traverse and build the output strings
    while (current != null) {
        Process processData = current.getData();
        int processID = processData.getId(); // Requires Process.getId()
        
        // --- Build the Gantt Bar and Borders ---
        ganttBar.append("|");
        
        // Create the ID string. Use 'Px' format, centered within the segmentWidth.
        String idString = "P" + processID; 
        
        // Calculate internal padding to center the ID string within the space of (segmentWidth - 1).
        // Example: Width 4 -> Space of 3. ID 'P1' (2 chars). Left padding 1, Right padding 0.
        int dataLength = idString.length();
        int availableSpace = segmentWidth - 1; // 3 characters available between the pipes
        int leftPadding = (availableSpace - dataLength) / 2;
        int rightPadding = availableSpace - dataLength - leftPadding;

        // Apply padding: (left spaces) + (ID) + (right spaces)
        ganttBar.append(" ".repeat(leftPadding))
                .append(idString)
                .append(" ".repeat(rightPadding));

        // Append the separators
        topBorder.append("-".repeat(segmentWidth));
        bottomBorder.append("-".repeat(segmentWidth));
        
        // --- Build the Time Markers ---
        cumulativeTime += choice; // Increment time by 2 if utilized by MLQ, else use 1 time unit
        
        // The total padding needed is the full segmentWidth, minus the length of the new time number.
        String cumulativeTimeString = String.valueOf(cumulativeTime);
        int paddingLength = segmentWidth - cumulativeTimeString.length();
        
        // Append the time marker with padding
        timeMarkers.append(String.format("%" + paddingLength + "s", cumulativeTimeString));
        // Add 1 to the cumulative time before finishing
        if (cumulativeTime == totalTimeUnits){
            cumulativeTime++;
            cumulativeTimeString = String.valueOf(cumulativeTime);
        }
        current = current.getNextNode();
    }
    
    // 3. Print the final results
    ganttBar.append("|");
    
    System.out.println(topBorder.toString()); 
    System.out.println(ganttBar.toString());
    System.out.println(bottomBorder.toString()); 
    System.out.println(timeMarkers.toString());
    
    System.out.println("\n **Total Time Units Displayed**: **" + cumulativeTime + "**");
}

    public Main get(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    
}