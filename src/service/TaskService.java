package service;

import model.Task;
import model.TaskStatus;
import repository.ITaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
 * Façade que oferece operações de alto nível para gerenciamento de tarefas.
 * Centraliza regras de negócio simples (validações, manipulação de status)
 * e delega persistência para o repositório.
 */
public class TaskService {
    private final ITaskRepository repo;

    public TaskService(ITaskRepository repo) {
        this.repo = repo;
    }

    /*
     * Retorna todas as tarefas cadastradas (em memória).
     */
    public List<Task> getAllTasks() { return repo.findAll(); }

    /*
     * Cria uma nova tarefa com os dados fornecidos. Define valores padrão e delega
     * a persistência ao repositório.
     */
    public Task createTask(String title, String description, LocalDate dueDate, int priority) {
        Task t = new Task(title);
        t.setDescription(description);
        t.setDueDate(dueDate);
        t.setPriority(priority);
        repo.save(t);
        return t;
    }

    /*
     * Marca a tarefa identificada por id como CONCLUIDA. Retorna true se a tarefa
     * existir e for atualizada.
     */
    public boolean completeTask(String id) {
        Optional<Task> ot = repo.findById(id);
        if (ot.isEmpty()) return false;
        Task t = ot.get();
        t.setStatus(TaskStatus.CONCLUIDA);
        repo.save(t);
        return true;
    }

    /*
     * Remove a tarefa com o id fornecido. Retorna true se a remoção ocorreu.
     */
    public boolean deleteTask(String id) {
        Optional<Task> ot = repo.findById(id);
        if (ot.isEmpty()) return false;
        repo.delete(id);
        return true;
    }
}
