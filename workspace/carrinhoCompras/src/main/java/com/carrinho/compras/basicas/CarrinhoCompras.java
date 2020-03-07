package com.carrinho.compras.basicas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "carrinho_compras_seq", sequenceName = "carrinho_compras_seq")
@Table(name = "carrinho_compras")
public class CarrinhoCompras implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carrinho_compras_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "total")
	private BigDecimal total;
	
	@Column(name = "sub_total")
	private BigDecimal subTotal;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<Produto> produtos = new ArrayList<Produto>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<Cupom> cupons = new ArrayList<Cupom>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

//	public List<Produto> getProdutos() {
//		return produtos;
//	}
//
//	public void setProdutos(List<Produto> produtos) {
//		this.produtos = produtos;
//	}
//
//	public List<Cupom> getCupons() {
//		return cupons;
//	}
//
//	public void setCupons(List<Cupom> cupons) {
//		this.cupons = cupons;
//	}
}
