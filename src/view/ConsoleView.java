package view;

import controller.TaskController;
import model.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/*
 * Implementação simples de View que utiliza o console (terminal) para interação.
 * Esta classe **não** realiza lógica de negócio — apenas coleta entradas e mostra saídas.
 */
public class ConsoleView {
    private final TaskController controller;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleView(TaskController controller) { this.controller = controller; }

    /*
     * Loop principal da interface de console: apresenta um menu e trata opções.
     */
    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1": create(); break;
                case "2": list(); break;
                case "3": complete(); break;
                case "4": delete(); break;
                case "0": running = false; break;
                default: System.out.println("Opção inválida");
            }
        }
        System.out.println("Saindo...");
    }

    private void showMenu() {
        System.out.println("\n=== ToDoList ===");
        System.out.println("1) Criar tarefa");
        System.out.println("2) Listar tarefas");
        System.out.println("3) Marcar concluída");
        System.out.println("4) Remover tarefa");
        System.out.println("0) Sair");
        System.out.print("Escolha: ");
    }

    private void create() {
        System.out.print("Título: ");
        String t = sc.nextLine();
        System.out.print("Descrição: ");
        String d = sc.nextLine();
        System.out.print("Data venc. (YYYY-MM-DD) ou vazio: ");
        String dd = sc.nextLine().trim();
        LocalDate due = dd.isBlank() ? null : LocalDate.parse(dd);
        System.out.print("Prioridade (1-3): ");
        int p = 2;
        try { p = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { /* usa padrão */ }
        Task task = controller.createTask(t, d, due, p);
        System.out.println("Criada: " + task.getId());
    }

    private void list() {
        List<Task> tasks = controller.listTasks();
        if (tasks.isEmpty()) System.out.println("Nenhuma tarefa.");
        for (Task t : tasks) System.out.println(t.toString());
    }

    private void complete() {
        System.out.print("ID da tarefa: ");
        String id = sc.nextLine().trim();
        boolean ok = controller.complete(id);
        System.out.println(ok ? "Marcada como concluída." : "Tarefa não encontrada.");
    }

    private void delete() {
        System.out.print("ID da tarefa: ");
        String id = sc.nextLine().trim();
        boolean ok = controller.delete(id);
        System.out.println(ok ? "Removida." : "Tarefa não encontrada.");
    }
}
