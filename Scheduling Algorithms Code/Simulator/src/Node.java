public class Node <t extends Process> {
    private t data;
    private Node<t> nextNode;

    public Node() {
        this.data = null;
        this.nextNode = null;
    }
    public Node(t data){
        this.data = data;
        this.nextNode = null;
    }
    //Geters and Seters
    public t getData() {
        return data;
    }
    public void setData(t data) {
        this.data = data;
    }
    public Node<t> getNextNode() {
        return nextNode;
    }
    public void setNextNode(Node<t> nextNode) {
        this.nextNode = nextNode;
    }  

}
