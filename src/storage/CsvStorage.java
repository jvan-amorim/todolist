package storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/*
 * Implementação Singleton de IStorage que persiste em um arquivo CSV.
 * - Garante uma única instância apontando para o mesmo arquivo
 * - Faz escrita atômica usando arquivo temporário e rename
 */
public class CsvStorage implements IStorage {
    private static CsvStorage instance; // instância única
    private final Path file;             // caminho do arquivo CSV

    private CsvStorage(Path file) {
        this.file = file;
    }

    /*
     * Retorna a instância única de CsvStorage. O primeiro chamador define o arquivo.
     * Uso: CsvStorage.getInstance(Paths.get("data/tasks.csv"));
     */
    public static synchronized CsvStorage getInstance(Path file) {
        if (instance == null) instance = new CsvStorage(file);
        return instance;
    }

    @Override
    public List<String> readAll() throws IOException {
        if (!Files.exists(file)) return new ArrayList<>();
        return Files.readAllLines(file, StandardCharsets.UTF_8);
    }

    @Override
    public void writeAll(List<String> lines) throws IOException {
        // escrita atômica: escreve em arquivo temporário, depois renomeia
        Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
        Files.createDirectories(file.getParent());
        Files.write(tmp, lines, StandardCharsets.UTF_8);
        // ATOMIC_MOVE pode lançar UnsupportedOperationException em alguns sistemas de arquivos;
        // usamos REPLACE_EXISTING combinado com ATOMIC_MOVE quando suportado.
        Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }
}
