package manager;

import constants.Status;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();

    private HistoryManager historyManager = Managers.getDefaultHistory();

    private int counter = 0;

    private void setEpicStatus(Epic epic) {
        ArrayList<Integer> relatedEpicSubtasksId = epic.getSubtasksId();
        int counter = 0;
        for (Integer id : relatedEpicSubtasksId) {
            Status currentSubtaskStatus = subtasks.get(id).getStatus();
            epic.setStatus(Status.NEW);
            if (currentSubtaskStatus.equals(Status.DONE)) {
                counter++;
            }
            if (counter == relatedEpicSubtasksId.size()) {
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

    /**
     * Create
     */
    @Override
    public Task createTask(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Status.NEW);
        }
        task.setId(++counter);
        tasks.put(task.getId(), task);

        return task;
    }


    @Override
    public Epic createEpic(Epic epic) {

        epic.setId(++counter);
        epic.setStatus(Status.NEW);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
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
     */
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }


    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();
        ArrayList<Integer> epicSubtasksId = epics.get(epicId).getSubtasksId();
        for (Integer id : epicSubtasksId) {
            epicSubtasks.add(subtasks.get(id));
        }

        return epicSubtasks;
    }

    /**
     * Update
     */
    @Override
    public Task updateTask(Task updatedTask) {
        tasks.put(updatedTask.getId(), updatedTask);
        return updatedTask;
    }

    @Override
    public Subtask updateSubtask(Subtask updatedSubtask) {
        subtasks.put(updatedSubtask.getId(), updatedSubtask);
        Epic relatedEpic = epics.get(updatedSubtask.getEpicId());
        setEpicStatus(relatedEpic);
        return updatedSubtask;
    }

    @Override
    public Epic updateEpic(Epic updatedEpic) {
        Epic savedEpic = epics.get(updatedEpic.getId());
        savedEpic.setTitle(updatedEpic.getTitle());
        savedEpic.setDescription(updatedEpic.getDescription());
        return updatedEpic;
    }

    /**
     * Delete
     */
    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Epic currentEpic = epics.get(subtasks.get(id).getEpicId());
            currentEpic.getSubtasksId().remove(Integer.valueOf(id));
            setEpicStatus(currentEpic);
            subtasks.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteEpic(int epicId) {
        if (epics.containsKey(epicId)) {
            Epic currentEpic = epics.get(epicId);
            for (Integer subtaskEpicId : currentEpic.getSubtasksId()) {
                subtasks.remove(subtaskEpicId);
                historyManager.remove(subtaskEpicId);
            }
            epics.remove(epicId);
            historyManager.remove(epicId);
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteSubtasks();
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryTaskManager that = (InMemoryTaskManager) o;
        return counter == that.counter && Objects.equals(tasks, that.tasks) && Objects.equals(subtasks, that.subtasks) && Objects.equals(epics, that.epics) && Objects.equals(historyManager, that.historyManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, subtasks, epics, historyManager, counter);
    }

}
