package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/*
 * Representa uma tarefa do sistema.
 * Exibe encapsulamento (campos privados e getters/setters) e métodos
 * para conversão para/desde CSV (toCsv/fromCsv).
 */
public class Task {
    // Campos privados — encapsulamento
    private String id;                // identificador único (UUID)
    private String title;             // título (não nulo)
    private String description;       // descrição livre
    private LocalDate dueDate;        // data de vencimento (pode ser null)
    private int priority;             // prioridade: 1 alta, 2 média, 3 baixa
    private TaskStatus status;        // enum PENDENTE/CONCLUIDA

    private static final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

    /*
     * Construtor mínimo: gera um id e define valores padrão.
     */
    public Task(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = "";
        this.priority = 2;
        this.status = TaskStatus.PENDENTE;
    }

    /*
     * Construtor completo usado ao carregar do CSV.
     */
    public Task(String id, String title, String description, LocalDate dueDate, int priority, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    // === Getters e Setters (encapsulamento) ===
    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { if (title != null && !title.isBlank()) this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    /*
     * Converte o objeto Task para uma linha CSV (campos separados por ';').
     * Observação: implementa um escape simples para evitar conflitos com o separador.
     */
    public String toCsv() {
        // formato: id;title;description;dueDate;priority;status
        String due = (dueDate == null) ? "" : dueDate.format(fmt);
        return escape(id) + ";" + escape(title) + ";" + escape(description) + ";" + due + ";" + priority + ";" + status.name();
    }

    /*
     * Constrói um Task a partir de uma linha CSV. Retorna null se a linha for inválida.
     */
    public static Task fromCsv(String line) {
        String[] parts = line.split(";", -1); // -1 preserva campos vazios
        if (parts.length < 6) return null;
        String id = unescape(parts[0]);
        String title = unescape(parts[1]);
        String description = unescape(parts[2]);
        LocalDate due = parts[3].isBlank() ? null : LocalDate.parse(parts[3], fmt);
        int priority = Integer.parseInt(parts[4]);
        TaskStatus status = TaskStatus.valueOf(parts[5]);
        return new Task(id, title, description, due, priority, status);
    }

    // Escape básico: substitui ';' por '\;' e barras por '\\'
    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace(";", "\\;");
    }

    private static String unescape(String s) {
        if (s == null) return "";
        // Reverte a ordem de escape: primeiro \; -> ; e \\ -> \\.
        // Nota: esta substituição simples funciona com o escape usado acima.
        return s.replace("\\;", ";").replace("\\\\", "\\");
    }

    @Override
    public String toString() {
        String due = (dueDate == null) ? "-" : dueDate.format(fmt);
        return String.format("[%s] %s (prio=%d) - %s - due=%s", id, title, priority, status, due);
    }
}
