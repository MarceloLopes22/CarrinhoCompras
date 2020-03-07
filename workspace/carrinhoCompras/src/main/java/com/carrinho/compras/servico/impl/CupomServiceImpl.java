package com.carrinho.compras.servico.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.carrinho.compras.basicas.Cupom;
import com.carrinho.compras.controles.resposta.Response;
import com.carrinho.compras.dao.CupomDAO;
import com.carrinho.compras.servico.CupomService;

@Service
public class CupomServiceImpl implements CupomService {

	@Autowired
	private CupomDAO dao;
	
	@Override
	public ResponseEntity<Response<Cupom>> salvar(Cupom cupom, BindingResult result) {
		
		Response<Cupom> response = new Response<Cupom>();
		
		cupom = dao.saveAndFlush(cupom);
		response.setData(cupom);
		response.setHttpStatus(HttpStatus.CREATED);
		
		return new ResponseEntity<Response<Cupom>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Cupom>> atualizar(Cupom cupom, BindingResult result) {
		Response<Cupom> response = new Response<Cupom>();
		
		Optional<Cupom> cupomPesquisado = dao.findById(cupom.getId());
		
		if (cupomPesquisado != null && cupomPesquisado.isPresent()) {
			Cupom cupomConsultado = cupomPesquisado.get();
			cupomConsultado = cupom;
			
			cupom = dao.saveAndFlush(cupomConsultado);
			response.setData(cupom);
			response.setHttpStatus(HttpStatus.OK);
		} else {
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.getErros().add("Cupom não encontrado.");
		}
		
		
		return new ResponseEntity<Response<Cupom>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Cupom>> remover(Long idCupom) {
		Response<Cupom> response = new Response<Cupom>();
		
		Optional<Cupom> cupomConsultado = dao.findById(idCupom);
		
		if (cupomConsultado != null && cupomConsultado.isPresent()) {
			dao.delete(cupomConsultado.get());
			response.setHttpStatus(HttpStatus.OK);
			response.setMensagemSucesso("Cupom removido com suceeso.");
		} else {
			response.getErros().add("Cupom não encontrado");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Response<Cupom>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Page<Cupom>>> listar(int page, int count) {
		Response<Page<Cupom>> response = new Response<Page<Cupom>>();
		
		if (page == 0 && count == 0) {
			response.getErros().add("Não foi possivel realizar a pesquisa.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Pageable paging = PageRequest.of(page, count);
		Page<Cupom> results = dao.findAll(paging);
		
		if(results.hasContent()) {
			response.setData(results);
			response.setHttpStatus(HttpStatus.OK);
        } else {
        	response.setData(new ArrayList<Cupom>());
        }
		
		return new ResponseEntity<Response<Page<Cupom>>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Cupom>> pesquisarPorId(Long idCupom) {
		Response<Cupom> response = new Response<Cupom>();
		
		Optional<Cupom> cupom = dao.findById(idCupom);
		if (cupom != null && cupom.isPresent()) {
			response.setData(cupom);
			response.setHttpStatus(HttpStatus.FOUND);
		} else {
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.getErros().add("Cupom não encontrado");
		}
		
		return new ResponseEntity<Response<Cupom>>(response, response.getHttpStatus());
	}

}
