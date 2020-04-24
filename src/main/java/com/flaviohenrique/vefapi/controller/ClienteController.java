package com.flaviohenrique.vefapi.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flaviohenrique.vefapi.domain.model.Cliente;

@RestController
public class ClienteController {
	
	

	@GetMapping("clientes")
	public List<Cliente> listar() {
		
		var cliente1 = new Cliente();
		var cliente2 = new Cliente();
		
		cliente1.setId(1l);
		cliente1.setNome("flavio");
		cliente1.setEmail("flavio.henso@gmail.com");
		cliente1.setTelefone("84988796047");
		
		cliente2.setId(2l);
		cliente2.setNome("andreia");
		cliente2.setEmail("michellegn@hotmail.com.br");
		cliente2.setTelefone("84998218129");
		
		return Arrays.asList(cliente1,cliente2);
	}
	
}
