package repository;

import model.Task;
import storage.IStorage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação do repositório que serializa/deserializa Task para linhas CSV.
 * Mantém um Map em memória (cache) e sempre persiste as alterações.
 */
public class CsvTaskRepository implements ITaskRepository {
    private final IStorage storage;               // dependência injetada
    private final Map<String, Task> map = new LinkedHashMap<>(); // preserva ordem de inserção

    /**
     * Construtor: injeta o storage e tenta carregar os dados existentes.
     */
    public CsvTaskRepository(IStorage storage) {
        this.storage = storage;
        try { load(); } catch (Exception e) { /* ignore on startup */ }
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void save(Task task) {
        map.put(task.getId(), task);
        try { persist(); } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void delete(String id) {
        map.remove(id);
        try { persist(); } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void load() throws Exception {
        List<String> lines = storage.readAll();
        map.clear();
        for (String l : lines) {
            if (l == null || l.isBlank() || l.startsWith("#")) continue; // ignora linhas vazias/comentários
            Task t = Task.fromCsv(l);
            if (t != null) map.put(t.getId(), t);
        }
    }

    @Override
    public void persist() throws Exception {
        List<String> lines = map.values().stream().map(Task::toCsv).collect(Collectors.toList());
        storage.writeAll(lines);
    }
}
