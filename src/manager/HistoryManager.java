package manager;

import task.Node;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {
    void add(Task task);

    void remove(int id);

    List<Task> getHistory();

    void removeNode(Node<Task> node);

    void linkLast(Task task);

    ArrayList<Task> getTasks();

}
