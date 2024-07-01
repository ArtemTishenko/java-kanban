package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    Task createTask(Task task);

    Epic createEpic(Epic epic);

    Subtask createSubtask(Subtask createdSubtask);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    Task getTaskById(int id);

    Subtask getSubtaskById(int id);

    Epic getEpicById(int id);

    ArrayList<Subtask> getEpicSubtasks(int epicId);

    Task updateTask(Task updatedTask);

    Subtask updateSubtask(Subtask updatedSubtask);

    Epic updateEpic(Epic updatedEpic);

    void deleteTask(int id);

    void deleteSubtask(int id);

    void deleteEpic(int epicId);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    List<Task> getHistory();

}
