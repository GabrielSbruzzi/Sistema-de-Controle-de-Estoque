package Java.com.produtoapi.client.service;

import Java.com.produtoapi.client.model.Produto;
import Java.com.produtoapi.client.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> salvarLista(List<Produto> produtos) {
        return produtoRepository.saveAll(produtos);
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    public Produto atualizar(Long id, Produto produto) {
        if (produtoRepository.existsById(id)) {
            produto.setId((long) Math.toIntExact(id)); // Corrigido para Long
            return produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Produto> listarEstoqueBaixo(int limite) {
        return produtoRepository.findByQuantidadeLessThan(limite);
    }

    public Page<Produto> listarPaginado(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    // Sobrecarga para paginação simples com page e size
    public Page<Produto> listarPaginado(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return produtoRepository.findAll(pageable);
    }

    // Exportar produtos como CSV ou similar
    public ResponseEntity<Resource> exportarProdutos() {
        try {
            List<Produto> produtos = produtoRepository.findAll();
            StringBuilder csv = new StringBuilder();
            csv.append("ID,Nome,Descricao,Quantidade,Preco\n");
            for (Produto p : produtos) {
                csv.append(p.getId()).append(",")
                        .append(p.getNome()).append(",")
                        .append(p.getDescricao()).append(",")
                        .append(p.getQuantidade()).append(",")
                        .append(p.getPreco()).append("\n");
            }
            ByteArrayResource resource = new ByteArrayResource(csv.toString().getBytes());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=produtos.csv")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao exportar produtos", e);
        }
    }
}
