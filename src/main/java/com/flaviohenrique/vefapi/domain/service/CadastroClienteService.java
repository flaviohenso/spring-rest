package com.flaviohenrique.vefapi.domain.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flaviohenrique.vefapi.domain.exception.NegocioException;
import com.flaviohenrique.vefapi.domain.model.Cliente;
import com.flaviohenrique.vefapi.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente salvar(Cliente cliente) {
		
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if(clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("já existe um cliente com esse email");
		}
		
		return clienteRepository.save(cliente);
	}

	public Boolean excluir(Long clienteId) {

		if (!clienteRepository.existsById(clienteId)) {
			return false;
		}

		clienteRepository.deleteById(clienteId);
		return true;
	}

	public Cliente update(Long clienteId, Cliente cliente) {
		if (!clienteRepository.existsById(clienteId)) {
			return null;
		}

		cliente.setId(clienteId);
		clienteRepository.save(cliente);
		return cliente;
	}

	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	public Cliente buscar(Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);

		if (cliente.isPresent()) {
			return cliente.get();
		}

		return null;
	}

}
