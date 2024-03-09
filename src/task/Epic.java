package task;

import constants.Status;
import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description);
    }

    public void setStatus(HashMap<Integer,Subtask> subtasks) {


        Status currentStatus = Status.NEW;
        int counterStatus =0;
        if (subtasks.size() > 0) {

            for (Subtask subtask : subtasks.values()) {
                if (subtask.getStatus().equals(Status.DONE)) {
                    counterStatus++;
                }
                if (subtask.getStatus().equals(Status.IN_PROGRESS)) {
                    currentStatus = Status.IN_PROGRESS;
                    break;
                }
            }
            if(counterStatus == subtasks.size()){
                currentStatus = Status.DONE;
            }else if(counterStatus > 0 && counterStatus < subtasks.size()){
                currentStatus = Status.IN_PROGRESS;
            }
        }
        super.status = currentStatus;

    }

    public void addSubtaskId(Integer subtaskId, HashMap<Integer,Subtask> subtasks) {
        subtasksId.add(subtaskId);
        setStatus(subtasks);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }
    public void deleteSubtasks(){
        this.subtasksId.clear();
    }




    @Override
    public String toString() {
        String result = "{" +
                "title='" + super.title + '\'' +
                ", description='" + super.description + '\'' +
                ", id=" + super.id +
                ", status=" + super.status +
                ", subtasksId.size=" + subtasksId.size() +
                '}';

        return result;
    }
}
