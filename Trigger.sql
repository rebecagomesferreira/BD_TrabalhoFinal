
USE EmpresaDeReciclagem;


-- verifica espaço no estoque antes de inserir

DELIMITER $$
CREATE TRIGGER verifica_espaco_estoque
BEFORE INSERT ON estoque_armazenA_material
FOR EACH ROW
BEGIN
    DECLARE capacidade_max INT;
    DECLARE nivel_atual INT;

    SELECT capacidade, nivel_atual INTO capacidade_max, nivel_atual
    FROM estoque
    WHERE id_estoque = NEW.id_estoque;

    IF nivel_atual + 1 > capacidade_max THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Estoque não possui espaço suficiente.';
    END IF;
END$$
DELIMITER ;




-- Atualiza nível do estoque após inserção

DELIMITER $$
CREATE TRIGGER atualiza_nivel_estoque
AFTER INSERT ON estoque_armazenA_material
FOR EACH ROW
BEGIN
    UPDATE estoque
    SET nivel_atual = nivel_atual + 1
    WHERE id_estoque = NEW.id_estoque;
END$$
DELIMITER ;



-- Impede exclusão de fornecedor com documentos de coleta

DELIMITER $$
CREATE TRIGGER impede_exclusao_fornecedor
BEFORE DELETE ON fornecedor
FOR EACH ROW
BEGIN
    IF EXISTS (SELECT 1 FROM documento_coleta WHERE id_fornecedor = OLD.id_fornecedor) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Não é permitido excluir fornecedor com documentos de coleta.';
    END IF;
END$$
DELIMITER ;


-- Impede atualização de preço abaixo do mínimo

DELIMITER $$
CREATE TRIGGER verifica_preco_minimo
BEFORE UPDATE ON material
FOR EACH ROW
BEGIN
    IF NEW.preco_kg < 0.1 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Preço do material não pode ser menor que 0.1';
    END IF;
END$$
DELIMITER ;




--  Impede cliente duplicado (CPF/CNPJ)

DELIMITER $$
CREATE TRIGGER verifica_cpf_duplicado_cliente
BEFORE INSERT ON cliente
FOR EACH ROW
BEGIN
    IF EXISTS (SELECT 1 FROM cliente WHERE cpf_cnpj = NEW.cpf_cnpj) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cliente com CPF/CNPJ já cadastrado.';
    END IF;
END$$
DELIMITER ;

