package Java.com.produtoapi.client.repository;

import Java.com.produtoapi.client.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Buscar produtos que contenham parte do nome (case insensitive)
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // Buscar produtos com estoque abaixo de um valor definido
    List<Produto> findByQuantidadeLessThan(int limite);
}
