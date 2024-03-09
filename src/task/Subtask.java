package task;
public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, int epicId) {
        super(title, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        String result = "{" +
                "title='" + super.title + '\'' +
                ", description='" + super.description + '\'' +
                ", id=" + super.id +
                ", status=" + super.status +
                ", epicId=" +epicId +
                '}';

        return result;
    }
}
