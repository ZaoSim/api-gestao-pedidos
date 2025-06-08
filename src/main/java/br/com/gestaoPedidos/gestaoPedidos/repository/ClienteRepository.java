package br.com.gestaoPedidos.gestaoPedidos.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gestaoPedidos.gestaoPedidos.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
    @Query("SELECT cliente FROM Cliente cliente WHERE uuid = :uuid")
    public Cliente findClienteByUUID(@RequestParam("uuid") UUID uuid);
}
