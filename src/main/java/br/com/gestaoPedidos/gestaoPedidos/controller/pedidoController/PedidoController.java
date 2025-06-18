package br.com.gestaoPedidos.gestaoPedidos.controller.pedidoController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestaoPedidos.gestaoPedidos.controller.clienteController.dto.ClienteDTO;
import br.com.gestaoPedidos.gestaoPedidos.controller.pedidoController.dto.PedidoDTO;
import br.com.gestaoPedidos.gestaoPedidos.controller.pedidoController.dto.retornoPedidos.ProdutoPedidoDTO;
import br.com.gestaoPedidos.gestaoPedidos.controller.pedidoController.dto.retornoPedidos.RetornoPedidosDTO;
import br.com.gestaoPedidos.gestaoPedidos.controller.produtoController.dto.ProdutoDTO;
import br.com.gestaoPedidos.gestaoPedidos.model.Cliente;
import br.com.gestaoPedidos.gestaoPedidos.model.Pedido;
import br.com.gestaoPedidos.gestaoPedidos.model.PedidosProdutos;
import br.com.gestaoPedidos.gestaoPedidos.model.Produto;
import br.com.gestaoPedidos.gestaoPedidos.services.ProdutoService.ProdutoService;
import br.com.gestaoPedidos.gestaoPedidos.services.clienteService.ClienteService;
import br.com.gestaoPedidos.gestaoPedidos.services.pedidoService.PedidoProdutoService;
import br.com.gestaoPedidos.gestaoPedidos.services.pedidoService.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/")
@CrossOrigin(origins = "*")
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos")
public class PedidoController {
    
    @Autowired
    PedidoService pedidoService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    ProdutoService produtoService;
    @Autowired
    PedidoProdutoService pedidoProdutoService;

