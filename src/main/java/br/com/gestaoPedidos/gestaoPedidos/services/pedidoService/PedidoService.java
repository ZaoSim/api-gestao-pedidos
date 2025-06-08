package br.com.gestaoPedidos.gestaoPedidos.services.pedidoService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestaoPedidos.gestaoPedidos.model.Pedido;
import br.com.gestaoPedidos.gestaoPedidos.repository.PedidoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository repository;

    public Pedido salvar(Pedido pedido) {
        return repository.save(pedido);
    }
    
    public List<Pedido> findAll(){
        return repository.findAll();
    }

    public Pedido findPedidoByUUID(UUID uuid){
        return repository.findPedidoByUUID(uuid);
    }

    public void delete(Pedido pedido){
        repository.deleteById(pedido.getCodigo());
    }

    public List<Pedido> retornaPedidosPorTipo(String tipo){
        return repository.retornaPedidosPorTipo(tipo);
    }

    public List<Pedido> retornaPedidosPorCliente(Long id){
        return repository.retornaPedidosPorCliente(id);
    }
}
