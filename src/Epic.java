import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description);
    }

    public Status setStatus() {
        Status currentStatus = Status.NEW;
        int counterStatus =0;
        if (subtasks.size() > 0) {
            for (Subtask subtask : subtasks) {
                if (subtask.getStatus().equals(Status.DONE)) {
                    counterStatus++;
                }
                if (subtask.getStatus().equals(Status.IN_PROGRESS)) {
                    currentStatus = Status.IN_PROGRESS;
                }
            }
            if(counterStatus == subtasks.size()){
                currentStatus = Status.DONE;
            }else if(counterStatus > 0 && counterStatus < subtasks.size()){
                currentStatus = Status.IN_PROGRESS;
            }
        } else {
            currentStatus = Status.NEW;
        }
        super.status = currentStatus;
        return currentStatus;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        super.status = setStatus();
    }

    public ArrayList<Subtask> getEpicSubtasks() {
        return subtasks;
    }
    public void deleteSubtasks(){
        this.subtasks.clear();
    }




    @Override
    public String toString() {
        String result = "{" +
                "title='" + super.title + '\'' +
                ", description='" + super.description + '\'' +
                ", id=" + super.id +
                ", status=" + super.status +
                ", subtasks.size=" + subtasks.size() +
                '}';

        return result;
    }
}
