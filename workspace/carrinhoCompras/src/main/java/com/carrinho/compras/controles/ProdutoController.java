package com.carrinho.compras.controles;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carrinho.compras.basicas.Produto;
import com.carrinho.compras.controles.resposta.Response;
import com.carrinho.compras.servico.ProdutoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@PostMapping(value = "/produto/salvar")
	public ResponseEntity<Response<Produto>> salvar(HttpServletRequest request, @RequestBody @Valid Produto produto,
			BindingResult result) {
		return this.service.salvar(produto, result);
	}

	@PutMapping(value = "/produto/atualizar")
	public ResponseEntity<Response<Produto>> atualizar(HttpServletRequest request, @RequestBody @Valid Produto produto,
			BindingResult result) {
		return this.service.atualizar(produto, result);
	}

	@RequestMapping(value = "/produto/pesquisarPorId/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response<Produto>> pesquisarPorId(@PathVariable("id") Long idProduto) {
		return this.service.pesquisarPorId(idProduto);
	}

	@DeleteMapping(value = "/produto/remover/{id}")
	public ResponseEntity<Response<Produto>> remover(@PathVariable("id") Long idProduto) {
		return this.service.remover(idProduto);
	}

	@GetMapping(value = "/produto/listar/{page}/{count}")
	public ResponseEntity<Response<Page<Produto>>> listar(@PathVariable("page") int page,
			@PathVariable("count") int count) {
		return this.service.listar(page, count);
	}
}
