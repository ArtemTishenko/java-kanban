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
        epic.setStatus(subtasks);
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Subtask createSubtask(Subtask createdSubtask) {
        createdSubtask.setId(++counter);
        if (createdSubtask.getStatus() == null) {
            createdSubtask.setStatus(Status.NEW);
        }
        subtasks.put(createdSubtask.getId(), createdSubtask);

        //формируем мапу с подзадачами эпика для обновления статуса
        HashMap<Integer, Subtask> epicSubtasks = new HashMap<>();
        for (Subtask cSubtask : subtasks.values()) {
            if (cSubtask.getEpicId() == createdSubtask.getEpicId()) {
                epicSubtasks.put(cSubtask.getId(), cSubtask);
            }
        }
        epics.get(createdSubtask.getEpicId())
                .addSubtaskId(createdSubtask.getId(), epicSubtasks);

        return createdSubtask;
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
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();
        ArrayList<Integer> epicSubtasksId = epics.get(epicId).getSubtasksId();
        for (Subtask subtask : subtasks.values()) {
            for (Integer epicSubtaskId : epicSubtasksId) {
                if (subtask.getId() == epicSubtaskId) {
                    epicSubtasks.add(subtask);
                }
            }
        }
        return epicSubtasks;
    }

    /*
     * Update
     * */
    public Task updateTask(Task updatedTask) {
        Task task = tasks.get(updatedTask.getId());
        task = updatedTask;
        return task;
    }

    public void updateSubtask(Subtask updatedSubtask) {

        HashMap<Integer, Subtask> epicSubtasks = new HashMap<>();

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getId() == updatedSubtask.getId()) {
                subtask = updatedSubtask;
            }
            Epic relatedEpic = epics.get(updatedSubtask.getEpicId());
            //формируем мапу с подзадачами эпика для обновления статуса
            ArrayList<Integer> relatedEpicSubtasksId = relatedEpic.getSubtasksId();
            for (Integer id : relatedEpicSubtasksId) {
                if (id == subtask.getId()) {
                    epicSubtasks.put(subtask.getId(), subtask);
                }
            }
            relatedEpic.setStatus(epicSubtasks);
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
        tasks.remove(id);
    }

    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Epic currentEpic = epics.get(getSubtaskById(id).getEpicId());
            currentEpic.getSubtasksId().remove(getSubtaskById(id));
            currentEpic.setStatus(subtasks);
            subtasks.remove(id);
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
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteSubtasks();
            epic.setStatus(subtasks);
        }
    }

}
