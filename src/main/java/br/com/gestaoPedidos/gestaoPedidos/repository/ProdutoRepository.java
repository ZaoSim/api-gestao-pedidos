package br.com.gestaoPedidos.gestaoPedidos.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gestaoPedidos.gestaoPedidos.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    
    @Query("SELECT produto FROM Produto produto WHERE uuid = :uuid")
    public Produto findProdutoByUUID(@RequestParam("uuid") UUID uuid);

}
