-- Script: criar_tabelas_material_cliente_funcionario_venda.sql
-- Ajuste conforme necessidade. Executar no banco EmpresaDeReciclagem

CREATE DATABASE IF NOT EXISTS EmpresaDeReciclagem;
USE EmpresaDeReciclagem;

-- Tabela material
CREATE TABLE IF NOT EXISTS material (
  id_material INT AUTO_INCREMENT PRIMARY KEY,
  tipo VARCHAR(100) NOT NULL,
  descricao VARCHAR(255),
  preco_kg DECIMAL(10,2) DEFAULT 0.00
);

-- Tabela cliente
CREATE TABLE IF NOT EXISTS cliente (
  id_cliente INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(150) NOT NULL,
  cpf VARCHAR(14),
  endereco VARCHAR(255)
);

-- Tabela funcionario
CREATE TABLE IF NOT EXISTS funcionario (
  id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(150) NOT NULL,
  cargo VARCHAR(100)
);

-- Tabela venda (exemplo de relacionamento)
CREATE TABLE IF NOT EXISTS venda (
  id_venda INT AUTO_INCREMENT PRIMARY KEY,
  id_cliente INT,
  data_venda DATETIME DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(10,2) DEFAULT 0.00,
  FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE SET NULL
);

-- Tabela item_venda (itens da venda)
CREATE TABLE IF NOT EXISTS item_venda (
  id_item INT AUTO_INCREMENT PRIMARY KEY,
  id_venda INT,
  id_material INT,
  quantidade DECIMAL(10,3) DEFAULT 0.0,
  preco_unitario DECIMAL(10,2) DEFAULT 0.00,
  FOREIGN KEY (id_venda) REFERENCES venda(id_venda) ON DELETE CASCADE,
  FOREIGN KEY (id_material) REFERENCES material(id_material)
);

