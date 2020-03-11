package com.carrinho.compras.servico.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.carrinho.compras.basicas.CarrinhoCompras;
import com.carrinho.compras.basicas.Cupom;
import com.carrinho.compras.basicas.Produto;
import com.carrinho.compras.controles.resposta.Response;
import com.carrinho.compras.dao.CarrinhoComprasDAO;
import com.carrinho.compras.enums.TipoProduto;
import com.carrinho.compras.servico.CarrinhoComprasService;
import com.carrinho.compras.servico.CupomService;

@Service
public class CarrinhoComprasServiceImpl implements CarrinhoComprasService {

	@Autowired
	private CarrinhoComprasDAO dao;
	
	@Autowired
	private CupomService cupomService;
	
	@Override
	public ResponseEntity<Response<CarrinhoCompras>> comprar(CarrinhoCompras carrinhoCompras, BindingResult result) {
		
		Response<CarrinhoCompras> response = new Response<CarrinhoCompras>();
		
		//aplicar regra de 10% de desconto em produtos do mesmo tipo cuja quantidade seja superior/igual a 10 itens
		//aplicar 10% de desconto por item.
		aplicarPercentualDescontoItensMesmoTipo(carrinhoCompras);
		
		//Calcular valor total e valor subtotal e aplicar desconto progressivo.
		calcularValorTotalESubTotal(carrinhoCompras);
		
		//Aplicar sempre um unico cupom de maior valor.
		aplicarCupomMaiorValor(carrinhoCompras, response);
		
		if (response.getErros().isEmpty()) {
			carrinhoCompras = dao.save(carrinhoCompras);
			response.setData(carrinhoCompras);
			response.setHttpStatus(HttpStatus.CREATED);
			response.setMensagemSucesso("Compra efetuada com sucesso!");
		}
		
		return new ResponseEntity<Response<CarrinhoCompras>>(response, response.getHttpStatus());
	}

	private void aplicarCupomMaiorValor(CarrinhoCompras carrinhoCompras, Response<CarrinhoCompras> response) {
		
		boolean isMaiorValor = false;
		int maiorValor = 0;
		int indexMaiorValor = 0;
		
		if (carrinhoCompras != null && carrinhoCompras.getCupons().size() > 0) {
			
			maiorValor = carrinhoCompras.getCupons().get(0).getDesconto();
			
			for (int i = 0; i < carrinhoCompras.getCupons().size(); i++) {
				if (carrinhoCompras.getCupons().get(i).getDesconto() > maiorValor) {
					indexMaiorValor = i;
					maiorValor = carrinhoCompras.getCupons().get(i).getDesconto();
					isMaiorValor = true;
				}
			}
			
			if (isMaiorValor) {
				Cupom cupom = carrinhoCompras.getCupons().get(indexMaiorValor);
				ResponseEntity<Response<Cupom>> responseEntity = cupomService.pesquisarPorId(cupom.getId());

				if (responseEntity != null && responseEntity.getBody().getData() != null) {
					
					cupom = Cupom.class.cast(responseEntity.getBody().getData());
					
					if (cupom.isCupomUsado()) {
						response.getErros().add("Cupom já foi utilizado favor usar outro.");
						response.setHttpStatus(HttpStatus.BAD_REQUEST);
					}
					
					if (response.getErros().isEmpty()) {
						aplicarDescontoNoValorTotal(carrinhoCompras, maiorValor);
						cupom.setCupomUsado(true);
						cupomService.atualizar(cupom, null);
						carrinhoCompras.setCupom(cupom);
					}
				}
			}
		}
	}

	/**
	 * Calcular os valores do total e subtotal das compras e 
	 * aplicar desconto progressivo de:
	 * valor total acima de 1.000 reais 5% de desconto,
	 * valor total acima de 5.000 reais 7% de desconto,
	 * valor total acima de 10.000 reais 10% de desconto.
	 **/
	private void calcularValorTotalESubTotal(CarrinhoCompras carrinhoCompras) {
		
		BigDecimal valorTotal = BigDecimal.ZERO;
		BigDecimal valorSubTotal = BigDecimal.ZERO;
		
		for (Produto produto : carrinhoCompras.getProdutos()) {
			BigDecimal quantidade = BigDecimal.valueOf(produto.getQuantidade());
			valorSubTotal = quantidade.multiply(produto.getPreco());
			valorTotal = valorTotal.add(valorSubTotal);
			valorSubTotal = valorTotal;
		}
		
		carrinhoCompras.setSubTotal(valorSubTotal);
		carrinhoCompras.setTotal(valorTotal);
		
		if (carrinhoCompras.getTotal().intValue() >= 1000 && carrinhoCompras.getTotal().intValue() < 5000) {
			// aplicar 5% desconto no valor total.
			aplicarDescontoNoValorTotal(carrinhoCompras, 5);
		} else if (carrinhoCompras.getTotal().intValue() >= 5000 && carrinhoCompras.getTotal().intValue() < 10000) {
			// aplicar 7% desconto no valor total.
			aplicarDescontoNoValorTotal(carrinhoCompras, 7);
		} else {
			// total acima de 10.000 aplicar desconto de 10%.
			aplicarDescontoNoValorTotal(carrinhoCompras, 10);
		}
	}

