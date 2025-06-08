ALTER TABLE gestaopedidos.pedidos_produtos
ADD COLUMN uuid uuid DEFAULT uuid_generate_v4();