package Java.com.produtoapi.client.repository;

import Java.com.produtoapi.client.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEstoque, Long> {

    // Buscar movimentações por produto
    List<MovimentacaoEstoque> findByProdutoId(Long produtoId);

    // Buscar movimentações por tipo (Entrada ou Saída)
    List<MovimentacaoEstoque> findByTipo(String tipo);

    // Buscar movimentações ignorando maiúsculas/minúsculas
    List<MovimentacaoEstoque> findByTipoIgnoreCase(String tipo);

    // Buscar movimentações de um produto ordenadas pela data decrescente
    List<MovimentacaoEstoque> findByProdutoIdOrderByDataDesc(Long produtoId);

    // Buscar movimentações dentro de um período
    List<MovimentacaoEstoque> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);
}
