package manager;

import constants.Status;
import org.junit.jupiter.api.Test;
import task.Task;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryMangerTest {
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    void shouldReturnRightHistoryManagerHistory() {
        Task task1 = new Task("Test addNewTask1", "Test addNewTask description");
        Task task2 = new Task("Test addNewTask2", "Test addNewTask description");
        List<Task> testListHistory = new ArrayList<>();
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);


        Task inMemoryTask1 = inMemoryTaskManager.getTaskById(task1.getId());
        testListHistory.add(new Task(
                inMemoryTask1.getTitle(),
                inMemoryTask1.getDescription(),
                inMemoryTask1.getId(),
                inMemoryTask1.getStatus()
        ));
        inMemoryHistoryManager.add(inMemoryTask1);
        Task inMemoryTask2 = inMemoryTaskManager.getTaskById(task2.getId());
        testListHistory.add(new Task(
                inMemoryTask2.getTitle(),
                inMemoryTask2.getDescription(),
                inMemoryTask2.getId(),
                inMemoryTask2.getStatus()
        ));
        inMemoryHistoryManager.add(task2);

        List<Task> inMemoryHistory = inMemoryHistoryManager.getHistory();
        assertEquals(testListHistory.toString(), inMemoryHistory.toString());
    }

    @Test
    void shouldReturnEmptyArrayAfterDeletingTaskFromHistory() {
        Task task1 = new Task("Test addNewTask1", "Test addNewTask description");
        inMemoryTaskManager.createTask(task1);
        Task inMemoryTask1 = inMemoryTaskManager.getTaskById(task1.getId());
        ArrayList<Task> testListHistory = new ArrayList<>();

        testListHistory.add(new Task(
                inMemoryTask1.getTitle(),
                inMemoryTask1.getDescription(),
                inMemoryTask1.getId(),
                inMemoryTask1.getStatus()
        ));
        inMemoryHistoryManager.add(new Task(task1.getTitle(), task1.getDescription(), task1.getId(), task1.getStatus()));

        List<Task> inMemoryHistory = inMemoryHistoryManager.getHistory();
        assertEquals(testListHistory.toString(), inMemoryHistory.toString());

        inMemoryHistoryManager.remove(task1.getId());
        assertEquals(inMemoryHistoryManager.getHistory().size(), 0);
    }
}
