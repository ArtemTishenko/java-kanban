package manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {
    @Test
    void shouldReturnReadyClassFromManager() {
        Managers managers = new Managers();
        InMemoryTaskManager inMemoryTaskManagerTest = new InMemoryTaskManager();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        var taskManagerTest = managers.getDefault();
        var historyManagerTest = managers.getDefaultHistory();

        taskManagerTest.getAllTasks();
        inMemoryTaskManagerTest.getAllTasks();

        inMemoryHistoryManager.getHistory();
        historyManagerTest.getHistory();


        assertNotNull(inMemoryTaskManagerTest.getAllTasks());
        assertNotNull(inMemoryHistoryManager.getHistory());

        assertEquals(taskManagerTest.getAllTasks(), inMemoryTaskManagerTest.getAllTasks());
    }


}
