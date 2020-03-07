package com.carrinho.compras.enums;

public enum TipoProduto {

	ELETRONICO(1, "Eletrônico"),
	LIMPEZA(2, "Limpeza"),
	MOVEL(3, "Movel");
	
	public int chave;
	public String valor;
	
	TipoProduto(int chave, String valor) {
		this.chave = chave;
		this.valor = valor;
	}
}
