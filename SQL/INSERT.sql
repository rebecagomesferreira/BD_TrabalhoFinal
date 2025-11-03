use empresadereciclagem;
--
-- INSERTS: Entidades Independentes
--
INSERT INTO grupo_usuarios (id_grupo, nome_grupo, descricao, prioridade) VALUES
('ADM', 'Administrador', 'Acesso total', 1),
('GER', 'Gerente', 'Gerencia operações', 2),
('OPE', 'Operador', 'Acesso básico', 3),
('VIS', 'Visualizador', 'Somente visualização', 4);
INSERT INTO FORNECEDOR 
(id_fornecedor, cpf_cnpj, nome, telefone, tipo_fornecedor)
VALUES
(gerar_id_fornecedor(), '123.456.789-01', 'Reciclagem Brasil LTDA', '11987654321', 'Empresa'),
(gerar_id_fornecedor(), '987.654.321-00', 'Coleta Sustentável', '11912345678', 'Empresa'),
(gerar_id_fornecedor(), '111.222.333-44', 'João da Silva', '11955554444', 'Pessoa Física'),
(gerar_id_fornecedor(), '222.333.444-55', 'EcoMateriais', '11966667777', 'Empresa'),
(gerar_id_fornecedor(), '333.444.555-66', 'Maria Oliveira', '11977778888', 'Pessoa Física');
SELECT * FROM FORNECEDOR;

INSERT INTO MATERIAL 
(id_material, tipo, descricao, preco_kg)
VALUES
(gerar_id_material('Plástico'), 'Plástico', 'Plástico PET transparente', 2.50),
(gerar_id_material('Papel'), 'Papel', 'Papelão ondulado', 1.20),
(gerar_id_material('Metal'), 'Metal', 'Alumínio de latas', 5.00),
(gerar_id_material('Vidro'), 'Vidro', 'Vidro verde', 3.00),
(gerar_id_material('Orgânico'), 'Orgânico', 'Restos de comida', 0.80);
SELECT * FROM MATERIAL;

INSERT INTO ESTOQUE 
(localizacao, capacidade, nivel_atual, nivel_minimo)
VALUES
('Galpão Central', 1000, 500, 100),
('Depósito Norte', 500, 200, 50),
('Depósito Sul', 300, 100, 30),
('Armazém Leste', 600, 250, 60),
('Armazém Oeste', 400, 150, 40);
SELECT * FROM ESTOQUE;

INSERT INTO FUNCIONARIO 
(id_funcionario, nome, cargo, endereco, cpf_cnpj, salario, idade, data_nascimento, telefone, email)
VALUES
(gerar_id_funcionario('Gerente'), 'Carlos Pereira', 'Gerente', 'Rua A, 100', '555.666.777-88', 5000.00, 40, '1985-05-15', '11999990000', 'carlos@empresa.com'),
(gerar_id_funcionario('Coletora'), 'Ana Souza', 'Coletora', 'Rua B, 200', '666.777.888-99', 2500.00, 30, '1993-02-10', '11988881111', 'ana@empresa.com'),
(gerar_id_funcionario('Separador'), 'Roberto Lima', 'Separador', 'Rua C, 300', '777.888.999-00', 2200.00, 28, '1995-07-22', '11977772222', 'roberto@empresa.com'),
(gerar_id_funcionario('Atendente'), 'Fernanda Costa', 'Atendente', 'Rua D, 400', '888.999.000-11', 2100.00, 35, '1988-11-05', '11966663333', 'fernanda@empresa.com'),
(gerar_id_funcionario('Motorista'), 'Marcos Pinto', 'Motorista', 'Rua E, 500', '999.000.111-22', 2300.00, 33, '1990-09-12', '11955554444', 'marcos@empresa.com');
SELECT * FROM FUNCIONARIO;

INSERT INTO CLIENTE 
(id_cliente, nome, cpf_cnpj, telefone, email)
VALUES
(gerar_id_cliente(), 'Empresa Verde LTDA', '111.222.333/0001-44', '11911112222', 'contato@empresaverde.com'),
(gerar_id_cliente(), 'João Martins', '222.333.444-55', '11922223333', 'joao@gmail.com'),
(gerar_id_cliente(), 'Lara Gomes', '333.444.555-66', '11933334444', 'lara@gmail.com'),
(gerar_id_cliente(), 'Eco Brasil', '444.555.666/0001-77', '11944445555', 'vendas@ecobrasil.com'),
(gerar_id_cliente(), 'Carlos Nunes', '555.666.777-88', '11955556666', 'carlos@gmail.com');
SELECT * FROM CLIENTE;

