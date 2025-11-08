-- Script: criar_procedures.sql
USE EmpresaDeReciclagem;
DELIMITER //
DROP PROCEDURE IF EXISTS sp_contar_materiais //
CREATE PROCEDURE sp_contar_materiais(OUT total INT)
BEGIN
  SELECT COUNT(*) INTO total FROM material;
END //
DELIMITER ;
