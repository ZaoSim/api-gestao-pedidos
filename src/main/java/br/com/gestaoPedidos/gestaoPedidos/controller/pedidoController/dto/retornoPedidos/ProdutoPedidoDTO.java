package br.com.gestaoPedidos.gestaoPedidos.controller.pedidoController.dto.retornoPedidos;

import java.math.BigDecimal;

public class ProdutoPedidoDTO {
    
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private int quantidade;
    private BigDecimal total;

    public ProdutoPedidoDTO() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }   
}
