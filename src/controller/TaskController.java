package controller;

import model.Task;
import service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
 * Controller é a ponte entre a View e o Service.
 * Recebe parâmetros vindos da View, realiza conversões mínimas e chama o Service.
 */
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) { this.service = service; }

    public List<Task> listTasks() { return service.getAllTasks(); }

    public Task createTask(String title, String desc, LocalDate due, int priority) { return service.createTask(title, desc, due, priority); }

    public boolean complete(String id) { return service.completeTask(id); }

    public boolean delete(String id) { return service.deleteTask(id); }
}
