package com.carrinho.compras.servico;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.carrinho.compras.basicas.Cupom;
import com.carrinho.compras.controles.resposta.Response;

public interface CupomService {

	public ResponseEntity<Response<Cupom>> salvar(Cupom cupom, BindingResult result);

	public ResponseEntity<Response<Cupom>> atualizar(Cupom cupom, BindingResult result);

	public ResponseEntity<Response<Cupom>> remover(Long idCupom);

	public ResponseEntity<Response<Page<Cupom>>> listar(int page, int count);

	public ResponseEntity<Response<Cupom>> pesquisarPorId(Long idCupom);
	
	public void salvarLista(List<Cupom> cupons);
}
