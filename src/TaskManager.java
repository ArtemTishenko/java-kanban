import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
     int counter = 0;

    /*
     * Create
     * */
    public Task createTask(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Status.NEW);
        }
        task.setId(++counter);
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        epic.setId(++counter);
        epic.setStatus();
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Subtask createSubtask(Subtask subtask) {
        subtask.setId(++counter);
        if (subtask.getStatus() == null) {
            subtask.setStatus(Status.NEW);
        }
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addSubtask(subtask);

        return subtask;
    }

    /*
     * Read
     * */
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    public Collection<Epic> getAllEpics() {
        return epics.values();
    }

    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        return epics.get(epicId).getEpicSubtasks();
    }

    /*
     * Update
     * */
    public Task updateTask(Task updatedTask) {

        for (Task task : tasks.values()) {
            if (task.getId() == updatedTask.getId()) {
                task = updatedTask;
            }
        }
        return updatedTask;
    }

    public void updateSubtask(Subtask updatedSubtask) {
        int relatedEpicId;
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getId() == updatedSubtask.getId()) {
                subtask = updatedSubtask;
                relatedEpicId = updatedSubtask.getEpicId();
                Epic relatedEpic = epics.get(relatedEpicId);
                relatedEpic.setStatus();
            }
        }
    }

    public Epic updateEpic(Epic updatedEpic) {
        for (Epic epic : epics.values()) {
            if (epic.getId() == updatedEpic.getId()) {
                epic = updatedEpic;
            }
        }
        return updatedEpic;
    }

    /*
     * Delete
     * */
    public void deleteTask(int id) {
        if(tasks.containsKey(id)){
            tasks.remove(id);
        }

    }

    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Epic currentEpic = epics.get(getSubtaskById(id).getEpicId());
            currentEpic.getEpicSubtasks().remove(getSubtaskById(id));
            currentEpic.setStatus();
            subtasks.remove(id);
        }
    }
    public void deleteEpic(int epicId){
        if(epics.containsKey(epicId)){
            for(Subtask subtask:subtasks.values()){
                if(subtask.getEpicId() == epicId){
                    subtasks.remove(subtask.getId());
                }
            }
            epics.remove(epicId);
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteSubtasks();
        }
    }

}
