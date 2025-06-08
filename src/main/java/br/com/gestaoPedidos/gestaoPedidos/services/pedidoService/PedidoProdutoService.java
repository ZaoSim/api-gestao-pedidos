package br.com.gestaoPedidos.gestaoPedidos.services.pedidoService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestaoPedidos.gestaoPedidos.model.PedidosProdutos;
import br.com.gestaoPedidos.gestaoPedidos.repository.PedidosProdutosRepository;

@Service
public class PedidoProdutoService {
    
    @Autowired
    private PedidosProdutosRepository repository;

    public PedidosProdutos salvar(PedidosProdutos pedidosProdutos) {
        return repository.save(pedidosProdutos);
    }
    
    public List<PedidosProdutos> findAll(){
        return repository.findAll();
    }

    public PedidosProdutos findPedidoByUUID(UUID uuid){
        return repository.findPedidosProdutosByUUID(uuid);
    }

    public void delete(PedidosProdutos pedidosProdutos){
        repository.delete(pedidosProdutos);
    }

    public List<PedidosProdutos> retornaPedidosProdutosPorUUIDPedido(UUID uuid){
        return repository.retornaPedidosProdutosPorUUIDPedido(uuid);
    }
}
