package com.flaviohenrique.vefapi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flaviohenrique.vefapi.domain.model.OrdemServico;
import com.flaviohenrique.vefapi.domain.service.GestaoOrdemServico;
import com.flaviohenrique.vefapi.representation.OrdemServicoInput;
import com.flaviohenrique.vefapi.representation.OrdemServicoModel;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	GestaoOrdemServico gestaoOrdemServico;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("novo")
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel novo(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordemServico = toEntity(ordemServicoInput);

		return toModel(gestaoOrdemServico.criar(ordemServico));

	}

	@PostMapping()
	public void editar(@RequestBody OrdemServico ordemservico) {

	}

	@GetMapping
	public List<OrdemServicoModel> listar(Long id) {

		return toModeloList(gestaoOrdemServico.listar());

	}

	@PostMapping("/{ordem_id}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordem_id) {

		OrdemServico ordemServico = gestaoOrdemServico.buscar(ordem_id);

		return ordemServico != null ? ResponseEntity.ok(toModel(ordemServico)) : ResponseEntity.notFound().build();

	}
	
	@PutMapping("/{ordem_id}/finalizar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<OrdemServicoModel> finalizar(@PathVariable Long ordem_id) {
		
		OrdemServico ordemServico = gestaoOrdemServico.finalizar(ordem_id);
		
		return ordemServico != null ? ResponseEntity.ok(toModel(ordemServico)) : ResponseEntity.notFound().build();
	}

	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoModel.class);
	}

	private List<OrdemServicoModel> toModeloList(List<OrdemServico> ordemServicos) {

		List<OrdemServicoModel> ordemServicoModels = new ArrayList<>();

		ordemServicos.forEach(ordemServico -> ordemServicoModels.add(toModel(ordemServico)));

		return ordemServicoModels;
	}

	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
}
