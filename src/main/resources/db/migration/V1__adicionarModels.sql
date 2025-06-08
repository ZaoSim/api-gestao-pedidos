-- Cria extensão para gerar UUIDs
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Cria tabela de clientes
CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL DEFAULT uuid_generate_v4(),
    nome VARCHAR(100) NOT NULL
);

-- Cria tabela de produtos
CREATE TABLE produtos (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL DEFAULT uuid_generate_v4(),
    descricao VARCHAR(255) NOT NULL,
    valor NUMERIC(10,2) NOT NULL,
    quantidade INTEGER NOT NULL
);

-- Cria tabela de pedidos
CREATE TABLE pedidos (
    codigo BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL DEFAULT uuid_generate_v4(),
    total NUMERIC(10,2) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

-- Cria a tabela de pedidos produtos
CREATE TABLE pedidos_produtos (
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL DEFAULT 1,
    PRIMARY KEY (pedido_id, produto_id),
    CONSTRAINT fk_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(codigo),
    CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES produtos(id)
);