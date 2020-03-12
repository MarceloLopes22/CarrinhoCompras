package com.carrinho.compras.controles;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

import com.carrinho.compras.basicas.Cupom;
import com.carrinho.compras.controles.resposta.Response;
import com.carrinho.compras.servico.CupomService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CupomController {

	@Autowired
	private CupomService service;

	@PostMapping(value = "/cupom/salvar")
	public ResponseEntity<Response<Cupom>> salvar(HttpServletRequest request, @RequestBody @Valid Cupom cupom) {
		return this.service.salvar(cupom);
	}

	@PutMapping(value = "/cupom/atualizar")
	public ResponseEntity<Response<Cupom>> atualizar(HttpServletRequest request, @RequestBody @Valid Cupom cupom) {
		return this.service.atualizar(cupom);
	}

	@RequestMapping(value = "/cupom/pesquisarPorId/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response<Cupom>> pesquisarPorId(@PathVariable("id") Long idCupom) {
		return this.service.pesquisarPorId(idCupom);
	}

	@DeleteMapping(value = "/cupom/remover/{id}")
	public ResponseEntity<Response<Cupom>> remover(@PathVariable("id") Long idCupom) {
		return this.service.remover(idCupom);
	}

	@GetMapping(value = "/cupom/listar/{page}/{count}")
	public ResponseEntity<Response<Page<Cupom>>> listar(@PathVariable("page") int page,
			@PathVariable("count") int count) {
		return this.service.listar(page, count);
	}
}
