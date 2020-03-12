-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.7.0
-- PostgreSQL version: 9.3
-- Project Site: pgmodeler.com.br
-- Model Author: ---

SET check_function_bodies = false;
-- ddl-end --


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: carrinho_compras_atual | type: DATABASE --
-- -- DROP DATABASE carrinho_compras_atual;
-- CREATE DATABASE carrinho_compras_atual
-- ;
-- -- ddl-end --
-- 

-- object: public.pedido | type: TABLE --
-- DROP TABLE public.pedido;
CREATE TABLE public.pedido(
	id_carrinho_compras bigint,
	id_produto bigint
);
-- ddl-end --
-- object: public.produto | type: TABLE --
-- DROP TABLE public.produto;
CREATE TABLE public.produto(
	id bigint,
	nome varchar,
	preco decimal,
	quantidade integer,
	tipo integer,
	CONSTRAINT pk_produto PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.cupom | type: TABLE --
-- DROP TABLE public.cupom;
CREATE TABLE public.cupom(
	id bigint,
	descricao varchar,
	desconto integer,
	is_cupom_usado boolean,
	CONSTRAINT pk_cupom PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.carrinho_compras | type: TABLE --
-- DROP TABLE public.carrinho_compras;
CREATE TABLE public.carrinho_compras(
	id bigint,
	total decimal,
	sub_total decimal,
	id_cupom bigint,
	CONSTRAINT pk_carrinho PRIMARY KEY (id)

);
-- ddl-end --
-- Appended SQL commands --
DROP TABLE "public"."carrinho_compras";
DROP TABLE "public"."cupom";
DROP TABLE "public"."pedido";
DROP TABLE "public"."produto";

INSERT INTO CUPOM (id,descricao,desconto,is_cupom_usado) VALUES (nextval('cupom_seq'),'Desconto 10%',10,false);
INSERT INTO CUPOM (id,descricao,desconto,is_cupom_usado) VALUES (nextval('cupom_seq'),'Desconto 40%',40,false);
INSERT INTO CUPOM (id,descricao,desconto,is_cupom_usado) VALUES (nextval('cupom_seq'),'Desconto 20%',20,false);
INSERT INTO CUPOM (id,descricao,desconto,is_cupom_usado) VALUES (nextval('cupom_seq'),'Desconto 30%',30,true);

INSERT INTO PRODUTO (id,nome,preco,quantidade,tipo) VALUES (nextval('produto_seq'),'TV',2500,10,0);
INSERT INTO PRODUTO (id,nome,preco,quantidade,tipo) VALUES (nextval('produto_seq'),'Computador Dell',5000,10,0);
INSERT INTO PRODUTO (id,nome,preco,quantidade,tipo) VALUES (nextval('produto_seq'),'Impressora HP',500,10,0);
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

-- object: cupom_fk | type: CONSTRAINT --
-- ALTER TABLE public.carrinho_compras DROP CONSTRAINT cupom_fk;
ALTER TABLE public.carrinho_compras ADD CONSTRAINT cupom_fk FOREIGN KEY (id_cupom)
REFERENCES public.cupom (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: carrinho_compras_uq | type: CONSTRAINT --
-- ALTER TABLE public.carrinho_compras DROP CONSTRAINT carrinho_compras_uq;
ALTER TABLE public.carrinho_compras ADD CONSTRAINT carrinho_compras_uq UNIQUE (id_cupom);
-- ddl-end --


-- object: fk_carrinho | type: CONSTRAINT --
-- ALTER TABLE public.pedido DROP CONSTRAINT fk_carrinho;
ALTER TABLE public.pedido ADD CONSTRAINT fk_carrinho FOREIGN KEY (id_carrinho_compras)
REFERENCES public.carrinho_compras (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --


-- object: fk_produto | type: CONSTRAINT --
-- ALTER TABLE public.pedido DROP CONSTRAINT fk_produto;
ALTER TABLE public.pedido ADD CONSTRAINT fk_produto FOREIGN KEY (id_produto)
REFERENCES public.produto (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --



