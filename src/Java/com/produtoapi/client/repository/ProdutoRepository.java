package Java.com.produtoapi.client.repository;

import Java.com.produtoapi.client.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
