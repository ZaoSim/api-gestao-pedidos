package br.com.gestaoPedidos.gestaoPedidos.controller.produtoController.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProdutoDTO {

    private UUID uuid;
    private String descricao;
    private BigDecimal valor;
    private int quantidade;

    public ProdutoDTO() {
    }

    public ProdutoDTO(UUID uuid) {
        this.uuid = uuid;
    }

    public ProdutoDTO(UUID uuid, String descricao) {
        this.uuid = uuid;
        this.descricao = descricao;
    }

    public ProdutoDTO(UUID uuid, String descricao, BigDecimal valor) {
        this.uuid = uuid;
        this.descricao = descricao;
        this.valor = valor;
    }

    public ProdutoDTO(UUID uuid, String descricao, BigDecimal valor, int quantidade) {
        this.uuid = uuid;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
}
