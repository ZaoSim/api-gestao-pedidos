package br.com.gestaoPedidos.gestaoPedidos.controller.pedidoController.dto.retornoPedidos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.gestaoPedidos.gestaoPedidos.controller.clienteController.dto.ClienteDTO;

public class RetornoPedidosDTO {
    
    private UUID uuid;
    private Long codigo;
    private BigDecimal total;
    private String tipo;
    private ClienteDTO cliente;
    private List<ProdutoPedidoDTO> items = new ArrayList<>();
    
    public RetornoPedidosDTO() {
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

    public List<ProdutoPedidoDTO> getItems() {
        return items;
    }

    public void setItems(List<ProdutoPedidoDTO> items) {
        this.items = items;
    }

    public void addItems(ProdutoPedidoDTO item){
        this.items.add(item);
    }



    public UUID getUuid() {
        return uuid;
    }



    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
