
USE empresadereciclagem;

-- CRIAÇÃO DE USUÁRIOS E ROLES

CREATE USER IF NOT EXISTS 'admin_master'@'%' IDENTIFIED BY 'admADM-2025-0001';

CREATE ROLE IF NOT EXISTS 'Administrador';
CREATE ROLE IF NOT EXISTS 'Gerente';
CREATE ROLE IF NOT EXISTS 'Operador';
CREATE ROLE IF NOT EXISTS 'Visualizador';


-- PRIVILÉGIOS DAS ROLES

-- Administrador -> total
GRANT ALL PRIVILEGES ON empresadereciclagem.* TO 'Administrador' WITH GRANT OPTION;

-- Gerente -> CRUD + EXECUTE (funções)
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON empresadereciclagem.* TO 'Gerente';

-- Operador -> Inserir, consultar + EXECUTE (funções)
GRANT SELECT, INSERT, EXECUTE ON empresadereciclagem.* TO 'Operador';

-- Visualizador -> somente leitura
GRANT SELECT ON empresadereciclagem.* TO 'Visualizador';


-- USUÁRIOS DE EXEMPLO

CREATE USER IF NOT EXISTS 'carlos_pereira'@'%' IDENTIFIED BY 'opGER-2025-2282';
CREATE USER IF NOT EXISTS 'ana_souza'@'%' IDENTIFIED BY 'opCOL-2025-5208';
CREATE USER IF NOT EXISTS 'roberto_lima'@'%' IDENTIFIED BY 'opSEP-2025-9195';
CREATE USER IF NOT EXISTS 'fernanda_costa'@'%' IDENTIFIED BY 'opATE-2025-0355';
CREATE USER IF NOT EXISTS 'marcos_pinto'@'%' IDENTIFIED BY 'opMOT-2025-4185';

CREATE USER IF NOT EXISTS 'empresa_verde'@'%' IDENTIFIED BY 'cliCLI-2025-9865';
CREATE USER IF NOT EXISTS 'joao_martins'@'%' IDENTIFIED BY 'cliCLI-2025-6771';
CREATE USER IF NOT EXISTS 'lara_gomes'@'%' IDENTIFIED BY 'cliCLI-2025-4260';
CREATE USER IF NOT EXISTS 'eco_brasil'@'%' IDENTIFIED BY 'cliCLI-2025-0988';


-- ASSOCIAÇÃO DE ROLES AOS USUÁRIOS

GRANT 'Administrador' TO 'admin_master'@'%' WITH ADMIN OPTION;

GRANT 'Gerente' TO 'carlos_pereira'@'%';

GRANT 'Operador' TO 'ana_souza'@'%';
GRANT 'Operador' TO 'roberto_lima'@'%';
GRANT 'Operador' TO 'fernanda_costa'@'%';
GRANT 'Operador' TO 'marcos_pinto'@'%';

GRANT 'Visualizador' TO 'empresa_verde'@'%';
GRANT 'Visualizador' TO 'joao_martins'@'%';
GRANT 'Visualizador' TO 'lara_gomes'@'%';
GRANT 'Visualizador' TO 'eco_brasil'@'%';


-- SEGURANÇA E BLOQUEIO DO ROOT


ALTER USER 'root'@'localhost' ACCOUNT LOCK;
REVOKE ALL PRIVILEGES, GRANT OPTION FROM 'root'@'localhost';
FLUSH PRIVILEGES;

SELECT 'SCRIPT_EXECUTADO: funções atualizadas (Invoker), roles com EXECUTE, root bloqueado' AS resultado;
