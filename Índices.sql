USE EmpresaDeReciclagem;

--  DOCUMENTO_VENDA → índice por cliente
CREATE INDEX idx_documento_venda_cliente
ON documento_venda (id_cliente);

-- Teste do índice
SELECT * 
FROM documento_venda 
WHERE id_cliente = (SELECT id_cliente FROM cliente LIMIT 1);

--  DOCUMENTO_COLETA → índice por data
CREATE INDEX idx_documento_coleta_data
ON documento_coleta (data);

-- Teste do índice
SELECT * 
FROM documento_coleta 
WHERE data = '2025-09-21';

--  MATERIAL → índice por tipo
CREATE INDEX idx_material_tipo
ON material (tipo);

-- Teste do índice
SELECT * 
FROM material 
WHERE tipo = 'Plástico';

--  CLIENTE → índice por cpf_cnpj
CREATE INDEX idx_cliente_cpf_cnpj
ON cliente (cpf_cnpj);

-- Teste do índice
SELECT * 
FROM cliente 
WHERE cpf_cnpj = '111.222.333/0001-44';

--  FUNCIONARIO_SEPARA_MATERIAL → índice por id_funcionario
CREATE INDEX idx_funcionario_separa_funcionario
ON funcionario_separa_material (id_funcionario);

-- Teste do índice
SELECT * 
FROM funcionario_separa_material 
WHERE id_funcionario = (SELECT id_funcionario FROM funcionario LIMIT 1);
