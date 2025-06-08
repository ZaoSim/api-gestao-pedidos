package br.com.gestaoPedidos.gestaoPedidos.controller.clienteController.dto;

import java.util.UUID;

public class ClienteDTO {

    private UUID uuid;
    private Long id;
    private String nome;

    public ClienteDTO() {
    }

    public ClienteDTO(UUID uuid, Long id, String nome) {
        this.uuid = uuid;
        this.id = id;
        this.nome = nome;
    }

    public ClienteDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public ClienteDTO(UUID uuid, String nome) {
        this.uuid = uuid;
        this.nome = nome;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }
}
