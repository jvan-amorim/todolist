package repository;

import model.Task;
import java.util.List;
import java.util.Optional;

/*
 * Interface de repositório no nível de objetos Task.
 * Define operações básicas de CRUD e métodos para carregar/persistir dados.
 */
public interface ITaskRepository {
    List<Task> findAll();
    Optional<Task> findById(String id);
    void save(Task task);
    void delete(String id);
    void load() throws Exception;     // carrega do storage
    void persist() throws Exception;  // persiste no storage
}
