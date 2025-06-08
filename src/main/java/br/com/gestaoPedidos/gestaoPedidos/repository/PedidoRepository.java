package br.com.gestaoPedidos.gestaoPedidos.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gestaoPedidos.gestaoPedidos.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
    @Query("SELECT pedido FROM Pedido pedido WHERE uuid = :uuid")
    public Pedido findPedidoByUUID(@RequestParam("uuid") UUID uuid);

    @Query("SELECT pedido FROM Pedido pedido WHERE LOWER(pedido.tipo) = LOWER(:tipo) ")
    public List<Pedido> retornaPedidosPorTipo(@RequestParam("tipo") String tipo);

    @Query("SELECT pedido FROM Pedido pedido "
        + " INNER JOIN Cliente cliente ON pedido.cliente = cliente "
        + " WHERE cliente.id = :id")
    public List<Pedido> retornaPedidosPorCliente(@RequestParam("id") Long id);
}
