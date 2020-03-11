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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carrinho.compras.basicas.CarrinhoCompras;
import com.carrinho.compras.controles.resposta.Response;
import com.carrinho.compras.servico.CarrinhoComprasService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CarrinhoComprasController {

	@Autowired
	private CarrinhoComprasService service;

	@PostMapping(value = "/carrinhoCompras/comprar")
	public ResponseEntity<Response<CarrinhoCompras>> comprar(HttpServletRequest request, @RequestBody @Valid CarrinhoCompras carrinhoCompras,
			BindingResult result) {
		return this.service.comprar(carrinhoCompras, result);
	}

	@RequestMapping(value = "/carrinhoCompras/pesquisarPorId/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response<CarrinhoCompras>> pesquisarPorId(@PathVariable("id") Long idCarrinhoCompras) {
		return this.service.pesquisarPorId(idCarrinhoCompras);
	}

	@DeleteMapping(value = "/carrinhoCompras/remover/{id}")
	public ResponseEntity<Response<CarrinhoCompras>> remover(@PathVariable("id") Long idCarrinhoCompras) {
		return this.service.remover(idCarrinhoCompras);
	}

	@GetMapping(value = "/carrinhoCompras/listar/{page}/{count}")
	public ResponseEntity<Response<Page<CarrinhoCompras>>> listar(@PathVariable("page") int page,
			@PathVariable("count") int count) {
		return this.service.listar(page, count);
	}
}
