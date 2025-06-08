package br.com.gestaoPedidos.gestaoPedidos.controller.produtoController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestaoPedidos.gestaoPedidos.controller.produtoController.dto.ProdutoDTO;
import br.com.gestaoPedidos.gestaoPedidos.model.Produto;
import br.com.gestaoPedidos.gestaoPedidos.services.ProdutoService.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/")
@Tag(name = "Produtos", description = "Gerenciamento de produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @PostMapping(path = "/produtos")
    @Operation(summary = "Criar novo produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados do produto inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    public ResponseEntity<?> novoProduto(@RequestBody ProdutoDTO produtoDTO) {

        try {
            if (produtoDTO == null || produtoDTO.getDescricao() == null || produtoDTO.getDescricao().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados do produto inválidos");
            }
            Produto produto = new Produto();
            produto.setDescricao(produtoDTO.getDescricao());
            produto.setQuantidade(produtoDTO.getQuantidade());
            produto.setValor(produtoDTO.getValor());
            produtoService.salvar(produto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Produto criado com sucesso!");
    }

    @GetMapping(path = "/produtos")
    @Operation(summary = "Listar produtos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso"),
        @ApiResponse(responseCode = "204", description = "Nenhum produto cadastro"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    public ResponseEntity<?> retornaProdutos(){
        List<Produto> produtos = produtoService.findAll();
        List<ProdutoDTO> produtosDTO = new ArrayList<>();
        try {
            if(produtos.size() == 0){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sem produtos cadastrados");
            }
            for(Produto p : produtos){
                produtosDTO.add(new ProdutoDTO(
                    p.getUuid(),
                    p.getDescricao(),
                    p.getValor(),
                    p.getQuantidade()
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno");
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtosDTO);
    }

    @GetMapping(path = "/produtos/{uuid}")
    @Operation(summary = "Listar produtos por UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    public ResponseEntity<?> retornarProdutoPorUUID(@PathVariable(name = "uuid") UUID uuid) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        try {
            Produto produto = produtoService.findProdutoByUUID(uuid);
            produtoDTO = new ProdutoDTO(
                produto.getUuid(),
                produto.getDescricao(),
                produto.getValor(),
                produto.getQuantidade());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro");
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtoDTO);
    }

    @PutMapping(path = "/produtos/{uuid}")
    @Operation(summary = "Listar produtos por UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado!"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    public ResponseEntity<?> atualizarProduto(@PathVariable(name = "uuid") UUID uuid,
            @RequestBody ProdutoDTO produtoDTO) {

        try {
            Produto produto = produtoService.findProdutoByUUID(uuid);
            if (produto == null || produto.getId() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
            }
            produto.setDescricao(produtoDTO.getDescricao());
            produto.setQuantidade(produtoDTO.getQuantidade());
            produto.setValor(produtoDTO.getValor());
            produtoService.salvar(produto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro");
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtoDTO);
    }

    @DeleteMapping(path = "/produtos/{uuid}")
    @Operation(summary = "Listar produtos por UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "204", description = "Produto não encontrado!"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    public ResponseEntity<?> deletarCliente(@PathVariable(name = "uuid") UUID uuid) {
        try {
            Produto produto = produtoService.findProdutoByUUID(uuid);
            if(produto == null || produto.getId() == 0){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto não encontrado");
            }
            produtoService.delete(produto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Produto excluido com sucesso!");
    }
}
