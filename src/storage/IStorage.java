package storage;

import java.io.IOException;
import java.util.List;

/*
 * Interface que abstrai a leitura e escrita de linhas (persistência bruta).
 * Permite trocar a implementação (por ex., CSV, banco de dados) sem alterar
 * o repositório.
 */
public interface IStorage {
    java.util.List<String> readAll() throws IOException;
    void writeAll(java.util.List<String> lines) throws IOException;
}
