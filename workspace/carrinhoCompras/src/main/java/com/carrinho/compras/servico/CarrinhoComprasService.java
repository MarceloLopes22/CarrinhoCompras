package com.carrinho.compras.servico;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.carrinho.compras.basicas.CarrinhoCompras;
import com.carrinho.compras.controles.resposta.Response;

public interface CarrinhoComprasService {

	public ResponseEntity<Response<CarrinhoCompras>> comprar(CarrinhoCompras carrinhoCompras, BindingResult result);

	public ResponseEntity<Response<CarrinhoCompras>> remover(Long idCarrinhoCompras);

	public ResponseEntity<Response<Page<CarrinhoCompras>>> listar(int page, int count);

	public ResponseEntity<Response<CarrinhoCompras>> pesquisarPorId(Long idCarrinhoCompras);
}
