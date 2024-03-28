package manager;

import task.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private LinkedList<Task> historyList = new LinkedList<>();
    private final static int HISTORY_SIZE =10;

    @Override
    public void add(Task task) {
        if(!task.equals(null)){
            if(historyList.size() < HISTORY_SIZE){
                historyList.add(task);

            }else{
                historyList.removeFirst();
                historyList.add(task);
            }
        }

    }

    @Override
    public List<Task> getHistory() {
       LinkedList<Task> cloneHistoryList = new LinkedList<>();
       for(Task taskHistory:historyList){
           cloneHistoryList.add(taskHistory);
       }
        return cloneHistoryList;
    }
}
