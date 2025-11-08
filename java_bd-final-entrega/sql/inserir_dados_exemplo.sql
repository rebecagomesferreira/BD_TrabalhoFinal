-- Script: inserir_dados_exemplo.sql
USE EmpresaDeReciclagem;

INSERT INTO material (tipo, descricao, preco_kg) VALUES
('Plástico', 'PET transparente', 1.20),
('Papel', 'Papelão', 0.50),
('Metal', 'Alumínio', 3.50);

INSERT INTO cliente (nome, cpf, endereco) VALUES
('Maria Silva', '000.000.000-00', 'Rua A, 123'),
('João Souza', '111.111.111-11', 'Av. B, 456');

INSERT INTO funcionario (nome, cargo) VALUES
('Carlos', 'Operador'),
('Ana', 'Gerente');
