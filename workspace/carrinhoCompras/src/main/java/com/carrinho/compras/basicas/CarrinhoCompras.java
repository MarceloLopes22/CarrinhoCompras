package com.carrinho.compras.basicas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@SequenceGenerator(allocationSize = 1, name = "carrinho_compras_seq", sequenceName = "carrinho_compras_seq")
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
	
	@ManyToOne
	@JoinColumn(name = "id_cupom", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private Cupom cupom;

	@ManyToMany
	@JoinTable(name = "pedido", joinColumns = { @JoinColumn(name = "id_carrinho_compras") }, inverseJoinColumns = {
			@JoinColumn(name = "id_produto") })
	private List<Produto> produtos = new ArrayList<>();
	
	@Transient
	private List<Cupom> cupons = new ArrayList<>();

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

	public List<Cupom> getCupons() {
		return cupons;
	}

	public void setCupons(List<Cupom> cupons) {
		this.cupons = cupons;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public Cupom getCupom() {
		return cupom;
	}

	public void setCupom(Cupom cupom) {
		this.cupom = cupom;
	}

	public List<Cupom> getCuponsDiferentes(Cupom cupom) {
		
		List<Cupom> diferentes = new ArrayList<>();
		for (Cupom c : cupons) {
			if (cupom.getId() != c.getId()) {
				diferentes.add(c);
			}
		}
		return diferentes;
	}
}
