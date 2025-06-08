package br.com.gestaoPedidos.gestaoPedidos.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Minha API")
                        .description("API para gerenciamento de pedidos, produtos e clientes. " +
                                "Permite operações CRUD em clientes e produtos, além de " + 
                                "gerenciar pedidos com múltiplos itens.\n\n" +
                                "Principais funcionalidades:\n" +
                                "* Cadastro e gestão de clientes\n" +
                                "* Cadastro e gestão de produtos\n" +
                                "* Criação de pedidos\n" +
                                "* Consulta de pedidos por cliente\n" +
                                "* Consulta de pedidos por tipo\n" +
                                "* Gestão de itens do pedido")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("João Elias")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desenvolvimento")));
    }
}
