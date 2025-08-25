package Java.com.produtoapi.client.controller;

import Java.com.produtoapi.client.model.MovimentacaoEstoque;
import Java.com.produtoapi.client.service.MovimentacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Libera acesso pro frontend
@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService service;

    public MovimentacaoController(MovimentacaoService service) {
        this.service = service;
    }

    @PostMapping
    public MovimentacaoEstoque registrar(@RequestBody MovimentacaoEstoque mov) {
        return service.registrar(mov);
    }

    @GetMapping
    public List<MovimentacaoEstoque> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{produtoId}")
    public List<MovimentacaoEstoque> listarPorProduto(@PathVariable Long produtoId) {
        return service.listarPorProduto(produtoId);
    }
}
