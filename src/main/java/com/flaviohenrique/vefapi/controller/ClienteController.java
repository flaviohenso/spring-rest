package com.flaviohenrique.vefapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flaviohenrique.vefapi.domain.model.Cliente;
import com.flaviohenrique.vefapi.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private CadastroClienteService cadastroClienteService;

	@GetMapping
	public List<Cliente> listar() {		
		return cadastroClienteService.listar();	
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {		
		Cliente cliente = cadastroClienteService.buscar(clienteId);
		
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return cadastroClienteService.salvar(cliente);
	}
	
	@PutMapping("{clienteId}")
	public ResponseEntity<Cliente> update(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente) {		
		Cliente responseCliente = cadastroClienteService.update(clienteId, cliente);
		
		return responseCliente != null ? ResponseEntity.ok(responseCliente) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{clienteId}")
	public ResponseEntity<Void> excluir(@PathVariable Long clienteId) {		
		return cadastroClienteService.excluir(clienteId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
	
}
