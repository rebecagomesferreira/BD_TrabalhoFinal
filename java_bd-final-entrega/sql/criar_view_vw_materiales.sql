-- Script: criar_view_vw_materiales.sql
USE EmpresaDeReciclagem;
DROP VIEW IF EXISTS vw_materiales;
CREATE VIEW vw_materiales AS
SELECT id_material, tipo, descricao, preco_kg
FROM material;
