package br.com.gestaoPedidos.gestaoPedidos.controller.pedidoController.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.gestaoPedidos.gestaoPedidos.controller.clienteController.dto.ClienteDTO;
import br.com.gestaoPedidos.gestaoPedidos.controller.produtoController.dto.ProdutoDTO;

public class PedidoDTO {
    
    private String uuid;
    private Long codigo;
    private BigDecimal total;
    private String tipo;
    private ClienteDTO cliente;
    private List<ProdutoDTO> items;
    
    public PedidoDTO() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<ProdutoDTO> getItems() {
        return items;
    }

    public void setItems(List<ProdutoDTO> items) {
        this.items = items;
    }
    
    
}
