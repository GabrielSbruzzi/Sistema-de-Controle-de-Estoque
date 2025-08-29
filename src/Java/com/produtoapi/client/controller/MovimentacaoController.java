package Java.com.produtoapi.client.controller;

import Java.com.produtoapi.client.model.MovimentacaoEstoque;
import Java.com.produtoapi.client.service.MovimentacaoService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*") // Libera acesso ao frontend
@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService service;

    public MovimentacaoController(MovimentacaoService service) {
        this.service = service;
    }

    // Criar movimentação
    @PostMapping
    public MovimentacaoEstoque registrar(@RequestBody MovimentacaoEstoque mov) {
        return service.registrar(mov);
    }

    // Listar todas
    @GetMapping
    public List<MovimentacaoEstoque> listarTodas() {
        return service.listarTodas();
    }

    // Listar por produto
    @GetMapping("/{produtoId}")
    public List<MovimentacaoEstoque> listarPorProduto(@PathVariable Long produtoId) {
        return service.listarPorProduto(produtoId);
    }

    // Listar por tipo (ENTRADA / SAIDA)
    @GetMapping("/tipo/{tipo}")
    public List<MovimentacaoEstoque> listarPorTipo(@PathVariable String tipo) {
        return service.listarPorTipo(tipo);
    }

    // Listar por período (inicio, fim)
    @GetMapping("/periodo")
    public List<MovimentacaoEstoque> listarPorPeriodo(
            @RequestParam String inicio,
            @RequestParam String fim
    ) {
        return service.listarPorPeriodo(inicio, fim);
    }

    // Resumo das movimentações no período
    @GetMapping("/resumo")
    public Map<String, Object> resumoMovimentacoes(
            @RequestParam String inicio,
            @RequestParam String fim
    ) {
        return service.resumoMovimentacoes(inicio, fim);
    }

    // Paginação
    @GetMapping("/paginado")
    public Page<MovimentacaoEstoque> listarPaginado(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return service.listarPaginado(page, size);
    }
}