INSERT INTO TIPO_PAGAMENTO (descricao, parcelamento, num_parcelas) VALUES
('Dinheiro', FALSE, NULL),
('Cartão de Crédito', TRUE, 3),
('Boleto', FALSE, NULL),
('Pix', FALSE, NULL),
('Cartão Débito', FALSE, NULL);
SELECT * FROM TIPO_PAGAMENTO;

--
-- INSERTS: Entidades Dependentes (Corrigidas para usar nomes/chaves estáveis)


--  Primeiro, pegamos os ids dos estoques

--  Porque se fizermos SELECT direto dentro do VALUES do INSERT,
-- o MySQL vai reclamar (erro 1442) já que a trigger tenta atualizar
-- a mesma tabela ESTOQUE ao mesmo tempo.
SET @galpao_central = (SELECT id_estoque FROM ESTOQUE WHERE localizacao = 'Galpão Central');
SET @deposito_norte = (SELECT id_estoque FROM ESTOQUE WHERE localizacao = 'Depósito Norte');
SET @deposito_sul   = (SELECT id_estoque FROM ESTOQUE WHERE localizacao = 'Depósito Sul');
SET @armazem_leste  = (SELECT id_estoque FROM ESTOQUE WHERE localizacao = 'Armazém Leste');
SET @armazem_oeste  = (SELECT id_estoque FROM ESTOQUE WHERE localizacao = 'Armazém Oeste');


--  Pegamos os ids dos materiais

-- Mesma razão: evitar SELECT dentro do VALUES que acessa tabelas que 
-- podem ser afetadas por triggers.
SET @plastico = (SELECT id_material FROM MATERIAL WHERE tipo = 'Plástico');
SET @papel    = (SELECT id_material FROM MATERIAL WHERE tipo = 'Papel');
SET @metal    = (SELECT id_material FROM MATERIAL WHERE tipo = 'Metal');
SET @vidro    = (SELECT id_material FROM MATERIAL WHERE tipo = 'Vidro');
SET @organico = (SELECT id_material FROM MATERIAL WHERE tipo = 'Orgânico');


--  Inserindo os materiais no estoque

-- Usamos as variáveis em vez de SELECT dentro do VALUES.
-- Assim, o INSERT não acessa diretamente ESTOQUE durante a execução,
-- permitindo que a trigger atualize o nivel_atual sem erro.
INSERT INTO ESTOQUE_ARMAZENA_MATERIAL (id_estoque, id_material, data_entrega) VALUES
(@galpao_central, @plastico, '2025-09-20'),
(@galpao_central, @papel, '2025-09-20'),
(@deposito_norte, @metal, '2025-09-21'),
(@deposito_norte, @vidro, '2025-09-21'),
(@deposito_sul, @organico, '2025-09-22'),
(@armazem_leste, @plastico, '2025-09-22'),
(@armazem_leste, @metal, '2025-09-23'),
(@armazem_oeste, @papel, '2025-09-23'),
(@armazem_oeste, @vidro, '2025-09-24'),
(@armazem_oeste, @organico, '2025-09-24');


SELECT * FROM ESTOQUE_ARMAZENA_MATERIAL;

