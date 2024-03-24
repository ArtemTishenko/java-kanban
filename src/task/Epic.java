package task;

import constants.Status;

import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description);
    }
    public Epic(String title, String description,int id) {
        super(title, description,id);
    }


    public void setStatus(Status status) {
        super.status = status;
    }

    public void addSubtaskId(Integer subtaskId) {
        subtasksId.add(subtaskId);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void deleteSubtasks() {
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
