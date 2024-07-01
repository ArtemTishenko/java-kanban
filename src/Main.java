import constants.Status;
import manager.*;
import task.Epic;
import task.Subtask;
import task.Task;


public class Main {

    public static void main(String[] args) {
        customScenery();
        userScenery();
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }

    private static void customScenery() {
        InMemoryTaskManager inMemoryTaskManager1 = new InMemoryTaskManager();
        System.out.println("-CREATE-");
        Task task1 = new Task("Task1", "Description task 1");
        Task task2 = new Task("Task2", "Description task 2");
        inMemoryTaskManager1.createTask(task1);
        inMemoryTaskManager1.createTask(task2);
        Epic epic1 = new Epic("Epic 1", "Description epic 1");
        Epic epic2 = new Epic("Epic 2", "Description epic 2");
        inMemoryTaskManager1.createEpic(epic1);
        inMemoryTaskManager1.createEpic(epic2);
        Subtask subtask1e1 = new Subtask("Subtask 1 epic1", "Description subtask 1ep1", epic1.getId());
        Subtask subtask2e1 = new Subtask("Subtask 2 epic1", "Description subtask 2ep1", epic1.getId());
        Subtask subtask3e2 = new Subtask("Subtask 3 epic2", "Description subtask 3ep2", epic2.getId());
        inMemoryTaskManager1.createSubtask(subtask1e1);
        inMemoryTaskManager1.createSubtask(subtask2e1);
        inMemoryTaskManager1.createSubtask(subtask3e2);

        System.out.println(inMemoryTaskManager1.getAllTasks());
        System.out.println(inMemoryTaskManager1.getAllEpics());
        System.out.println(inMemoryTaskManager1.getAllSubtasks());

        System.out.println("-UPDATE-");
        task1.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager1.updateTask(task1);
        task2.setStatus(Status.DONE);
        inMemoryTaskManager1.updateTask(task2);
        System.out.println(inMemoryTaskManager1.getAllTasks());
        subtask1e1.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager1.updateSubtask(subtask1e1);

        subtask2e1.setStatus(Status.DONE);
        inMemoryTaskManager1.updateSubtask(subtask2e1);

        subtask3e2.setStatus(Status.DONE);
        inMemoryTaskManager1.updateSubtask(subtask3e2);

        epic1.setTitle("Epic1 Updated");
        inMemoryTaskManager1.updateEpic(epic1);

        System.out.println(inMemoryTaskManager1.getAllSubtasks());
        System.out.println(inMemoryTaskManager1.getAllEpics());

        System.out.println("-DELETE-");
        inMemoryTaskManager1.getTaskById(task2.getId());

        inMemoryTaskManager1.deleteTask(task2.getId());
        System.out.println(inMemoryTaskManager1.getAllTasks());

        inMemoryTaskManager1.deleteSubtask(subtask1e1.getId());
        System.out.println(inMemoryTaskManager1.getAllSubtasks());
        inMemoryTaskManager1.deleteEpic(epic2.getId());
        System.out.println(inMemoryTaskManager1.getAllSubtasks());
        System.out.println(inMemoryTaskManager1.getAllEpics());

        System.out.println("-READ-");
        System.out.println(inMemoryTaskManager1.getEpicSubtasks(epic1.getId()));
        inMemoryTaskManager1.getTaskById(task1.getId());
        inMemoryTaskManager1.getEpicById(epic1.getId());
        inMemoryTaskManager1.getSubtaskById(subtask2e1.getId());
        System.out.println(inMemoryTaskManager1.getHistory());

        System.out.println("-DELETE ALL-");
        inMemoryTaskManager1.deleteAllSubtasks();
        System.out.println(inMemoryTaskManager1.getAllEpics());

        System.out.println("-MANAGER-");
        TaskManager inMemoryTaskManager2 = Managers.getDefault();

        inMemoryTaskManager2.createTask(task1);
        inMemoryTaskManager2.createTask(task2);
        inMemoryTaskManager2.createEpic(epic1);
        inMemoryTaskManager2.createSubtask(subtask1e1);
        inMemoryTaskManager2.createSubtask(subtask1e1);
        inMemoryTaskManager2.createEpic(epic2);
        inMemoryTaskManager2.createSubtask(subtask2e1);

        inMemoryTaskManager2.getTaskById(task1.getId());
        inMemoryTaskManager2.getEpicById(epic1.getId());
        inMemoryTaskManager2.getSubtaskById(subtask2e1.getId());
        inMemoryTaskManager2.getEpicById(epic1.getId());

        printAllTasks(inMemoryTaskManager2);

        inMemoryTaskManager2.deleteAllTasks();

    }

    private static void userScenery() {
        //1
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task1 = new Task("Task1", "Description task 1");
        Task task2 = new Task("Task2", "Description task 2");
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);

        Epic epic1 = new Epic("Epic 1", "Description epic 1");
        inMemoryTaskManager.createEpic(epic1);
        Subtask subtask1e1 = new Subtask("Subtask 1 epic1", "Description subtask 1ep1", epic1.getId());
        Subtask subtask2e1 = new Subtask("Subtask 2 epic1", "Description subtask 2ep1", epic1.getId());
        inMemoryTaskManager.createSubtask(subtask1e1);
        inMemoryTaskManager.createSubtask(subtask2e1);

        Epic epic2 = new Epic("Epic 2", "Description epic 2");
        inMemoryTaskManager.createEpic(epic2);
        //2
        inMemoryTaskManager.getTaskById(task2.getId());
        inMemoryTaskManager.getTaskById(task1.getId());
        inMemoryTaskManager.getEpicById(epic1.getId());
        inMemoryTaskManager.getEpicById(epic2.getId());
        inMemoryTaskManager.getEpicById(epic2.getId());
        inMemoryTaskManager.getSubtaskById(subtask1e1.getId());
        inMemoryTaskManager.getSubtaskById(subtask2e1.getId());
        printAllTasks(inMemoryTaskManager);
        inMemoryTaskManager.getSubtaskById(subtask1e1.getId());
        inMemoryTaskManager.getTaskById(task1.getId());
        inMemoryTaskManager.getEpicById(epic1.getId());
        //3
        printAllTasks(inMemoryTaskManager);

        //4
        inMemoryTaskManager.deleteTask(task1.getId());
        printAllTasks(inMemoryTaskManager);

        //5
        inMemoryTaskManager.deleteEpic(epic1.getId());
        printAllTasks(inMemoryTaskManager);


    }

}
