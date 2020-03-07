package com.carrinho.compras.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrinho.compras.basicas.Produto;

@Repository
public interface ProdutoDAO extends JpaRepository<Produto, Long> {

}
