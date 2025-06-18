package br.com.gestaoPedidos.gestaoPedidos.controller.clienteController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestaoPedidos.gestaoPedidos.controller.clienteController.dto.ClienteDTO;
import br.com.gestaoPedidos.gestaoPedidos.model.Cliente;
import br.com.gestaoPedidos.gestaoPedidos.services.clienteService.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/")
@CrossOrigin(origins = "*")
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping(path = "/clientes")
    @Operation(summary = "Criar um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Nome vazio"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    public ResponseEntity<?> novoCliente(@RequestBody ClienteDTO clienteDTO) {

        try {
            if (clienteDTO.getNome() == null || clienteDTO.getNome().equals("")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome vazio");
            }

            Cliente cliente = new Cliente();
            cliente.setNome(clienteDTO.getNome());
            cliente = clienteService.salvar(cliente);

            clienteDTO.setUuid(cliente.getUuid());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");

        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
    }

    @GetMapping(path = "/clientes")
    @Operation(summary = "Listar clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes"),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado!"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    public ResponseEntity<?> retornaClientes() {
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        try {
            List<Cliente> clientes = clienteService.findAll();
            if (clientes.size() == 0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sem clientes cadastrados!");
            }

            for (Cliente c : clientes) {
                ClienteDTO clienteDTO = new ClienteDTO();
                clienteDTO.setNome(c.getNome());
                clienteDTO.setUuid(c.getUuid());
                clientesDTO.add(clienteDTO);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro");
        }

        return ResponseEntity.status(HttpStatus.OK).body(clientesDTO);
    }

    @GetMapping(path = "/clientes/{uuid}")
    @Operation(summary = "Pesquisar cliente por UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado!"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    public ResponseEntity<?> retornarClientePorUUID(@PathVariable(name = "uuid") UUID uuid) {
        ClienteDTO clienteDTO = new ClienteDTO();
        try {
            Cliente cliente = clienteService.findClienteByUUID(uuid);
            clienteDTO = new ClienteDTO(cliente.getUuid(), cliente.getNome());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro");
        }

        return ResponseEntity.status(HttpStatus.OK).body(clienteDTO);
    }

    @PutMapping(path = "/clientes/{uuid}")
    @Operation(summary = "Atualizar dados do cliente por UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado!"),
            @ApiResponse(responseCode = "400", description = "Informe o novo nome do cliente!"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    public ResponseEntity<?> atualizarCliente(@PathVariable(name = "uuid") UUID uuid,
            @RequestBody ClienteDTO clienteDTO) {

        try {
            Cliente cliente = clienteService.findClienteByUUID(uuid);
            if (cliente == null || cliente.getId() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
            }
            if((clienteDTO.getNome().trim()).isBlank()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Informe o novo nome do cliente!");
            }
            cliente.setNome(clienteDTO.getNome());
            clienteService.salvar(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro");
        }

        return ResponseEntity.status(HttpStatus.OK).body(clienteDTO);
    }

    @Operation(summary = "Atualizar dados do cliente por UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente excluido com sucesso!"),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado!"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @DeleteMapping(path = "/clientes/{uuid}")
    public ResponseEntity<?> deletarCliente(@PathVariable(name = "uuid") UUID uuid) {
        try {
            Cliente cliente = clienteService.findClienteByUUID(uuid);
            if(cliente == null || cliente.getId() == 0){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum cliente encontrado!");
            }
            clienteService.delete(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Cliente excluido com sucesso!");
    }
}
