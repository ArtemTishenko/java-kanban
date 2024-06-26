package task;


public class Node<T> {
    Node<T> prev;
    T task;
    Node<T> next;

    public Node(Node<T> prev, T task, Node<T> next) {
        this.prev = prev;
        this.task = task;
        this.next = next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public T getTask() {
        return task;
    }

    public void setTask(T task) {
        this.task = task;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

}