-- Já estava correto no seu script (usando nome)
INSERT INTO DOCUMENTO_COLETA 
(id_doc_coleta, data, local, id_funcionario, id_fornecedor)
VALUES
(gerar_id_coleta(), '2025-09-20', 'Galpão Central',
 (SELECT id_funcionario FROM FUNCIONARIO WHERE nome='Ana Souza' LIMIT 1),
 (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Reciclagem Brasil LTDA' LIMIT 1)),

(gerar_id_coleta(), '2025-09-21', 'Depósito Norte',
 (SELECT id_funcionario FROM FUNCIONARIO WHERE nome='Roberto Lima' LIMIT 1),
 (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Coleta Sustentável' LIMIT 1)),

(gerar_id_coleta(), '2025-09-22', 'Depósito Sul',
 (SELECT id_funcionario FROM FUNCIONARIO WHERE nome='Fernanda Costa' LIMIT 1),
 (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='João da Silva' LIMIT 1)),

(gerar_id_coleta(), '2025-09-23', 'Armazém Leste',
 (SELECT id_funcionario FROM FUNCIONARIO WHERE nome='Ana Souza' LIMIT 1),
 (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='EcoMateriais' LIMIT 1)),

(gerar_id_coleta(), '2025-09-24', 'Armazém Oeste',
 (SELECT id_funcionario FROM FUNCIONARIO WHERE nome='Marcos Pinto' LIMIT 1),
 (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Maria Oliveira' LIMIT 1));
SELECT * FROM DOCUMENTO_COLETA;



INSERT INTO FORNECEDOR_FORNECE_MATERIAL (id_fornecedor, id_material, id_doc_coleta) VALUES
((SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Reciclagem Brasil LTDA'), 
(SELECT id_material FROM MATERIAL WHERE tipo='Plástico'), 
(SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-20' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Reciclagem Brasil LTDA'))),

((SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Reciclagem Brasil LTDA'), 
(SELECT id_material FROM MATERIAL WHERE tipo='Papel'), 
(SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-20' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Reciclagem Brasil LTDA'))),

((SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Coleta Sustentável'), 
(SELECT id_material FROM MATERIAL WHERE tipo='Metal'), 
(SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-21' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Coleta Sustentável'))),

((SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Coleta Sustentável'), 
(SELECT id_material FROM MATERIAL WHERE tipo='Vidro'), 
(SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-21' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Coleta Sustentável'))),

((SELECT id_fornecedor FROM FORNECEDOR WHERE nome='João da Silva'), 
(SELECT id_material FROM MATERIAL WHERE tipo='Orgânico'), 
(SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-22' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='João da Silva'))),

((SELECT id_fornecedor FROM FORNECEDOR WHERE nome='EcoMateriais'),
(SELECT id_material FROM MATERIAL WHERE tipo='Plástico'), 
(SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-23' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='EcoMateriais'))),

((SELECT id_fornecedor FROM FORNECEDOR WHERE nome='EcoMateriais'), 
(SELECT id_material FROM MATERIAL WHERE tipo='Metal'), 
(SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-23' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='EcoMateriais'))),

((SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Maria Oliveira'), 
(SELECT id_material FROM MATERIAL WHERE tipo='Papel'), 
(SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-24' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Maria Oliveira'))),

((SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Maria Oliveira'), 
(SELECT id_material FROM MATERIAL WHERE tipo='Vidro'), 
(SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-24' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Maria Oliveira'))),

((SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Maria Oliveira'), 
(SELECT id_material FROM MATERIAL WHERE tipo='Orgânico'), 
(SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-24' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Maria Oliveira')));
SELECT * FROM FORNECEDOR_FORNECE_MATERIAL;

-- Corrigido: Usando 'data' + 'nome' (para doc), 'tipo' (para material) e 'nome' (para func)
INSERT INTO DOC_COLETA_CONTEM_MATERIAL (id_doc_coleta, id_material, id_funcionario, quantidade) VALUES
((SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-20' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Reciclagem Brasil LTDA')), 
(SELECT id_material FROM Material WHERE tipo = 'Plástico'), 
(SELECT id_funcionario FROM Funcionario WHERE nome = 'Ana Souza'), 100),

((SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-20' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Reciclagem Brasil LTDA')), 
(SELECT id_material FROM Material WHERE tipo = 'Papel'), 
(SELECT id_funcionario FROM Funcionario WHERE nome = 'Ana Souza'), 200),

((SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-21' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Coleta Sustentável')), 
(SELECT id_material FROM Material WHERE tipo = 'Metal'), 
(SELECT id_funcionario FROM Funcionario WHERE nome = 'Roberto Lima'), 150), 

((SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-21' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Coleta Sustentável')), 
(SELECT id_material FROM Material WHERE tipo = 'Vidro'), 
(SELECT id_funcionario FROM Funcionario WHERE nome = 'Roberto Lima'), 80),

((SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-22' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='João da Silva')), 
(SELECT id_material FROM Material WHERE tipo = 'Orgânico'), 
(SELECT id_funcionario FROM Funcionario WHERE nome = 'Fernanda Costa'), 50), 

((SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-23' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='EcoMateriais')), 
(SELECT id_material FROM Material WHERE tipo = 'Plástico'), 
(SELECT id_funcionario FROM Funcionario WHERE nome = 'Ana Souza'), 120),

((SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-23' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='EcoMateriais')), 
(SELECT id_material FROM Material WHERE tipo = 'Metal'), 
(SELECT id_funcionario FROM Funcionario WHERE nome = 'Ana Souza'), 90),

((SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-24' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Maria Oliveira')), 
(SELECT id_material FROM Material WHERE tipo = 'Papel'), 
(SELECT id_funcionario FROM Funcionario WHERE nome = 'Marcos Pinto'), 60),

((SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-24' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Maria Oliveira')), 
(SELECT id_material FROM Material WHERE tipo = 'Vidro'),
(SELECT id_funcionario FROM Funcionario WHERE nome = 'Marcos Pinto'), 70),

((SELECT id_doc_coleta FROM Documento_Coleta WHERE data = '2025-09-24' AND id_fornecedor = (SELECT id_fornecedor FROM FORNECEDOR WHERE nome='Maria Oliveira')), 
(SELECT id_material FROM Material WHERE tipo = 'Orgânico'), 
(SELECT id_funcionario FROM Funcionario WHERE nome = 'Marcos Pinto'), 40);
SELECT * FROM DOC_COLETA_CONTEM_MATERIAL;


INSERT INTO DOCUMENTO_VENDA 
(id_doc_venda, data, valor, id_cliente, id_funcionario, id_tipo_pagamento)
VALUES
(gerar_id_venda(), '2025-09-21', 500.00,
 (SELECT id_cliente FROM CLIENTE WHERE nome='Empresa Verde LTDA' LIMIT 1),
 (SELECT id_funcionario FROM FUNCIONARIO WHERE nome='Fernanda Costa' LIMIT 1), 
 (SELECT id_tipo_pagamento FROM TIPO_PAGAMENTO WHERE descricao = 'Dinheiro')),

(gerar_id_venda(), '2025-09-22', 300.00,
 (SELECT id_cliente FROM CLIENTE WHERE nome='João Martins' LIMIT 1),
 (SELECT id_funcionario FROM FUNCIONARIO WHERE nome='Fernanda Costa' LIMIT 1), 
 (SELECT id_tipo_pagamento FROM TIPO_PAGAMENTO WHERE descricao = 'Cartão de Crédito')),

(gerar_id_venda(), '2025-09-23', 450.00,
 (SELECT id_cliente FROM CLIENTE WHERE nome='Lara Gomes' LIMIT 1),
 (SELECT id_funcionario FROM FUNCIONARIO WHERE nome='Marcos Pinto' LIMIT 1), 
 (SELECT id_tipo_pagamento FROM TIPO_PAGAMENTO WHERE descricao = 'Boleto')),

(gerar_id_venda(), '2025-09-24', 600.00,
 (SELECT id_cliente FROM CLIENTE WHERE nome='Eco Brasil' LIMIT 1),
 (SELECT id_funcionario FROM FUNCIONARIO WHERE nome='Ana Souza' LIMIT 1), 
 (SELECT id_tipo_pagamento FROM TIPO_PAGAMENTO WHERE descricao = 'Pix')),

(gerar_id_venda(), '2025-09-25', 350.00,
 (SELECT id_cliente FROM CLIENTE WHERE nome='Carlos Nunes' LIMIT 1),
 (SELECT id_funcionario FROM FUNCIONARIO WHERE nome='Roberto Lima' LIMIT 1), 
 (SELECT id_tipo_pagamento FROM TIPO_PAGAMENTO WHERE descricao = 'Cartão Débito'));
SELECT * FROM DOCUMENTO_VENDA;


-- Corrigido: Usando 'data' + 'nome' (para doc) e 'tipo' (para material)
INSERT INTO ITEM_VENDA (id_doc_venda, id_material, quantidade, preco_kg) VALUES
( (SELECT id_doc_venda FROM DOCUMENTO_VENDA WHERE data = '2025-09-21' AND id_cliente = (SELECT id_cliente FROM CLIENTE WHERE nome='Empresa Verde LTDA')), 
(SELECT id_material FROM Material WHERE tipo = 'Plástico'), 
50, 2.50),

((SELECT id_doc_venda FROM DOCUMENTO_VENDA WHERE data = '2025-09-21' AND id_cliente = (SELECT id_cliente FROM CLIENTE WHERE nome='Empresa Verde LTDA')), 
(SELECT id_material FROM Material WHERE tipo = 'Papel'), 
100, 1.20),

((SELECT id_doc_venda FROM DOCUMENTO_VENDA WHERE data = '2025-09-22' AND id_cliente = (SELECT id_cliente FROM CLIENTE WHERE nome='João Martins')), 
(SELECT id_material FROM Material WHERE tipo = 'Metal'), 
60, 5.00),

((SELECT id_doc_venda FROM DOCUMENTO_VENDA WHERE data = '2025-09-22' AND id_cliente = (SELECT id_cliente FROM CLIENTE WHERE nome='João Martins')), 
(SELECT id_material FROM Material WHERE tipo = 'Vidro'), 
40, 3.00),

((SELECT id_doc_venda FROM DOCUMENTO_VENDA WHERE data = '2025-09-23' AND id_cliente = (SELECT id_cliente FROM CLIENTE WHERE nome='Lara Gomes')), 
(SELECT id_material FROM Material WHERE tipo = 'Orgânico'), 
50, 0.80),

((SELECT id_doc_venda FROM DOCUMENTO_VENDA WHERE data = '2025-09-23' AND id_cliente = (SELECT id_cliente FROM CLIENTE WHERE nome='Lara Gomes')), 
(SELECT id_material FROM Material WHERE tipo = 'Plástico'), 
70, 2.50),

((SELECT id_doc_venda FROM DOCUMENTO_VENDA WHERE data = '2025-09-24' AND id_cliente = (SELECT id_cliente FROM CLIENTE WHERE nome='Eco Brasil')), 
(SELECT id_material FROM Material WHERE tipo = 'Metal'), 
50, 5.00),

((SELECT id_doc_venda FROM DOCUMENTO_VENDA WHERE data = '2025-09-25' AND id_cliente = (SELECT id_cliente FROM CLIENTE WHERE nome='Carlos Nunes')), 
(SELECT id_material FROM Material WHERE tipo = 'Papel'), 
80, 1.20),

((SELECT id_doc_venda FROM DOCUMENTO_VENDA WHERE data = '2025-09-25' AND id_cliente = (SELECT id_cliente FROM CLIENTE WHERE nome='Carlos Nunes')), 
(SELECT id_material FROM Material WHERE tipo = 'Vidro'), 
60, 3.00),

((SELECT id_doc_venda FROM DOCUMENTO_VENDA WHERE data = '2025-09-25' AND id_cliente = (SELECT id_cliente FROM CLIENTE WHERE nome='Carlos Nunes')), 
(SELECT id_material FROM Material WHERE tipo = 'Orgânico'), 
30, 0.80);
SELECT * FROM ITEM_VENDA;

-- Corrigido: Usando 'nome' para funcionario e 'nome' para cliente
INSERT INTO FUNCIONARIO_ATENDE_CLIENTE (id_funcionario, id_cliente) VALUES
((SELECT id_funcionario FROM Funcionario WHERE nome = 'Fernanda Costa'), 
(SELECT id_cliente FROM CLIENTE WHERE nome = 'Empresa Verde LTDA')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Fernanda Costa'), 
(SELECT id_cliente FROM CLIENTE WHERE nome = 'João Martins')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Marcos Pinto'), 
(SELECT id_cliente FROM CLIENTE WHERE nome = 'Lara Gomes')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Ana Souza'), 
(SELECT id_cliente FROM CLIENTE WHERE nome = 'Eco Brasil')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Roberto Lima'), 
(SELECT id_cliente FROM CLIENTE WHERE nome = 'Carlos Nunes'));
SELECT * FROM FUNCIONARIO_ATENDE_CLIENTE;



INSERT INTO FUNCIONARIO_RECEBE_MATERIAL (id_funcionario, id_material) VALUES
((SELECT id_funcionario FROM Funcionario WHERE nome = 'Ana Souza'), 
(SELECT id_material FROM Material WHERE tipo = 'Plástico')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Roberto Lima'), 
(SELECT id_material FROM Material WHERE tipo = 'Metal')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Ana Souza'), 
(SELECT id_material FROM Material WHERE tipo = 'Papel')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Marcos Pinto'), 
(SELECT id_material FROM Material WHERE tipo = 'Vidro')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Fernanda Costa'), 
(SELECT id_material FROM Material WHERE tipo = 'Orgânico'));
SELECT * FROM FUNCIONARIO_RECEBE_MATERIAL;


-- Corrigido: Usando 'nome' para funcionario e 'tipo' para material
INSERT INTO FUNCIONARIO_SEPARA_MATERIAL (id_funcionario, id_material) VALUES
((SELECT id_funcionario FROM Funcionario WHERE nome = 'Roberto Lima'), 
(SELECT id_material FROM Material WHERE tipo = 'Plástico')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Roberto Lima'), 
(SELECT id_material FROM Material WHERE tipo = 'Papel')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Ana Souza'), 
(SELECT id_material FROM Material WHERE tipo = 'Metal')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Fernanda Costa'), 
(SELECT id_material FROM Material WHERE tipo = 'Vidro')),

((SELECT id_funcionario FROM Funcionario WHERE nome = 'Marcos Pinto'),
(SELECT id_material FROM Material WHERE tipo = 'Orgânico'));
SELECT * FROM FUNCIONARIO_SEPARA_MATERIAL;



