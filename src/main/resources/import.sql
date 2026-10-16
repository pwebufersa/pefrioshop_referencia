-- Carga inicial do PéFrioShop. Executada pelo Hibernate na criação do esquema.
-- Uma instrução por linha: o extrator do Hibernate não junta linhas.
-- A carga cobre todos os valores possíveis dos enums, com e sem dado opcional.

-- Clientes: os três gêneros, com e sem e-mail, nascimentos em meses variados,
-- cadastros em meses variados (para o relatório de novatos e de aniversariantes).
INSERT INTO cliente (id, nome_completo, genero, email, telefone, data_nascimento, data_cadastro) VALUES (1, 'Ana Maria da Silva', 'FEMININO', 'ana@exemplo.com', '(84) 99999-0001', DATE '1990-10-12', TIMESTAMP '2026-09-05 08:30:00');
INSERT INTO cliente (id, nome_completo, genero, email, telefone, data_nascimento, data_cadastro) VALUES (2, 'Bruno Costa Medeiros', 'MASCULINO', 'bruno@exemplo.com', '(84) 99999-0002', DATE '1985-11-03', TIMESTAMP '2026-10-05 09:15:00');
INSERT INTO cliente (id, nome_completo, genero, email, telefone, data_nascimento, data_cadastro) VALUES (3, 'Carla Souza Lima', 'FEMININO', null, '(84) 99999-0003', DATE '2000-10-25', TIMESTAMP '2026-10-14 14:40:00');
INSERT INTO cliente (id, nome_completo, genero, email, telefone, data_nascimento, data_cadastro) VALUES (4, 'Diego Alves Pereira', 'MASCULINO', null, '(84) 99999-0004', DATE '1978-06-30', TIMESTAMP '2026-06-10 10:05:00');
INSERT INTO cliente (id, nome_completo, genero, email, telefone, data_nascimento, data_cadastro) VALUES (5, 'Elisa Nunes Barbosa', 'NAO_INFORMADO', 'elisa@exemplo.com', '(84) 99999-0005', DATE '1995-02-14', TIMESTAMP '2026-07-20 17:25:00');
INSERT INTO cliente (id, nome_completo, genero, email, telefone, data_nascimento, data_cadastro) VALUES (6, 'Fabio Ramos Teixeira', 'NAO_INFORMADO', null, '(84) 99999-0006', DATE '2002-10-02', TIMESTAMP '2026-10-16 07:50:00');
INSERT INTO cliente (id, nome_completo, genero, email, telefone, data_nascimento, data_cadastro) VALUES (7, 'Helena Duarte Almeida', 'FEMININO', 'helena@exemplo.pt', '+351 912 345 678', DATE '1988-03-22', TIMESTAMP '2026-05-12 11:10:00');

-- Endereço em tabela própria: um por cliente; o país cobre os dois valores mais
-- prováveis na loja, Brasil e Portugal.
INSERT INTO endereco (id, cliente_id, rua, bairro, numero, cidade, cep, pais) VALUES (1, 1, 'Rua das Flores', 'Centro', '10', 'Açu', '59650000', 'BRASIL');
INSERT INTO endereco (id, cliente_id, rua, bairro, numero, cidade, cep, pais) VALUES (2, 2, 'Rua Gamaliel Martins Bezerra', 'Alto da Alegria', '587', 'Angicos', '59515000', 'BRASIL');
INSERT INTO endereco (id, cliente_id, rua, bairro, numero, cidade, cep, pais) VALUES (3, 3, 'Avenida Abel Cabral', 'Nova Parnamirim', '100', 'Parnamirim', '59151250', 'BRASIL');
INSERT INTO endereco (id, cliente_id, rua, bairro, numero, cidade, cep, pais) VALUES (4, 4, 'Rua do Sol', 'Boa Vista', '45', 'Mossoró', '59600000', 'BRASIL');
INSERT INTO endereco (id, cliente_id, rua, bairro, numero, cidade, cep, pais) VALUES (5, 5, 'Rua das Acácias', 'Centro', '220', 'Caicó', '59300000', 'BRASIL');
INSERT INTO endereco (id, cliente_id, rua, bairro, numero, cidade, cep, pais) VALUES (6, 6, 'Rua Projetada', 'Santa Maria', '9', 'Natal', '59000000', 'BRASIL');
INSERT INTO endereco (id, cliente_id, rua, bairro, numero, cidade, cep, pais) VALUES (7, 7, 'Rua Augusta', 'Baixa', '120', 'Lisboa', '1100-053', 'PORTUGAL');

