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

import com.carrinho.compras.basicas.Produto;
import com.carrinho.compras.controles.resposta.Response;
import com.carrinho.compras.dao.ProdutoDAO;
import com.carrinho.compras.servico.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoDAO dao;
	
	@Override
	public ResponseEntity<Response<Produto>> salvar(Produto produto, BindingResult result) {
		
		Response<Produto> response = new Response<Produto>();
		
		produto = dao.saveAndFlush(produto);
		response.setData(produto);
		response.setHttpStatus(HttpStatus.CREATED);
		
		return new ResponseEntity<Response<Produto>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Produto>> atualizar(Produto produto, BindingResult result) {
		Response<Produto> response = new Response<Produto>();
		
		Optional<Produto> produtoPesquisado = dao.findById(produto.getId());
		
		if (produtoPesquisado != null && produtoPesquisado.isPresent()) {
		
			Produto produtoConsultado = produtoPesquisado.get();
			produtoConsultado = produto;
			
			produto = dao.saveAndFlush(produtoConsultado);
			response.setData(produto);
			response.setHttpStatus(HttpStatus.OK);

		} else {
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.getErros().add("Produto não encontrado.");
		}
		
		return new ResponseEntity<Response<Produto>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Produto>> remover(Long idProduto) {
		Response<Produto> response = new Response<Produto>();
		
		Optional<Produto> produtoConsultado = dao.findById(idProduto);
		
		if (produtoConsultado != null && produtoConsultado.isPresent()) {
			dao.delete(produtoConsultado.get());
			response.setHttpStatus(HttpStatus.OK);
			response.setMensagemSucesso("Produto removido com suceeso.");
		} else {
			response.getErros().add("Produto não encontrado");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Response<Produto>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Page<Produto>>> listar(int page, int count) {
		Response<Page<Produto>> response = new Response<Page<Produto>>();
		
		if (page == 0 && count == 0) {
			response.getErros().add("Não foi possivel realizar a pesquisa.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Pageable paging = PageRequest.of(page, count);
		Page<Produto> results = dao.findAll(paging);
		
		if(results.hasContent()) {
			response.setData(results);
			response.setHttpStatus(HttpStatus.OK);
        } else {
        	response.setData(new ArrayList<Produto>());
        }
		
		return new ResponseEntity<Response<Page<Produto>>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Produto>> pesquisarPorId(Long idProduto) {
		Response<Produto> response = new Response<Produto>();
		
		Optional<Produto> produto = dao.findById(idProduto);
		if (produto != null && produto.isPresent()) {
			response.setData(produto);
			response.setHttpStatus(HttpStatus.FOUND);
		} else {
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.getErros().add("Produto não encontrado");
		}
		
		return new ResponseEntity<Response<Produto>>(response, response.getHttpStatus());
	}

}