    @PostMapping(path = "/pedidos")
    @Operation(summary = "Criar um novo pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida: lista de itens vazia, produto não encontrado ou produto duplicado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    public ResponseEntity<?> novoProduto(@RequestBody PedidoDTO pedidoDTO) {

        try {
            
            Pedido pedido = new Pedido();
            Cliente cliente = new Cliente();
            BigDecimal total = new BigDecimal(0);

            cliente = clienteService.findClienteByUUID(pedidoDTO.getCliente().getUuid());

            if (cliente == null || cliente.getId() == null || cliente.getId() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não cadastrado!");
            }

            if (pedidoDTO.getItems() == null || pedidoDTO.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("Lista de produtos vazia!");
            }
            List<Produto> produtos = new ArrayList<>();
            List<PedidosProdutos> pedidosProdutos = new ArrayList<>();
            List<ProdutoDTO> items = pedidoDTO.getItems();
            
            for(ProdutoDTO poDto : items){
                Produto produto = produtoService.findProdutoByUUID(poDto.getUuid());
                PedidosProdutos pedidoProduto = new PedidosProdutos();

                if(produto == null || produto.getId() == 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto não encontrado!\n" + poDto);
                }

                boolean produtoDuplicado = pedidosProdutos.stream()
                        .anyMatch(item -> item.getProduto().getId().equals(produto.getId()));
                        
                if(produtoDuplicado){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto duplicado!\n" + poDto.getDescricao());
                }
                
                total = new BigDecimal(total.doubleValue() + (poDto.getValor().doubleValue() * poDto.getQuantidade()));
                produtos.add(produto);
                pedidoProduto.setPedido(pedido);
                pedidoProduto.setProduto(produto);
                pedidoProduto.setQuantidade(produto.getQuantidade());
                pedidoProduto.setTotalItem(total);
                pedidoProduto.setValorUnitario(poDto.getValor());
                pedidosProdutos.add(pedidoProduto);
            }

            pedido.setCliente(cliente);
            pedido.setTipo(pedidoDTO.getTipo());
            pedido.setTotal(total);
            pedido.setItems(pedidosProdutos);
            
            pedidoService.salvar(pedido);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido criado com sucesso!");
    }

    
    @GetMapping(path = "/pedidos")
    @Operation(summary = "Listar todos os pedidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Nenhum pedido encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    public ResponseEntity<?> retornaPedidos(){
        List<Pedido> pedidos = pedidoService.findAll();
        List<RetornoPedidosDTO> listRetornoPedidosDTO = new ArrayList<>();
        
        try {
            if(pedidos.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(pedidos);
            }
            for(Pedido pedido : pedidos){

                RetornoPedidosDTO retornoPedidosDTO = new RetornoPedidosDTO();
                for(PedidosProdutos items : pedido.getItems()){

                    ProdutoPedidoDTO item = new ProdutoPedidoDTO();
                    item.setDescricao(items.getProduto().getDescricao());
                    item.setId(items.getProduto().getId());
                    item.setQuantidade(items.getQuantidade());
                    item.setTotal(items.getTotalItem());
                    item.setValor(items.getValorUnitario());
                    
                    retornoPedidosDTO.addItems(item);
                }
                
                retornoPedidosDTO.setCliente(new ClienteDTO(
                    pedido.getCliente().getUuid(),    
                    pedido.getCliente().getId(),
                    pedido.getCliente().getNome())
                );
                retornoPedidosDTO.setUuid(pedido.getUuid());
                retornoPedidosDTO.setTotal(pedido.getTotal());
                retornoPedidosDTO.setTipo(pedido.getTipo());
                retornoPedidosDTO.setCodigo(pedido.getCodigo());
                
                
                listRetornoPedidosDTO.add(retornoPedidosDTO);
            }    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno");
        }

        return ResponseEntity.status(HttpStatus.OK).body(listRetornoPedidosDTO);
    }

    @GetMapping(path = "/pedidos/{tipo}/tipos")
    @Operation(summary = "Listar pedidos por tipo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
        @ApiResponse(responseCode = "204", description = "Nenhum pedido encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    public ResponseEntity<?> retornaPedidosPorTipo(@PathVariable(name = "tipo") String tipo){
        List<Pedido> pedidos = pedidoService.retornaPedidosPorTipo(tipo);
        List<RetornoPedidosDTO> listRetornoPedidosDTO = new ArrayList<>();
        
        try {
            if(pedidos.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(pedidos);
            }
            for(Pedido pedido : pedidos){
                
                RetornoPedidosDTO retornoPedidosDTO = new RetornoPedidosDTO();
                for(PedidosProdutos items : pedido.getItems()){

                    ProdutoPedidoDTO item = new ProdutoPedidoDTO();
                    item.setDescricao(items.getProduto().getDescricao());
                    item.setId(items.getProduto().getId());
                    item.setQuantidade(items.getQuantidade());
                    item.setTotal(items.getTotalItem());
                    item.setValor(items.getValorUnitario());
                    
                    retornoPedidosDTO.addItems(item);
                }
                
                retornoPedidosDTO.setCliente(new ClienteDTO(
                    pedido.getCliente().getUuid(),    
                    pedido.getCliente().getId(),
                    pedido.getCliente().getNome())
                );
                retornoPedidosDTO.setUuid(pedido.getUuid());
                retornoPedidosDTO.setTotal(pedido.getTotal());
                retornoPedidosDTO.setTipo(pedido.getTipo());
                retornoPedidosDTO.setCodigo(pedido.getCodigo());
                
                
                listRetornoPedidosDTO.add(retornoPedidosDTO);
            }    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno");
        }

        return ResponseEntity.status(HttpStatus.OK).body(listRetornoPedidosDTO);
    }

    @GetMapping(path = "/pedidos/{uuid}/clientes")
    @Operation(summary = "Listar pedidos por UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
        @ApiResponse(responseCode = "204", description = "Nenhum cliente ou pedido encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    public ResponseEntity<?> retornaPedidosPorCliente(@PathVariable(name = "uuid") UUID uuid){
        Cliente cliente = clienteService.findClienteByUUID(uuid);

        if(cliente == null || cliente.getId() == 0){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente não encontrado!");
        }
        List<Pedido> pedidos = pedidoService.retornaPedidosPorCliente(cliente.getId());
        List<RetornoPedidosDTO> listRetornoPedidosDTO = new ArrayList<>();
        
        try {
            if(pedidos.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(pedidos);
            }
            for(Pedido pedido : pedidos){
                
                RetornoPedidosDTO retornoPedidosDTO = new RetornoPedidosDTO();
                for(PedidosProdutos items : pedido.getItems()){

                    ProdutoPedidoDTO item = new ProdutoPedidoDTO();
                    item.setDescricao(items.getProduto().getDescricao());
                    item.setId(items.getProduto().getId());
                    item.setQuantidade(items.getQuantidade());
                    item.setTotal(items.getTotalItem());
                    item.setValor(items.getValorUnitario());
                    
                    retornoPedidosDTO.addItems(item);
                }
                
                retornoPedidosDTO.setCliente(new ClienteDTO(
                    pedido.getCliente().getUuid(),    
                    pedido.getCliente().getId(),
                    pedido.getCliente().getNome())
                );
                retornoPedidosDTO.setUuid(pedido.getUuid());
                retornoPedidosDTO.setTotal(pedido.getTotal());
                retornoPedidosDTO.setTipo(pedido.getTipo());
                retornoPedidosDTO.setCodigo(pedido.getCodigo());
                
                
                listRetornoPedidosDTO.add(retornoPedidosDTO);
            }    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno");
        }

        return ResponseEntity.status(HttpStatus.OK).body(listRetornoPedidosDTO);
    }

    @GetMapping(path = "/pedidos/{uuid}/items")
    @Operation(summary = "Listar somente os itens do pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
        @ApiResponse(responseCode = "204", description = "Nenhum pedido encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    public ResponseEntity<?> retornaProdutosPedido(@PathVariable(name = "uuid") UUID uuid){
        
        List<PedidosProdutos> pedidosProdutos = pedidoProdutoService.retornaPedidosProdutosPorUUIDPedido(uuid);
        List<ProdutoPedidoDTO> listProdutos = new ArrayList<>();

        try {
            if(pedidosProdutos.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produtos não encontrados");
            }
            
            for(PedidosProdutos pedidoProduto : pedidosProdutos){
                
                ProdutoPedidoDTO produto = new ProdutoPedidoDTO();

                produto.setDescricao(pedidoProduto.getProduto().getDescricao());
                produto.setId(pedidoProduto.getProduto().getId());
                produto.setQuantidade(pedidoProduto.getQuantidade());
                produto.setValor(pedidoProduto.getValorUnitario());
                    
                listProdutos.add(produto);
            }    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno");
        }

        return ResponseEntity.status(HttpStatus.OK).body(listProdutos);
    }
    
}