-- Ficha clínica: todas as larguras, pisadas, temperaturas e odores; joanete nos
-- dois valores; visita ao podólogo informada e em branco. Os clientes 6 e 7 não
-- têm ficha.
INSERT INTO ficha_clinica_do_pe (id, cliente_id, comprimento_cm, largura, pisada, numero_preferido_br, marca_preferida, temperatura, odor, data_ultima_visita_podologo, tem_joanete) VALUES (1, 1, 24.50, 'NORMAL', 'NEUTRA', 37, 'PéFrioSports', 'FRIO', 'LEVE', DATE '2026-08-15', false);
INSERT INTO ficha_clinica_do_pe (id, cliente_id, comprimento_cm, largura, pisada, numero_preferido_br, marca_preferida, temperatura, odor, data_ultima_visita_podologo, tem_joanete) VALUES (2, 2, 27.80, 'LARGO', 'SUPINADA', 43, 'PassoFrio', 'QUENTE', 'FORTE', null, true);
INSERT INTO ficha_clinica_do_pe (id, cliente_id, comprimento_cm, largura, pisada, numero_preferido_br, marca_preferida, temperatura, odor, data_ultima_visita_podologo, tem_joanete) VALUES (3, 3, 23.10, 'ESTREITO', 'PRONADA', 36, 'PéFrioSports', 'NORMAL', 'SEM_ODOR', DATE '2026-09-20', false);
INSERT INTO ficha_clinica_do_pe (id, cliente_id, comprimento_cm, largura, pisada, numero_preferido_br, marca_preferida, temperatura, odor, data_ultima_visita_podologo, tem_joanete) VALUES (4, 4, 26.00, 'LARGO', 'NEUTRA', 41, 'SolaBoa', 'FRIO', 'FORTE', null, true);
INSERT INTO ficha_clinica_do_pe (id, cliente_id, comprimento_cm, largura, pisada, numero_preferido_br, marca_preferida, temperatura, odor, data_ultima_visita_podologo, tem_joanete) VALUES (5, 5, 25.30, 'NORMAL', 'PRONADA', 39, null, 'QUENTE', 'SEM_ODOR', DATE '2026-07-01', false);

-- Produtos: os cinco tipos e os quatro públicos; preço de compra zero em um item.
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro) VALUES (1, 'Tênis Corrida X', 'PéFrioSports', 'TENIS', 'UNISSEX', 42, 120.00, 259.90, DATE '2026-10-14');
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro) VALUES (2, 'Sandália Verão', 'PéFrioSports', 'SANDALIA', 'FEMININO', 37, 45.00, 99.90, DATE '2026-10-13');
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro) VALUES (3, 'Bota Campo', 'PéFrioSports', 'BOTA', 'MASCULINO', 41, 180.00, 349.90, DATE '2026-10-12');
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro) VALUES (4, 'Sapatênis Casual', 'PéFrioSports', 'SAPATENIS', 'MASCULINO', 40, 95.00, 189.90, DATE '2026-10-11');
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro) VALUES (5, 'Chinelo Praia', 'PéFrioSports', 'CHINELO', 'UNISSEX', 39, 15.00, 39.90, DATE '2026-10-10');
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro) VALUES (6, 'Tênis Infantil', 'PéFrioSports', 'TENIS', 'INFANTIL', 28, 60.00, 149.90, DATE '2026-09-30');
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro) VALUES (7, 'Chinelo Infantil', 'SolaBoa', 'CHINELO', 'INFANTIL', 30, 0.00, 29.90, DATE '2026-09-15');
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro) VALUES (8, 'Sandália Unissex', 'SolaBoa', 'SANDALIA', 'UNISSEX', 38, 50.00, 109.90, DATE '2026-08-01');
INSERT INTO produto (id, nome, marca, tipo, genero_alvo, numero_br, preco_compra, preco_venda, data_cadastro) VALUES (9, 'Tênis Balenciaga de Coleção', 'Balenciaga', 'TENIS', 'UNISSEX', 43, 900000.00, 1500000.00, DATE '2026-10-09');

