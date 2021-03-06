package com.carrinho.compras.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrinho.compras.basicas.CarrinhoCompras;

@Repository
public interface CarrinhoComprasDAO extends JpaRepository<CarrinhoCompras, Long> {

}
