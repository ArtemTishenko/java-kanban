package manager;

import constants.Status;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private int counter = 0;

    /**
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
        epic.setStatus(Status.NEW);
        epics.put(epic.getId(), epic);
        return epic;
    }
    private void setEpicStatus(Epic epic){
        ArrayList<Integer> relatedEpicSubtasksId =  epic.getSubtasksId();
        int counter = 0;
        for(Integer id: relatedEpicSubtasksId){
            Status currentSubtaskStatus = subtasks.get(id).getStatus();
            epic.setStatus(Status.NEW);
            if(currentSubtaskStatus.equals(Status.DONE)){
                counter++;
            }
            if(counter == relatedEpicSubtasksId.size()){
                epic.setStatus(Status.DONE);
            } else if (counter > 0 && counter < relatedEpicSubtasksId.size()) {
                epic.setStatus(Status.IN_PROGRESS);
            }
            if (currentSubtaskStatus.equals(Status.IN_PROGRESS)) {
                epic.setStatus(Status.IN_PROGRESS);
                break;
            }
        }
    }
    public Subtask createSubtask(Subtask createdSubtask) {
        createdSubtask.setId(++counter);
        if (createdSubtask.getStatus() == null) {
            createdSubtask.setStatus(Status.NEW);
        }
        subtasks.put(createdSubtask.getId(), createdSubtask);

        Epic relatedEpic = epics.get(createdSubtask.getEpicId());
        relatedEpic.addSubtaskId(createdSubtask.getId());
        setEpicStatus(relatedEpic);

        return createdSubtask;
    }

    /**
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
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();
        ArrayList<Integer> epicSubtasksId = epics.get(epicId).getSubtasksId();
        for(Integer id : epicSubtasksId){
            epicSubtasks.add(subtasks.get(id));
        }

        return epicSubtasks;
    }

    /**
     * Update
     * */
    public Task updateTask(Task updatedTask) {
       tasks.put(updatedTask.getId(),updatedTask);
       return updatedTask;
    }

    public Subtask updateSubtask(Subtask updatedSubtask) {
        subtasks.put(updatedSubtask.getId(), updatedSubtask);
        Epic relatedEpic = epics.get(updatedSubtask.getEpicId());
        setEpicStatus(relatedEpic);
        return updatedSubtask;
    }

    public Epic updateEpic(Epic updatedEpic) {
        Epic savedEpic = epics.get(updatedEpic.getId());
        savedEpic.setTitle(updatedEpic.getTitle());
        savedEpic.setDescription(updatedEpic.getDescription());
        return  updatedEpic;
    }

    /**
     * Delete
     * */
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Epic currentEpic = epics.get(subtasks.get(id).getEpicId());
            currentEpic.getSubtasksId().remove(Integer.valueOf(id));
            setEpicStatus(currentEpic);
            subtasks.remove((Integer.valueOf(id)));
        }
    }

    public void deleteEpic(int epicId) {
        if (epics.containsKey(epicId)) {
            Epic currentEpic = epics.get(epicId);
            for (Integer id : currentEpic.getSubtasksId()) {
                subtasks.remove(id);
            }
            epics.remove(epicId);
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteSubtasks();
            epic.setStatus(Status.NEW);
        }
    }

}
