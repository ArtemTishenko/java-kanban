package manager;

public class Managers {
    public Managers() {
    }

    public TaskManager getDefault(){
        return new InMemoryTaskManager();
    }
    public HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
