package task;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    void shouldHaveNoConflictTaskWithGeneratedIdAndWritten(){
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Test addNewTask", "Test addNewTask description");
        Task task2 = new Task("Test addNewTask", "Test addNewTask description");
        Task task3 = new Task("Test addNewTask", "Test addNewTask description",1);
        Task task4 = new Task("Test addNewTask", "Test addNewTask description",100);


        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.createTask(task4);

        assertEquals(1, task1.getId());// генерация по counter
        assertEquals(3, task3.getId());//генерация если существует задача с таким же id
        assertEquals(100, task4.getId());//генерация по преданному аргументу
    }

    @Test
    void shouldCreateTaskAndFindById() {
        Task task1 = new Task("title1","desc1");
        Epic epic1 = new Epic("epic1","descEpic1",2);
        Subtask subtask1 = new Subtask("subtask1", "descSubtask1", 2);

        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.createTask(task1);
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);

        assertNotNull(task1);
        assertNotNull(subtask1);
        assertNotNull(epic1);
        assertEquals(epic1.getClass(),taskManager.getEpicById(epic1.getId()).getClass(), "Возвращает верный класс эпика");
        assertEquals(subtask1.getClass(),taskManager.getSubtaskById(subtask1.getId()).getClass(),"Возвращает верный класс подзадачи");
        assertEquals(task1.getClass(),taskManager.getTaskById(task1.getId()).getClass(),"Возвращает верный класс задачи");



        assertEquals(task1.toString(),taskManager.getTaskById(task1.getId()).toString(),"Возаращает верную задачу по id");
        assertEquals(subtask1.toString(),taskManager.getSubtaskById(subtask1.getId()).toString(), "Возаращает верную подзадачу по id");
        assertEquals(epic1.toString(),taskManager.getEpicById(epic1.getId()).toString(),"Возаращает верный эпик по id");
    }


    @Test
    void shouldBeEqualsOfTasksIfIdIsSame() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Test addNewTask1", "Test addNewTask1 description");
        Task task2 = new Task("Test addNewTask2", "Test addNewTask1 description");
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        List<Task> listTasks = taskManager.getAllTasks();
        Task testTask = null;
        for (Task task : listTasks) {
            if (task.getId() == task1.getId()) {
                testTask = task;
            }
        }
        assertEquals(testTask, taskManager.getTaskById(task1.getId()), "Задачи одинаковы если id одинаковы ");

    }

    @Test
    void shouldBeEqualsOfChildTaskIfIdIsSame() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("Test addNewEpic", "Test addNewEpic description");
        taskManager.createEpic(epic1);
        assertEquals(epic1, taskManager.getEpicById(epic1.getId()));
        Subtask subtask1 = new Subtask("Test addNewSubtask", "Test addNewSubtask description", epic1.getId());
        Subtask subtask2 = new Subtask("Test addNewSubtask", "Test addNewSubtask description", epic1.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        List<Epic> listEpic = taskManager.getAllEpics();
        Epic testEpik = null;
        for (Epic epic : listEpic) {
            if (epic.getId() == epic1.getId()) {
                testEpik = epic;
            }
        }

        assertEquals(testEpik, taskManager.getEpicById(epic1.getId()));
        assertEquals(epic1, taskManager.getEpicById(epic1.getId()),"Эпики одинаковы, елси id одинаковы");
        assertEquals(subtask1, taskManager.getSubtaskById(subtask1.getId()),"Сабтаскы одинаковы, елси id одинаковы");

    }

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.createTask(task);

        Task savedTask = taskManager.getTaskById(task.getId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }
    @Test
    void add() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Test addNewTask11", "Test addNewTask description");
        historyManager.add(task);

        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }
}
