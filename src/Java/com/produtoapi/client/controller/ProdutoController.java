package Java.com.produtoapi.client.controller;

import Java.com.produtoapi.client.model.Produto;
import Java.com.produtoapi.client.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @PostMapping
    public Produto salvar(@RequestBody Produto produto) {
        return produtoService.salvar(produto);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        return produtoService.atualizar(id, produto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        produtoService.deletar(id);
    }

    @GetMapping("/{id}")
    public Optional<Produto> findById(@PathVariable Long id) {
        return produtoService.findById(id);
    }

    @PostMapping("/salvarLista")
    public List<Produto> salvarLista(@RequestBody List<Produto> produtos) {
        return produtoService.salvarLista(produtos);
    }

    @GetMapping("/buscar")
    public List<Produto> buscarPorNome(@RequestParam String nome) {
        return produtoService.buscarPorNome(nome);
    }

    @GetMapping("/estoque-baixo")
    public List<Produto> listarEstoqueBaixo(@RequestParam(defaultValue = "10") int limite) {
        return produtoService.listarEstoqueBaixo(limite);
    }

    @GetMapping("/paginado")
    public Page<Produto> listarPaginado(@RequestParam int page, @RequestParam int size) {
        return produtoService.listarPaginado(page, size);
    }

    @GetMapping("/exportar")
    public ResponseEntity<Resource> exportarProdutos() {
        return produtoService.exportarProdutos();
    }
}
