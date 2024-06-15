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

    private static void printAllTasks( TaskManager manager) {
        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
    private static void customScenery(){
        InMemoryTaskManager InMemoryTaskManager = new InMemoryTaskManager();
        System.out.println("-CREATE-");
        Task task1 = new Task("Task1", "Description task 1");
        Task task2 = new Task("Task2", "Description task 2");
        InMemoryTaskManager.createTask(task1);
        InMemoryTaskManager.createTask(task2);
        Epic epic1 = new Epic("Epic 1", "Description epic 1");
        Epic epic2 = new Epic("Epic 2", "Description epic 2");
        InMemoryTaskManager.createEpic(epic1);
        InMemoryTaskManager.createEpic(epic2);
        Subtask subtask1e1 = new Subtask("Subtask 1 epic1", "Description subtask 1ep1", epic1.getId());
        Subtask subtask2e1 = new Subtask("Subtask 2 epic1", "Description subtask 2ep1", epic1.getId());
        Subtask subtask3e2 = new Subtask("Subtask 3 epic2", "Description subtask 3ep2", epic2.getId());
        InMemoryTaskManager.createSubtask(subtask1e1);
        InMemoryTaskManager.createSubtask(subtask2e1);
        InMemoryTaskManager.createSubtask(subtask3e2);

        System.out.println(InMemoryTaskManager.getAllTasks());
        System.out.println(InMemoryTaskManager.getAllEpics());
        System.out.println(InMemoryTaskManager.getAllSubtasks());

        System.out.println("-UPDATE-");
        task1.setStatus(Status.IN_PROGRESS);
        InMemoryTaskManager.updateTask(task1);
        task2.setStatus(Status.DONE);
        InMemoryTaskManager.updateTask(task2);
        System.out.println(InMemoryTaskManager.getAllTasks());
        subtask1e1.setStatus(Status.IN_PROGRESS);
        InMemoryTaskManager.updateSubtask(subtask1e1);

        subtask2e1.setStatus(Status.DONE);
        InMemoryTaskManager.updateSubtask(subtask2e1);

        subtask3e2.setStatus(Status.DONE);
        InMemoryTaskManager.updateSubtask(subtask3e2);

        epic1.setTitle("Epic1 Updated");
        InMemoryTaskManager.updateEpic(epic1);

        System.out.println(InMemoryTaskManager.getAllSubtasks());
        System.out.println(InMemoryTaskManager.getAllEpics());

        System.out.println("-DELETE-");
        InMemoryTaskManager.getTaskById(task2.getId());

        System.out.println("@@@@@History"+InMemoryTaskManager.getHistory());
        InMemoryTaskManager.deleteTask(task2.getId());
        System.out.println("@@@@@History"+InMemoryTaskManager.getHistory());
        System.out.println(InMemoryTaskManager.getAllTasks());

        InMemoryTaskManager.deleteSubtask(subtask1e1.getId());
        System.out.println(InMemoryTaskManager.getAllSubtasks());
        InMemoryTaskManager.deleteEpic(epic2.getId());
        System.out.println(InMemoryTaskManager.getAllSubtasks());
        System.out.println(InMemoryTaskManager.getAllEpics());

        System.out.println("___________________");


        System.out.println("---------------------");

        System.out.println("-READ-");
        System.out.println(InMemoryTaskManager.getEpicSubtasks(epic1.getId()));
        InMemoryTaskManager.getTaskById(task1.getId());
        InMemoryTaskManager.getEpicById(epic1.getId());
        InMemoryTaskManager.getSubtaskById(subtask2e1.getId());
        System.out.println(InMemoryTaskManager.getHistory());


        System.out.println("-DELETE ALL-");
        InMemoryTaskManager.deleteAllSubtasks();
        System.out.println(InMemoryTaskManager.getAllEpics());

        System.out.println("-MANAGER-");
        TaskManager inMemoryTaskManager = Managers.getDefault();

        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.createEpic(epic1);
        inMemoryTaskManager.createSubtask(subtask1e1);
        inMemoryTaskManager.createSubtask(subtask1e1);
        inMemoryTaskManager.createEpic(epic2);
        inMemoryTaskManager.createSubtask(subtask2e1);

        inMemoryTaskManager.getTaskById(task1.getId());
        inMemoryTaskManager.getEpicById(epic1.getId());
        inMemoryTaskManager.getSubtaskById(subtask2e1.getId());
        inMemoryTaskManager.getEpicById(epic1.getId());

        printAllTasks(inMemoryTaskManager);

    }
    private static void userScenery(){
        //1
        InMemoryTaskManager InMemoryTaskManager = new InMemoryTaskManager();

        Task task1 = new Task("Task1", "Description task 1");
        Task task2 = new Task("Task2", "Description task 2");
        InMemoryTaskManager.createTask(task1);
        InMemoryTaskManager.createTask(task2);

        Epic epic1 = new Epic("Epic 1", "Description epic 1");
        InMemoryTaskManager.createEpic(epic1);
        Subtask subtask1e1 = new Subtask("Subtask 1 epic1", "Description subtask 1ep1", epic1.getId());
        Subtask subtask2e1 = new Subtask("Subtask 2 epic1", "Description subtask 2ep1", epic1.getId());
        InMemoryTaskManager.createSubtask(subtask1e1);
        InMemoryTaskManager.createSubtask(subtask2e1);

        Epic epic2 = new Epic("Epic 2", "Description epic 2");
        InMemoryTaskManager.createEpic(epic2);
        //2
        InMemoryTaskManager.getTaskById(task2.getId());
        InMemoryTaskManager.getTaskById(task1.getId());
        InMemoryTaskManager.getEpicById(epic1.getId());
        InMemoryTaskManager.getEpicById(epic2.getId());
        InMemoryTaskManager.getEpicById(epic2.getId());
        InMemoryTaskManager.getSubtaskById(subtask1e1.getId());
        InMemoryTaskManager.getSubtaskById(subtask2e1.getId());
        printAllTasks(InMemoryTaskManager);
        InMemoryTaskManager.getSubtaskById(subtask1e1.getId());
        InMemoryTaskManager.getTaskById(task1.getId());
        InMemoryTaskManager.getEpicById(epic1.getId());
        //3
        printAllTasks(InMemoryTaskManager);

        //4
        InMemoryTaskManager.deleteTask(task1.getId());
        printAllTasks(InMemoryTaskManager);

        //5
        InMemoryTaskManager.deleteEpic(epic1.getId());
        printAllTasks(InMemoryTaskManager);




    }

}
