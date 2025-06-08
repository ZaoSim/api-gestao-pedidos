ALTER TABLE gestaopedidos.pedidos_produtos ADD id bigserial NOT NULL;
ALTER TABLE gestaopedidos.pedidos_produtos ADD CONSTRAINT pedidos_produtos_unique UNIQUE (id);
