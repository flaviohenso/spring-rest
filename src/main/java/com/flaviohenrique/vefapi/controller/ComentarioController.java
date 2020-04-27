package com.flaviohenrique.vefapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flaviohenrique.vefapi.domain.model.Comentario;
import com.flaviohenrique.vefapi.domain.service.GestaoOrdemServico;
import com.flaviohenrique.vefapi.representation.ComentarioModel;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private GestaoOrdemServico gestaoOrdemServico;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@PathVariable Long ordemServicoId, @RequestBody Comentario comentario) {
		
		return modelMapper.map(gestaoOrdemServico.addComentario(ordemServicoId, comentario.getDescricao()), ComentarioModel.class);
	}
	
	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
		return toModeloList(gestaoOrdemServico.listarComentario(ordemServicoId));
	}
	
	private ComentarioModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioModel.class);
	}
	
	private List<ComentarioModel> toModeloList(List<Comentario> comentarios) {

		List<ComentarioModel> comentarioModels = new ArrayList<>();

		comentarios.forEach(comentario -> comentarioModels.add(toModel(comentario)));

		return comentarioModels;
	}
}
