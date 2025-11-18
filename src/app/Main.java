package app;

import controller.TaskController;
import repository.CsvTaskRepository;
import repository.ITaskRepository;
import service.TaskService;
import storage.CsvStorage;
import storage.IStorage;
import view.ConsoleView;

import java.nio.file.Paths;

/*
 * Ponto de entrada da aplicação ToDoList.
 * Cria instâncias dos componentes (Storage, Repository, Service, Controller, View)
 * e inicia a interface de console.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Inicializa o storage (Singleton) apontando para o arquivo CSV em 'data/tasks.csv'
            IStorage storage = CsvStorage.getInstance(Paths.get("data/tasks.csv"));

            // Repositório que usa o storage para persistência de objetos Task
            ITaskRepository repo = new CsvTaskRepository(storage);

            // Service (Façade) que oferece operações de alto nível sobre tarefas
            TaskService service = new TaskService(repo);

            // Controller encaminha chamadas da View para o Service
            TaskController controller = new TaskController(service);

            // View de console — interação com o usuário
            ConsoleView view = new ConsoleView(controller);

            // Inicia loop da interface
            view.start();
        } catch (Exception e) {
            System.err.println("Erro ao iniciar a aplicação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
