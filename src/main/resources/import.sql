-- Carga inicial do PéFrioShop. Executada pelo Hibernate na criação do esquema.
-- Uma instrução por linha: o extrator do Hibernate não junta linhas.
-- As chaves são explícitas para amarrar as relações.

INSERT INTO cliente (id, nome_completo, genero, email, telefone, data_nascimento, data_cadastro, rua, bairro, numero, cidade, cep) VALUES (1, 'Ana Maria da Silva', 'FEMININO', 'ana@exemplo.com', '(84) 99999-0001', DATE '1990-10-12', TIMESTAMP '2026-09-05 08:30:00', 'Rua das Flores', 'Centro', '10', 'Açu', '59650000');
INSERT INTO cliente (id, nome_completo, genero, email, telefone, data_nascimento, data_cadastro, rua, bairro, numero, cidade, cep) VALUES (2, 'Bruno Costa Medeiros', 'MASCULINO', 'bruno@exemplo.com', '(84) 99999-0002', DATE '1985-11-03', TIMESTAMP '2026-10-05 09:15:00', 'Rua Gamaliel Martins Bezerra', 'Alto da Alegria', '587', 'Angicos', '59515000');
INSERT INTO cliente (id, nome_completo, genero, email, telefone, data_nascimento, data_cadastro, rua, bairro, numero, cidade, cep) VALUES (3, 'Carla Souza Lima', 'FEMININO', 'carla@exemplo.com', '(84) 99999-0003', DATE '2000-10-25', TIMESTAMP '2026-10-14 14:40:00', 'Avenida Abel Cabral', 'Nova Parnamirim', '100', 'Parnamirim', '59151250');

INSERT INTO ficha_clinica_do_pe (id, cliente_id, comprimento_cm, largura, pisada, numero_preferido_br, marca_preferida, temperatura, odor, data_ultima_visita_podologo, tem_joanete) VALUES (1, 1, 24.50, 'NORMAL', 'NEUTRA', 37, 'PéFrioSports', 'FRIO', 'LEVE', DATE '2026-08-15', false);

INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro, foto, foto_content_type) VALUES (1, 'Tênis Corrida X', 'PéFrioSports', 'TENIS', 'UNISSEX', 42, 120.00, 259.90, DATE '2026-10-14', null, null);
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro, foto, foto_content_type) VALUES (2, 'Sandália Verão', 'PéFrioSports', 'SANDALIA', 'FEMININO', 37, 45.00, 99.90, DATE '2026-10-13', null, null);
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro, foto, foto_content_type) VALUES (3, 'Bota Campo', 'PéFrioSports', 'BOTA', 'MASCULINO', 41, 180.00, 349.90, DATE '2026-10-12', null, null);
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro, foto, foto_content_type) VALUES (4, 'Sapatênis Casual', 'PéFrioSports', 'SAPATENIS', 'MASCULINO', 40, 95.00, 189.90, DATE '2026-10-11', null, null);
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro, foto, foto_content_type) VALUES (5, 'Chinelo Praia', 'PéFrioSports', 'CHINELO', 'UNISSEX', 39, 15.00, 39.90, DATE '2026-10-10', null, null);

INSERT INTO pagamento_do_pedido (id, forma, cartao_ultimos_digitos, chave_pix, valor_pago) VALUES (1, 'PIX', null, 'ana@exemplo.com', null);
INSERT INTO pagamento_do_pedido (id, forma, cartao_ultimos_digitos, chave_pix, valor_pago) VALUES (2, 'DINHEIRO', null, null, 349.90);

INSERT INTO pedido (id, cliente_id, data_pedido, pagamento_id) VALUES (1, 1, TIMESTAMP '2026-09-05 10:30:00', 1);
INSERT INTO pedido (id, cliente_id, data_pedido, pagamento_id) VALUES (2, 2, TIMESTAMP '2026-10-05 15:00:00', 2);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (1, 1, 1, 1, 259.90);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (2, 1, 5, 2, 39.90);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (3, 2, 3, 1, 349.90);
