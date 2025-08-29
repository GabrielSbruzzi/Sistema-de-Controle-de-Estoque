package Java.com.produtoapi.client.service;

import Java.com.produtoapi.client.model.MovimentacaoEstoque;
import Java.com.produtoapi.client.repository.MovimentacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository repository;

    public MovimentacaoService(MovimentacaoRepository repository) {
        this.repository = repository;
    }

    // Criar movimentação
    public MovimentacaoEstoque registrar(MovimentacaoEstoque mov) {
        return repository.save(mov);
    }

    // Listar todas
    public List<MovimentacaoEstoque> listarTodas() {
        return repository.findAll();
    }

    // Listar por produto
    public List<MovimentacaoEstoque> listarPorProduto(Long produtoId) {
        return repository.findByProdutoId(produtoId);
    }

    // Listar por tipo (Entrada / Saída)
    public List<MovimentacaoEstoque> listarPorTipo(String tipo) {
        return repository.findByTipoIgnoreCase(tipo);
    }

    // Listar por período
    public List<MovimentacaoEstoque> listarPorPeriodo(String inicio, String fim) {
        LocalDateTime[] range = resolvePeriodo(inicio, fim);
        return repository.findByDataBetween(range[0], range[1]);
    }

    // Resumo das movimentações
    public Map<String, Object> resumoMovimentacoes(String inicio, String fim) {
        LocalDateTime[] range = resolvePeriodo(inicio, fim);
        List<MovimentacaoEstoque> movs = repository.findByDataBetween(range[0], range[1]);

        int totalEntradas = movs.stream()
                .filter(m -> isEntrada(m.getTipo()))
                .mapToInt(MovimentacaoEstoque::getQuantidade)
                .sum();

        int totalSaidas = movs.stream()
                .filter(m -> isSaida(m.getTipo()))
                .mapToInt(MovimentacaoEstoque::getQuantidade)
                .sum();

        int saldo = totalEntradas - totalSaidas;

        // Evolução diária
        Map<String, Integer> porDia = new LinkedHashMap<>();
        movs.stream()
                .sorted(Comparator.comparing(MovimentacaoEstoque::getData))
                .forEach(m -> {
                    String dia = m.getData().toLocalDate().toString();
                    int delta = isSaida(m.getTipo()) ? -m.getQuantidade() : m.getQuantidade();
                    porDia.merge(dia, delta, Integer::sum);
                });

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("inicio", range[0].toString());
        out.put("fim", range[1].toString());
        out.put("totalMovimentacoes", movs.size());
        out.put("totalEntradas", totalEntradas);
        out.put("totalSaidas", totalSaidas);
        out.put("saldo", saldo);
        out.put("porDia", porDia);

        return out;
    }

    // Paginação
    public Page<MovimentacaoEstoque> listarPaginado(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    // ===== Helpers =====

    private boolean isEntrada(String tipo) {
        return tipo != null && tipo.trim().equalsIgnoreCase("entrada");
    }

    private boolean isSaida(String tipo) {
        return tipo != null && (
                tipo.trim().equalsIgnoreCase("saída") ||
                        tipo.trim().equalsIgnoreCase("saida") // sem acento
        );
    }

    private LocalDateTime[] resolvePeriodo(String inicio, String fim) {
        LocalDateTime start = parseDateOrDateTime(inicio, true);
        LocalDateTime end = parseDateOrDateTime(fim, false);
        return new LocalDateTime[]{start, end};
    }

    private LocalDateTime parseDateOrDateTime(String s, boolean startOfDay) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("Parâmetro de data inválido");
        }
        try {
            return LocalDateTime.parse(s);
        } catch (Exception ignored) {}
        try {
            LocalDate d = LocalDate.parse(s);
            return startOfDay ? d.atStartOfDay() : d.atTime(LocalTime.MAX);
        } catch (Exception ignored) {}
        try {
            DateTimeFormatter br = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate d = LocalDate.parse(s, br);
            return startOfDay ? d.atStartOfDay() : d.atTime(LocalTime.MAX);
        } catch (Exception ignored) {}
        throw new IllegalArgumentException("Formato de data inválido: " + s);
    }
}
