package Java.com.produtoapi.client.service;

import Java.com.produtoapi.client.model.MovimentacaoEstoque;
import Java.com.produtoapi.client.repository.MovimentacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository repository;

    public MovimentacaoService(MovimentacaoRepository repository) {
        this.repository = repository;
    }

    public MovimentacaoEstoque registrar(MovimentacaoEstoque mov) {
        return repository.save(mov);
    }

    public List<MovimentacaoEstoque> listarTodas() {
        return repository.findAll();
    }

    public List<MovimentacaoEstoque> listarPorProduto(Long produtoId) {
        return repository.findByProdutoId(produtoId);
    }
}
