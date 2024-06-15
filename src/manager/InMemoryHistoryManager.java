package manager;

import task.Node;
import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> historyList = new ArrayList<>();
    private HashMap<Integer, Node> historyMap = new HashMap<>();
    private Node tail;
    private Node head;

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            Node node = historyMap.get(task.getId());
            removeNode(node);
        }
        linkLast(task);
        historyMap.put(task.getId(), tail);

    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            Node removableNode = historyMap.get(id);
            removeNode(removableNode);

        }
    }

    private void removeNode(Node node) {
        int id = node.getTask().getId();
        if (historyMap.containsKey(id)) {
            Node removableNode = historyMap.get(id);

            if (removableNode.equals(head)) {//Если это первый Node
                if (head == tail) { //Проверяем, если этот Node единственный
                    head = null;
                    tail = null;
                } else {
                    head = removableNode.getNext(); //Записываем следующий Node как первый
                    head.setPrev(null);
                }
            } else if (removableNode.equals(tail)) { //Если это последний Node
                tail = removableNode.getPrev(); //Записываем предыдущий Node как последний
                tail.setNext(null);
            } else {
                Node prev = removableNode.getPrev(); //Получаем Node перед удаляемым
                Node next = removableNode.getNext(); //Получаем Node после удаляемого
                prev.setNext(next); //Записываем ссылку на следующий Node в предыдущий
                next.setPrev(prev);
            }
        }

    }

    private void linkLast(Task task) {
        Node oldTail = tail;
        Node newNode = new Node(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            tail.getPrev().setNext(tail);
        }
    }

    private ArrayList<Task> getTasks() {
        historyList.clear();
        if (head != null) {
            Node node = head;
            while (node != null) {
                historyList.add(node.getTask());
                node = node.getNext();
            }
        }
        return historyList;
    }
}
