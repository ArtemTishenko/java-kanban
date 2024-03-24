package manager;

import constants.Status;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HistoryManager historyManager = new InMemoryHistoryManager();


    private int counter = 0;

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

    /**
     * Create
     * */
    @Override
    public Task createTask(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Status.NEW);
        }
        task.setId(generateTaskId(task.getId()));
        tasks.put(task.getId(), task);

        return task;
    }

    private int generateTaskId(int taskId) {
        if(taskId !=0){
            int maxId = 0;
            if (tasks.containsKey(taskId)){//5
                for (int currentTaskId :tasks.keySet()){
                    if (currentTaskId>maxId){
                        maxId = currentTaskId;
                    }
                }
                return ++maxId;
            }else {
                return taskId;
            }

        }else {
            return ++counter ;
        }


    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(generateTaskId(epic.getId()));
        epic.setStatus(Status.NEW);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Subtask createSubtask(Subtask createdSubtask) {
        createdSubtask.setId(generateTaskId(createdSubtask.getId()));
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
    @Override
    public List<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Task task:tasks.values()){
            allTasks.add(task);
        }
        return allTasks;
    }

    @Override
    public List<Epic> getAllEpics() {
        ArrayList<Epic> allEpics = new ArrayList<>();
        for (Epic epic:epics.values()){
            allEpics.add(epic);
        }
        return allEpics;
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        ArrayList<Subtask> allSubtasks = new ArrayList<>();
        for (Subtask subtask:subtasks.values()){
            allSubtasks.add(subtask);
        }
        return allSubtasks;
    }


    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
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
        for(Integer id : epicSubtasksId){
            epicSubtasks.add(subtasks.get(id));
        }

        return epicSubtasks;
    }

    /**
     * Update
     * */
    @Override
    public Task updateTask(Task updatedTask) {
       tasks.put(updatedTask.getId(),updatedTask);
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
        return  updatedEpic;
    }

    /**
     * Delete
     * */
    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Epic currentEpic = epics.get(subtasks.get(id).getEpicId());
            currentEpic.getSubtasksId().remove(Integer.valueOf(id));
            setEpicStatus(currentEpic);
            subtasks.remove((Integer.valueOf(id)));
        }
    }

    @Override
    public void deleteEpic(int epicId) {
        if (epics.containsKey(epicId)) {
            Epic currentEpic = epics.get(epicId);
            for (Integer id : currentEpic.getSubtasksId()) {
                subtasks.remove(id);
            }
            epics.remove(epicId);
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
