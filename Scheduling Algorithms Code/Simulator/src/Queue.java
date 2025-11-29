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
        Process dataToReturn = null;
        if (Front != null) {
            if (Front == Rear) {
                Rear = null;
                return null;
            }
           
            dataToReturn = Front.getData();
            Front = Front.getNextNode();
            if (Front == null) {
                Rear = null; // If the queue is now empty, set Rear to null
            }
            count--;
        }
        
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

    
}