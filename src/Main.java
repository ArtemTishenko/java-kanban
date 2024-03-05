public class Main {

    public static void main(String[] args) {
        TaskManager TaskManager = new TaskManager();
        System.out.println("-CREATE-");
        Task task1 = new Task("Task1", "Description task 1");
        Task task2 = new Task("Task2", "Description task 2");
        TaskManager.createTask(task1);
        TaskManager.createTask(task2);
        Epic epic1 = new Epic("Epic 1", "Description epic 1");
        Epic epic2 = new Epic("Epic 2", "Description epic 2");
        TaskManager.createEpic(epic1);
        TaskManager.createEpic(epic2);
        Subtask subtask1e1 = new Subtask("Subtask 1 epic1", "Description subtask 1ep1", epic1.getId());
        Subtask subtask2e1 = new Subtask("Subtask 2 epic1", "Description subtask 2ep1", epic1.getId());
        Subtask subtask3e2 = new Subtask("Subtask 3 epic2", "Description subtask 3ep2", epic2.getId());
        TaskManager.createSubtask(subtask1e1);
        TaskManager.createSubtask(subtask2e1);
        TaskManager.createSubtask(subtask3e2);

        System.out.println(TaskManager.getAllTasks());
        System.out.println(TaskManager.getAllEpics());
        System.out.println(TaskManager.getAllSubtasks());

        System.out.println("-UPDATE-");
        task1.setStatus(Status.IN_PROGRESS);
        TaskManager.updateTask(task1);
        task2.setStatus(Status.DONE);
        TaskManager.updateTask(task2);
        System.out.println(TaskManager.getAllTasks());
        subtask1e1.setStatus(Status.IN_PROGRESS);
        TaskManager.updateSubtask(subtask1e1);
        subtask2e1.setStatus(Status.DONE);
        TaskManager.updateSubtask(subtask2e1);
        subtask3e2.setStatus(Status.DONE);
        TaskManager.updateSubtask(subtask3e2);

        System.out.println(TaskManager.getAllSubtasks());
        System.out.println(TaskManager.getAllEpics());

        System.out.println("-DELETE-");
        TaskManager.deleteTask(task2.getId());
        System.out.println(TaskManager.getAllTasks());
        TaskManager.deleteSubtask(subtask1e1.getId());
        System.out.println(TaskManager.getAllSubtasks());
        TaskManager.deleteEpic(epic2.getId());
        System.out.println(TaskManager.getAllSubtasks());
        System.out.println(TaskManager.getAllEpics());

    }
}
