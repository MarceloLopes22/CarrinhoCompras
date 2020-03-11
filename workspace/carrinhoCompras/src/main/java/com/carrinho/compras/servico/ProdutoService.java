package com.carrinho.compras.servico;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.carrinho.compras.basicas.Produto;
import com.carrinho.compras.controles.resposta.Response;

public interface ProdutoService {

	public ResponseEntity<Response<Produto>> salvar(Produto produto, BindingResult result);

	public ResponseEntity<Response<Produto>> atualizar(Produto produto, BindingResult result);

	public ResponseEntity<Response<Produto>> remover(Long idProduto);

	public ResponseEntity<Response<Page<Produto>>> listar(int page, int count);

	public ResponseEntity<Response<Produto>> pesquisarPorId(Long idProduto);
	
	public void salvarLista(List<Produto> produtos);
}
