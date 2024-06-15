package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryTaskManagerTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    void shouldDeleteSubtaskFromEpicIfDeleteSubtask() {
        Epic epic1 = new Epic("Test epic 1", "test epic1 description");
        inMemoryTaskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Test subtask1", "test subtask1 description", epic1.getId());
        inMemoryTaskManager.createSubtask(subtask1);

        ArrayList<Integer> idEpicSubtasks = inMemoryTaskManager.getEpicById(epic1.getId()).getSubtasksId();
        assertEquals(idEpicSubtasks.getFirst(), subtask1.getId());
        inMemoryTaskManager.deleteSubtask(subtask1.getId());
        ArrayList<Integer> idEpicSubtasksEmpty = inMemoryTaskManager.getEpicById(epic1.getId()).getSubtasksId();
        assertTrue(idEpicSubtasksEmpty.isEmpty());

    }

}
