package com.carrinho.compras.basicas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "cupom_seq", sequenceName = "cupom_seq")
@Table(name = "cupom")
public class Cupom implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cupom_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "desconto")
	private Integer desconto;
	
	@Column(name = "is_cupom_usado")
	private boolean isCupomUsado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getDesconto() {
		return desconto;
	}

	public void setDesconto(Integer desconto) {
		this.desconto = desconto;
	}

	public boolean isCupomUsado() {
		return isCupomUsado;
	}

	public void setCupomUsado(boolean isCupomUsado) {
		this.isCupomUsado = isCupomUsado;
	}
}
