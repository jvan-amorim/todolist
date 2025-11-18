# ToDoList
Projeto de disciplina de Programação Orientada a Objetos, utilizando Java.

Sistema de Gerenciamento de Tarefas (Java, OO, MVC, CSV, Singleton, Façade).

## Estrutura
```
ToDoList/
├─ src/
│  └─ ...              # código-fonte
├─ data/
│  └─ tasks.csv        # arquivo de dados (opcional no Git)
├─ diagrams/
│  └─ uml.png          # diagrama UML
├─ README.md
└─ .gitignore
```

## Diagrama UML
O arquivo do diagrama está em `diagrams/uml.png`.

## Padrões usados
- **Singleton**: `storage.CsvStorage` (única instância para acesso ao arquivo CSV).
- **Façade**: `service.TaskService` (operações de alto nível expostas ao controller/view).
- **MVC**: `view.ConsoleView` -> `controller.TaskController` -> `service.TaskService` -> `repository.CsvTaskRepository` -> `storage.CsvStorage`.

## Como usar
1. Importe o projeto no Eclipse como Java Project.
2. Crie a pasta `data` na raiz do projeto (se não existir).
3. Execute `app.Main` (Run As > Java Application).
4. Use o menu no console.
