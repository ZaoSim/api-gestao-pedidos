package br.com.gestaoPedidos.gestaoPedidos.services.clienteService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestaoPedidos.gestaoPedidos.model.Cliente;
import br.com.gestaoPedidos.gestaoPedidos.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }
    
    public List<Cliente> findAll(){
        return repository.findAll();
    }

    public Cliente findClienteByUUID(UUID uuid){
        return repository.findClienteByUUID(uuid);
    }

    public void delete(Cliente cliente){
        repository.deleteById(cliente.getId());
    }
}
