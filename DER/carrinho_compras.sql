-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.7.0
-- PostgreSQL version: 9.3
-- Project Site: pgmodeler.com.br
-- Model Author: ---

SET check_function_bodies = false;
-- ddl-end --


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: carrinho_compras | type: DATABASE --
-- -- DROP DATABASE carrinho_compras;
-- CREATE DATABASE carrinho_compras
-- ;
-- -- ddl-end --
-- 

-- object: public.produto | type: TABLE --
-- DROP TABLE public.produto;
CREATE TABLE public.produto(
	id bigint NOT NULL,
	nome varchar,
	preco decimal,
	quantidade integer,
	tipo integer,
	CONSTRAINT pk_produto PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.carrinho_compras | type: TABLE --
-- DROP TABLE public.carrinho_compras;
CREATE TABLE public.carrinho_compras(
	id bigint NOT NULL,
	total decimal,
	sub_total decimal,
	id_cupom bigint,
	id_produto bigint,
	CONSTRAINT pk_carrinho_compras PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.cupom | type: TABLE --
-- DROP TABLE public.cupom;
CREATE TABLE public.cupom(
	id bigint NOT NULL,
	descricao varchar,
	desconto integer,
	is_cupom_usado boolean,
	CONSTRAINT pk_cupom PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.produto_seq | type: SEQUENCE --
-- DROP SEQUENCE public.produto_seq;
CREATE SEQUENCE public.produto_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.carrinho_compras_seq | type: SEQUENCE --
-- DROP SEQUENCE public.carrinho_compras_seq;
CREATE SEQUENCE public.carrinho_compras_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.cupom_seq | type: SEQUENCE --
-- DROP SEQUENCE public.cupom_seq;
CREATE SEQUENCE public.cupom_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: cupom_fk | type: CONSTRAINT --
-- ALTER TABLE public.carrinho_compras DROP CONSTRAINT cupom_fk;
ALTER TABLE public.carrinho_compras ADD CONSTRAINT cupom_fk FOREIGN KEY (id_cupom)
REFERENCES public.cupom (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: produto_fk | type: CONSTRAINT --
-- ALTER TABLE public.carrinho_compras DROP CONSTRAINT produto_fk;
ALTER TABLE public.carrinho_compras ADD CONSTRAINT produto_fk FOREIGN KEY (id_produto)
REFERENCES public.produto (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --



