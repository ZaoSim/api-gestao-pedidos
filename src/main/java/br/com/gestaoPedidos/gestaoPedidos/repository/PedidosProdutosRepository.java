package br.com.gestaoPedidos.gestaoPedidos.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gestaoPedidos.gestaoPedidos.model.PedidosProdutos;

@Repository
public interface PedidosProdutosRepository extends JpaRepository<PedidosProdutos, Long>{
    
    @Query("SELECT pedidosProdutos FROM Pedido pedidosProdutos WHERE uuid = :uuid")
    public PedidosProdutos findPedidosProdutosByUUID(@RequestParam("uuid") UUID uuid);
    
    @Query("SELECT pedidosProdutos FROM PedidosProdutos pedidosProdutos "
        + " INNER JOIN Pedido pedido ON pedido.codigo = pedidosProdutos.pedido.codigo "
        + " WHERE pedido.uuid = :uuid")
    public List<PedidosProdutos> retornaPedidosProdutosPorUUIDPedido(@RequestParam("uuid") UUID uuid);
}
