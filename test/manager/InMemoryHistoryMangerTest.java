package manager;

import constants.Status;
import org.junit.jupiter.api.Test;
import task.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryMangerTest {
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    void shouldReturnRightHistoryManagerHistory(){
        Task task1 = new Task("Test addNewTask1", "Test addNewTask description");
        Task task2 = new Task("Test addNewTask2", "Test addNewTask description");
        List<Task> testListHistory = new ArrayList<>();
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);


        Task inMemoryTask1 = inMemoryTaskManager.getTaskById(task1.getId());
        Task inMemoryTask2 = inMemoryTaskManager.getTaskById(task2.getId());
        testListHistory.add(new Task(
                inMemoryTask1.getTitle(),
                inMemoryTask1.getDescription(),
                inMemoryTask1.getId(),
                inMemoryTask1.getStatus()
        ));

        testListHistory.add(new Task(
                inMemoryTask2.getTitle(),
                inMemoryTask2.getDescription(),
                inMemoryTask2.getId(),
                inMemoryTask1.getStatus()
        ));

        inMemoryTaskManager.getTaskById(task1.getId()).setStatus(Status.DONE);
        testListHistory.add(new Task(
                inMemoryTask1.getTitle(),
                inMemoryTask1.getDescription(),
                inMemoryTask1.getId(),
                Status.NEW //устанавливаем здесь статус new по скольку второй вызов getId() добавляет в историю не измененный статус
        ));
        inMemoryTaskManager.getTaskById(task1.getId());
        testListHistory.add(new Task(
                inMemoryTask1.getTitle(),
                inMemoryTask1.getDescription(),
                inMemoryTask1.getId(),
                inMemoryTask1.getStatus()
        ));
        System.out.println(testListHistory);
        System.out.println(inMemoryHistoryManager.getHistory());

        List<Task> inMemoryHistory = inMemoryHistoryManager.getHistory();

        assertEquals(testListHistory.toString(), inMemoryHistory.toString());

    }
}
