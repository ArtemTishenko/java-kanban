package manager;

import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private List<Task> historyList = new ArrayList<>();
    private final static int HISTORY_SIZE =10;

    @Override
    public void add(Task task) {
        Task cloneTask = new Task(task.getTitle(),task.getDescription(),task.getId(),task.getStatus());
        if(historyList.size() < HISTORY_SIZE){
            historyList.add(cloneTask);
        }else{
            historyList.removeFirst();
            historyList.add(cloneTask);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