-- Foto do produto: uma com imagem e miniatura, outra com imagem e sem miniatura,
-- para exercitar o marcador da listagem. Conteúdo: um PNG de 1 por 1 pixel.
INSERT INTO produto_foto (id, produto_id, conteudo, content_type, miniatura, miniatura_content_type) VALUES (1, 1, X'89504E470D0A1A0A0000000D49484452000000010000000108060000001F15C4890000000D4944415478DA6364F8CF500F00038601805A347D6B0000000049454E44AE426082', 'image/png', X'89504E470D0A1A0A0000000D49484452000000010000000108060000001F15C4890000000D4944415478DA6364F8CF500F00038601805A347D6B0000000049454E44AE426082', 'image/png');
INSERT INTO produto_foto (id, produto_id, conteudo, content_type, miniatura, miniatura_content_type) VALUES (2, 2, X'89504E470D0A1A0A0000000D49484452000000010000000108060000001F15C4890000000D4944415478DA6364F8CF500F00038601805A347D6B0000000049454E44AE426082', 'image/png', null, null);

-- Pagamentos: as três formas, com o dado de cada uma e com o campo vazio.
INSERT INTO pagamento_do_pedido (id, forma, cartao_ultimos_digitos, chave_pix, valor_pago) VALUES (1, 'PIX', null, 'ana@exemplo.com', null);
INSERT INTO pagamento_do_pedido (id, forma, cartao_ultimos_digitos, chave_pix, valor_pago) VALUES (2, 'DINHEIRO', null, null, 349.90);
INSERT INTO pagamento_do_pedido (id, forma, cartao_ultimos_digitos, chave_pix, valor_pago) VALUES (3, 'CARTAO_CREDITO', '1234', null, null);
INSERT INTO pagamento_do_pedido (id, forma, cartao_ultimos_digitos, chave_pix, valor_pago) VALUES (4, 'PIX', null, '85999990000', null);
INSERT INTO pagamento_do_pedido (id, forma, cartao_ultimos_digitos, chave_pix, valor_pago) VALUES (5, 'DINHEIRO', null, null, 379.80);
INSERT INTO pagamento_do_pedido (id, forma, cartao_ultimos_digitos, chave_pix, valor_pago) VALUES (6, 'CARTAO_CREDITO', '9876', null, null);

-- Pedidos: espalhados por cinco meses, para o faturamento de doze meses. O
-- cliente 1 tem dois pedidos; os clientes 6 e 7 não têm nenhum.
INSERT INTO pedido (id, cliente_id, data_pedido, pagamento_id) VALUES (1, 1, TIMESTAMP '2026-09-05 10:30:00', 1);
INSERT INTO pedido (id, cliente_id, data_pedido, pagamento_id) VALUES (2, 2, TIMESTAMP '2026-10-05 15:00:00', 2);
INSERT INTO pedido (id, cliente_id, data_pedido, pagamento_id) VALUES (3, 1, TIMESTAMP '2026-06-20 09:00:00', 3);
INSERT INTO pedido (id, cliente_id, data_pedido, pagamento_id) VALUES (4, 3, TIMESTAMP '2026-10-15 11:20:00', 4);
INSERT INTO pedido (id, cliente_id, data_pedido, pagamento_id) VALUES (5, 4, TIMESTAMP '2026-08-10 16:45:00', 5);
INSERT INTO pedido (id, cliente_id, data_pedido, pagamento_id) VALUES (6, 5, TIMESTAMP '2026-07-30 14:10:00', 6);

-- Itens: os oito produtos aparecem; um item guarda valor unitário diferente do
-- preço atual, para mostrar que o histórico do pedido é preservado.
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (1, 1, 1, 1, 259.90);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (2, 1, 5, 2, 39.90);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (3, 2, 3, 1, 349.90);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (4, 3, 2, 3, 99.90);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (5, 3, 6, 1, 149.90);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (6, 4, 4, 2, 189.90);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (7, 5, 8, 1, 109.90);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (8, 5, 7, 4, 29.90);
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, valor_unitario) VALUES (9, 6, 1, 1, 249.90);
