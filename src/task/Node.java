package task;


public class Node{
    Node prev ;
    Task task;
    Node next;
    public Node(Node prev,Task task,Node next) {
        this.prev = prev;
        this.task = task;
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

//    @Override
//    public String toString() {
//        return "Node{" +
//                "prev=" + prev +
//                ", task=" + task +
//                ", next=" + next +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Node node = (Node) o;
//        return Objects.equals(prev, node.prev) && Objects.equals(task, node.task) && Objects.equals(next, node.next);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(prev, task, next);
//    }
}
