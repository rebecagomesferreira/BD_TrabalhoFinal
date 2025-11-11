
USE EmpresaDeReciclagem;

-- CRIAÇÃO DE USUÁRIOS E ROLES

CREATE USER IF NOT EXISTS 'admin_master'@'%' IDENTIFIED BY 'admADM-2025-0001';

CREATE ROLE IF NOT EXISTS 'Administrador';
CREATE ROLE IF NOT EXISTS 'Gerente';
CREATE ROLE IF NOT EXISTS 'Operador';
CREATE ROLE IF NOT EXISTS 'Visualizador';


-- PRIVILÉGIOS DAS ROLES

-- Administrador -> total
GRANT ALL PRIVILEGES ON EmpresaDeReciclagem.* TO 'Administrador' WITH GRANT OPTION;

-- Gerente -> CRUD + EXECUTE (funções)
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON EmpresaDeReciclagem.* TO 'Gerente';

-- Operador -> Inserir, consultar + EXECUTE (funções)
GRANT SELECT, INSERT, EXECUTE ON EmpresaDeReciclagem.* TO 'Operador';

-- Visualizador -> somente leitura
GRANT SELECT ON EmpresaDeReciclagem.* TO 'Visualizador';


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


-- APLICAR ROLES AOS USUÁRIOS (necessário ativar as roles padrão)
SET DEFAULT ROLE ALL TO 'admin_master'@'%';
SET DEFAULT ROLE ALL TO 'carlos_pereira'@'%';
SET DEFAULT ROLE ALL TO 'ana_souza'@'%';
SET DEFAULT ROLE ALL TO 'roberto_lima'@'%';
SET DEFAULT ROLE ALL TO 'fernanda_costa'@'%';
SET DEFAULT ROLE ALL TO 'marcos_pinto'@'%';
SET DEFAULT ROLE ALL TO 'empresa_verde'@'%';
SET DEFAULT ROLE ALL TO 'joao_martins'@'%';
SET DEFAULT ROLE ALL TO 'lara_gomes'@'%';
SET DEFAULT ROLE ALL TO 'eco_brasil'@'%';

FLUSH PRIVILEGES;

SELECT 'SCRIPT_EXECUTADO: Usuários e roles criados com sucesso!' AS resultado;