	private void aplicarDescontoNoValorTotal(CarrinhoCompras carrinhoCompras, int percentualDesconto) {
		BigDecimal desconto = carrinhoCompras.getTotal().multiply(BigDecimal.valueOf(percentualDesconto))
														.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
		carrinhoCompras.setTotal(carrinhoCompras.getTotal().subtract(desconto));	
	}

	/**
	 * aplicar regra de 10% de desconto em produtos do mesmo tipo cuja quantidade seja superior/igual a 10 itens
	 * aplicar 10% de desconto por item.
	 * */
	private void aplicarPercentualDescontoItensMesmoTipo(CarrinhoCompras carrinhoCompras) {
		
		if (carrinhoCompras != null && !carrinhoCompras.getProdutos().isEmpty()) {
			List<Produto> produtos = carrinhoCompras.getProdutos();
			
			for (Produto p : produtos) {
				
				if (p.getQuantidade() >= 10) {
					
					if (p.getTipo().chave == TipoProduto.ELETRONICO.chave) {
						aplicarDesconto(p);
					}
					
					if (p.getTipo().chave == TipoProduto.LIMPEZA.chave) {
						aplicarDesconto(p);
					}
					
					if (p.getTipo().chave == TipoProduto.MOVEL.chave) {
						aplicarDesconto(p);
					}
				}
			}
		}
		
	}

	private void aplicarDesconto(Produto produto) {
		BigDecimal desconto = produto.getPreco().multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100));
		produto.setPreco(produto.getPreco().subtract(desconto));
	}

	@Override
	public ResponseEntity<Response<CarrinhoCompras>> remover(Long idCarrinhoCompras) {
		Response<CarrinhoCompras> response = new Response<CarrinhoCompras>();
		
		Optional<CarrinhoCompras> carrinhoComprasConsultado = dao.findById(idCarrinhoCompras);
		
		if (carrinhoComprasConsultado != null && carrinhoComprasConsultado.isPresent()) {
			dao.delete(carrinhoComprasConsultado.get());
			response.setHttpStatus(HttpStatus.OK);
			response.setMensagemSucesso("CarrinhoCompras removido com suceeso.");
		} else {
			response.getErros().add("CarrinhoCompras não encontrado");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Response<CarrinhoCompras>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<Page<CarrinhoCompras>>> listar(int page, int count) {
		Response<Page<CarrinhoCompras>> response = new Response<Page<CarrinhoCompras>>();
		
		if (page == 0 && count == 0) {
			response.getErros().add("Não foi possivel realizar a pesquisa.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Pageable paging = PageRequest.of(page, count);
		Page<CarrinhoCompras> results = dao.findAll(paging);
		
		if(results.hasContent()) {
			response.setData(results);
			response.setHttpStatus(HttpStatus.OK);
        } else {
        	response.setData(new ArrayList<CarrinhoCompras>());
        	response.setHttpStatus(HttpStatus.OK);
        }
		
		return new ResponseEntity<Response<Page<CarrinhoCompras>>>(response, response.getHttpStatus());
	}

	@Override
	public ResponseEntity<Response<CarrinhoCompras>> pesquisarPorId(Long idCarrinhoCompras) {
		Response<CarrinhoCompras> response = new Response<CarrinhoCompras>();
		
		Optional<CarrinhoCompras> carrinhoCompras = dao.findById(idCarrinhoCompras);
		if (carrinhoCompras != null && carrinhoCompras.isPresent()) {
			response.setData(carrinhoCompras.get());
			response.setHttpStatus(HttpStatus.FOUND);
		} else {
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.getErros().add("CarrinhoCompras não encontrado");
		}
		
		return new ResponseEntity<Response<CarrinhoCompras>>(response, response.getHttpStatus());
	}

}
