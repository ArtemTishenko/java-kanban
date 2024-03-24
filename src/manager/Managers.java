package manager;

public class Managers {
    public <T extends TaskManager> TaskManager getDefault(){
        return new InMemoryTaskManager();
    }
    public HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
