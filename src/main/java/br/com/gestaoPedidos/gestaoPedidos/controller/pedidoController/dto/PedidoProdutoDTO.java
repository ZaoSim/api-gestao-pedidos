package br.com.gestaoPedidos.gestaoPedidos.controller.pedidoController.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.gestaoPedidos.gestaoPedidos.model.Produto;

public class PedidoProdutoDTO {
    
    private PedidoDTO pedido;    
    private UUID uuid;
    private Produto produto;    
    private int quantidade;    
    private BigDecimal valorUnitario;
    private BigDecimal totalItem;

    public PedidoProdutoDTO() {
    }
    public PedidoDTO getPedido() {
        return pedido;
    }
    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }
    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    public BigDecimal getTotalItem() {
        return totalItem;
    }
    public void setTotalItem(BigDecimal totalItem) {
        this.totalItem = totalItem;
    }
}
