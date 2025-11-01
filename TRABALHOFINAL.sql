-- CREATE DATABASE IF NOT EXISTS EmpresaDeReciclagem;
USE EmpresaDeReciclagem;

-- tabela grupo de usuários
/*
CREATE TABLE IF NOT EXISTS grupo_usuarios (
    id_grupo CHAR(10) PRIMARY KEY,       -- ID customizado
    nome_grupo VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT NOT NULL,             -- Permissões do grupo
    prioridade INT NOT NULL              -- Hierarquia do grupo
);



-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome_usuario VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    id_cliente CHAR(15) NULL,
    id_funcionario CHAR(15) NULL,
    id_grupo CHAR(10) NOT NULL,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_grupo) REFERENCES grupo_usuarios(id_grupo)
);



-- tabela fornecedor
CREATE TABLE IF NOT EXISTS FORNECEDOR (
  id_fornecedor CHAR(15) PRIMARY KEY,
  cpf_cnpj VARCHAR(20) NOT NULL UNIQUE,
  nome VARCHAR(255) NOT NULL,
  telefone VARCHAR(20),
  tipo_fornecedor VARCHAR(50)
);


CREATE TABLE IF NOT EXISTS MATERIAL (
  id_material CHAR(15) PRIMARY KEY,
  tipo VARCHAR(30) NOT NULL,
  descricao VARCHAR(255),
  preco_kg DECIMAL(10,2) NOT NULL
);

-- tabela estoque
CREATE TABLE IF NOT EXISTS ESTOQUE (
  id_estoque INT PRIMARY KEY AUTO_INCREMENT,
  localizacao VARCHAR(255) NOT NULL,
  capacidade INT NOT NULL,
  nivel_atual INT,
  nivel_minimo INT
);

-- tabela estoque armazena material
CREATE TABLE IF NOT EXISTS ESTOQUE_ARMAZENA_MATERIAL (
  id_estoque INT NOT NULL,
  id_material CHAR(15) NOT NULL,
  data_entrega DATE NOT NULL,
  PRIMARY KEY (id_estoque, id_material),
  CONSTRAINT fk_estoque FOREIGN KEY (id_estoque)
    REFERENCES ESTOQUE(id_estoque)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_mat_armazenado FOREIGN KEY (id_material)
    REFERENCES MATERIAL(id_material)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- tabela funcionario
CREATE TABLE IF NOT EXISTS FUNCIONARIO (
  id_funcionario CHAR(15) PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  cargo VARCHAR(100) NOT NULL,
  endereco VARCHAR(255),
  cpf_cnpj VARCHAR(20) NOT NULL UNIQUE,
  salario DECIMAL(10,2) NOT NULL,
  idade INT,
  data_nascimento DATE,
  telefone VARCHAR(20),
  email VARCHAR(255)
);

-- tabela cliente
CREATE TABLE IF NOT EXISTS CLIENTE (
  id_cliente CHAR(15) PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  cpf_cnpj VARCHAR(20) NOT NULL UNIQUE,
  telefone VARCHAR(20),
  email VARCHAR(255)
);

-- tabela documento coleta
CREATE TABLE IF NOT EXISTS DOCUMENTO_COLETA (
  id_doc_coleta CHAR(15) PRIMARY KEY,
  data DATE NOT NULL,
  local VARCHAR(255) NOT NULL,
  id_funcionario CHAR(15) NOT NULL,
  id_fornecedor CHAR(15) NOT NULL,
  CONSTRAINT fk_doc_coleta_func FOREIGN KEY (id_funcionario)
    REFERENCES FUNCIONARIO(id_funcionario)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_doc_coleta_forn FOREIGN KEY (id_fornecedor)
    REFERENCES FORNECEDOR(id_fornecedor)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

-- tabela doc_coleta contem material (RELACIONAMENTO TERNÁRIO)
CREATE TABLE IF NOT EXISTS DOC_COLETA_CONTEM_MATERIAL (
  id_doc_coleta CHAR(15) NOT NULL,
  id_material CHAR(15) NOT NULL,
  id_funcionario CHAR(15) NOT NULL,   
  quantidade INT NOT NULL,
  PRIMARY KEY (id_doc_coleta, id_material, id_funcionario),
  CONSTRAINT fk_dc_mat_doc FOREIGN KEY (id_doc_coleta)
    REFERENCES DOCUMENTO_COLETA(id_doc_coleta)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_dc_mat_mat FOREIGN KEY (id_material)
    REFERENCES MATERIAL(id_material)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_dc_mat_func FOREIGN KEY (id_funcionario)
    REFERENCES FUNCIONARIO(id_funcionario)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- tabela fornecedor fornece material
CREATE TABLE IF NOT EXISTS FORNECEDOR_FORNECE_MATERIAL (
  id_fornecedor CHAR(15) NOT NULL,
  id_material CHAR(15) NOT NULL,
  id_doc_coleta CHAR(15) NOT NULL,
  PRIMARY KEY (id_fornecedor, id_material, id_doc_coleta),
  CONSTRAINT fk_fornec FOREIGN KEY (id_fornecedor)
    REFERENCES FORNECEDOR(id_fornecedor)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_material FOREIGN KEY (id_material)
    REFERENCES MATERIAL(id_material)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_doc_coleta FOREIGN KEY (id_doc_coleta)
    REFERENCES DOCUMENTO_COLETA(id_doc_coleta)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- tabela tipo pagamento
CREATE TABLE IF NOT EXISTS TIPO_PAGAMENTO (
  id_tipo_pagamento INT PRIMARY KEY AUTO_INCREMENT,
  descricao VARCHAR(100) NOT NULL,
  parcelamento BOOLEAN DEFAULT FALSE,
  num_parcelas INT
);

-- tabela documento venda
CREATE TABLE IF NOT EXISTS DOCUMENTO_VENDA (
  id_doc_venda CHAR(15) PRIMARY KEY,
  data DATE NOT NULL,
  valor DECIMAL(10,2) NOT NULL,
  id_cliente CHAR(15) NOT NULL,
  id_funcionario CHAR(15) NOT NULL,
  id_tipo_pagamento INT NOT NULL,
  CONSTRAINT fk_doc_venda_cliente FOREIGN KEY (id_cliente)
    REFERENCES CLIENTE(id_cliente)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_doc_venda_func FOREIGN KEY (id_funcionario)
    REFERENCES FUNCIONARIO(id_funcionario)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_doc_venda_pag FOREIGN KEY (id_tipo_pagamento)
    REFERENCES TIPO_PAGAMENTO(id_tipo_pagamento)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

-- tabela item venda (documento venda contem material)
CREATE TABLE IF NOT EXISTS ITEM_VENDA (
  id_doc_venda CHAR(15) NOT NULL,
  id_material CHAR(15) NOT NULL,
  quantidade INT NOT NULL,
  preco_kg DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_doc_venda, id_material),
  CONSTRAINT fk_item_venda_doc FOREIGN KEY (id_doc_venda)
    REFERENCES DOCUMENTO_VENDA(id_doc_venda)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_item_venda_mat FOREIGN KEY (id_material)
    REFERENCES MATERIAL(id_material)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- tabela funcionario atende cliente
CREATE TABLE IF NOT EXISTS FUNCIONARIO_ATENDE_CLIENTE (
  id_funcionario CHAR(15) NOT NULL,
  id_cliente CHAR(15) NOT NULL,
  PRIMARY KEY (id_funcionario, id_cliente),
  CONSTRAINT fk_atende_func FOREIGN KEY (id_funcionario)
    REFERENCES FUNCIONARIO(id_funcionario)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_atende_cliente FOREIGN KEY (id_cliente)
    REFERENCES CLIENTE(id_cliente)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- tabela funcionario recebe material
CREATE TABLE IF NOT EXISTS FUNCIONARIO_RECEBE_MATERIAL (
  id_funcionario CHAR(15) NOT NULL,
  id_material CHAR(15) NOT NULL,
  PRIMARY KEY (id_funcionario, id_material),
  CONSTRAINT fk_recebe_func FOREIGN KEY (id_funcionario)
    REFERENCES FUNCIONARIO(id_funcionario)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_recebe_mat FOREIGN KEY (id_material)
    REFERENCES MATERIAL(id_material)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- tabela funcionario separa material
CREATE TABLE IF NOT EXISTS FUNCIONARIO_SEPARA_MATERIAL (
  id_funcionario CHAR(15) NOT NULL,
  id_material CHAR(15) NOT NULL,
  PRIMARY KEY (id_funcionario, id_material),
  CONSTRAINT fk_separa_func FOREIGN KEY (id_funcionario)
    REFERENCES FUNCIONARIO(id_funcionario)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_separa_mat FOREIGN KEY (id_material)
    REFERENCES MATERIAL(id_material)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- consultas
SELECT nome, telefone
FROM FORNECEDOR
WHERE tipo_fornecedor = 'Empresa';

SELECT localizacao, nivel_atual, nivel_minimo
FROM ESTOQUE
WHERE nivel_atual < nivel_minimo;

SELECT dc.id_doc_coleta, dc.data, f.nome AS funcionario, fr.nome AS fornecedor
FROM DOCUMENTO_COLETA dc
JOIN FUNCIONARIO f ON dc.id_funcionario = f.id_funcionario
JOIN FORNECEDOR fr ON dc.id_fornecedor = fr.id_fornecedor;

SELECT c.nome AS cliente, m.tipo AS material, iv.quantidade, iv.preco_kg
FROM ITEM_VENDA iv
JOIN DOCUMENTO_VENDA dv ON iv.id_doc_venda = dv.id_doc_venda
JOIN CLIENTE c ON dv.id_cliente = c.id_cliente
JOIN MATERIAL m ON iv.id_material = m.id_material;

SELECT c.nome AS cliente, SUM(dv.valor) AS total_gasto
FROM DOCUMENTO_VENDA dv
JOIN CLIENTE c ON dv.id_cliente = c.id_cliente
GROUP BY c.nome;*/

-- atualizações
UPDATE FORNECEDOR
SET telefone = '11999998888'
WHERE id_fornecedor = 'FOR-2025-3408';

UPDATE FUNCIONARIO
SET salario = salario * 1.10
WHERE id_funcionario = 'COL-2025-5764';

UPDATE ITEM_VENDA
SET quantidade = 120
WHERE id_doc_venda = 'VEN-251031-449' AND id_material = 'MAT-PAP-3413';

UPDATE MATERIAL
SET preco_kg = preco_kg * 0.8
WHERE id_material = 'MAT-PLÁ-9968';

-- verificacoes apos updates
SELECT * FROM FORNECEDOR;
SELECT nome, cargo, salario FROM FUNCIONARIO;
SELECT * FROM ITEM_VENDA;
SELECT * FROM MATERIAL;
