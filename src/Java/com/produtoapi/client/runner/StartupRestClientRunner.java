package Java.com.produtoapi.client.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import Java.com.produtoapi.client.model.Produto;

import java.util.Arrays;
import java.util.List;

@Component
public class StartupRestClientRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RestClient sendo executado");

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/produtos";

        Produto[] produtosArray = restTemplate.getForObject(url, Produto[].class);
        if (produtosArray != null) {
            List<Produto> produtos = Arrays.asList(produtosArray);
            produtos.forEach(produto ->
                    System.out.println("ID: " + produto.getId() +
                            " Nome: " + produto.getNome() +
                            " Preço: " + produto.getPreco()));
        } else {
            System.out.println("Nenhum produto encontrado.");
        }
    }
}
