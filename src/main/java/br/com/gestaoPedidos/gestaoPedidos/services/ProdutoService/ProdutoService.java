package br.com.gestaoPedidos.gestaoPedidos.services.ProdutoService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestaoPedidos.gestaoPedidos.model.Produto;
import br.com.gestaoPedidos.gestaoPedidos.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository repository;

    public Produto salvar(Produto produto){
        return repository.save(produto);
    }
    
    public List<Produto> findAll(){
        return repository.findAll();
    }

    public Produto findProdutoByUUID(UUID uuid){
        return repository.findProdutoByUUID(uuid);
    }

    public void delete(Produto pedido){
        repository.deleteById(pedido.getId());
    }

}
