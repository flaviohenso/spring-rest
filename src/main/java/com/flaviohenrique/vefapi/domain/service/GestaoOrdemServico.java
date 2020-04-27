package com.flaviohenrique.vefapi.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flaviohenrique.vefapi.domain.exception.EntidadeNaoEncontradaException;
import com.flaviohenrique.vefapi.domain.exception.NegocioException;
import com.flaviohenrique.vefapi.domain.model.Cliente;
import com.flaviohenrique.vefapi.domain.model.Comentario;
import com.flaviohenrique.vefapi.domain.model.OrdemServico;
import com.flaviohenrique.vefapi.domain.model.StatusOrdemServico;
import com.flaviohenrique.vefapi.domain.repository.ClienteRepository;
import com.flaviohenrique.vefapi.domain.repository.ComentarioRepository;
import com.flaviohenrique.vefapi.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServico {

	@Autowired
	OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	ComentarioRepository comentarioRepository;

	@Autowired
	ClienteRepository clienteRepository;

	public OrdemServico criar(OrdemServico ordemServico) {
		ordemServico.setStatusOrdemServico(StatusOrdemServico.ABERTO);
		ordemServico.setDataAbertura(OffsetDateTime.now());

		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente nao encontrado"));

		ordemServico.setCliente(cliente);

		return ordemServicoRepository.save(ordemServico);
	}

	public List<OrdemServico> listar() {
		return ordemServicoRepository.findAll();
	}

	public OrdemServico buscar(Long id) {
	
		Optional<OrdemServico> optional = ordemServicoRepository.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	public Comentario addComentario(Long id, String descricao) {
		
		OrdemServico ordemServico = ordemServicoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		
		Comentario comentario = new Comentario();
		comentario.setDescricao(descricao);
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}

	public List<Comentario> listarComentario(Long id) {
		OrdemServico ordemServico = ordemServicoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		
		return ordemServico.getComentarios();
	}
	
	public OrdemServico finalizar(Long ordemServicoId) {
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		
		ordemServico.setStatusOrdemServico(StatusOrdemServico.FINALIZADA);
		ordemServico.setDataFinalizacao(OffsetDateTime.now());

		
		return ordemServicoRepository.save(ordemServico);
	}
}
