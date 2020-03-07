package com.carrinho.compras.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrinho.compras.basicas.Cupom;

@Repository
public interface CupomDAO extends JpaRepository<Cupom, Long> {

}
