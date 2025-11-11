use EmpresaDeReciclagem;

-- FUNCTION 

-- calcular valor gasto por cliente
DELIMITER //
<<<<<<< HEAD
CREATE FUNCTION TotalGastoCliente(p_id_cliente INT)
=======
CREATE FUNCTION TotalGastoCliente(p_id_cliente CHAR(15))
>>>>>>> d010dca (criação controller e service fornecedor)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE total DECIMAL(10,2);
    SELECT SUM(valor) INTO total
    FROM DOCUMENTO_VENDA
    WHERE id_cliente = p_id_cliente;
    RETURN IFNULL(total, 0);
END //
DELIMITER ;
SELECT nome, TotalGastoCliente(id_cliente) AS TotalGasto
FROM CLIENTE;

-- calcula o valor total de uma venda
DELIMITER //
<<<<<<< HEAD
CREATE FUNCTION ValorTotalVenda(p_id_doc_venda INT)
=======
CREATE FUNCTION ValorTotalVenda(p_id_doc_venda CHAR(15))
>>>>>>> d010dca (criação controller e service fornecedor)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE total DECIMAL(10,2);
    SELECT SUM(quantidade * preco_kg) INTO total
    FROM ITEM_VENDA
    WHERE id_doc_venda = p_id_doc_venda;
    RETURN IFNULL(total, 0);
END //
DELIMITER ;
SELECT id_doc_venda, ValorTotalVenda(id_doc_venda) AS TotalVenda
FROM DOCUMENTO_VENDA;


-- calcula a quantidade total em estoque
DELIMITER //
<<<<<<< HEAD
CREATE FUNCTION QuantidadeMaterialEstoque(p_id_material INT)
=======
CREATE FUNCTION QuantidadeMaterialEstoque(p_id_material CHAR(15))
>>>>>>> d010dca (criação controller e service fornecedor)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT SUM(nivel_atual) INTO total
    FROM ESTOQUE e
    JOIN ESTOQUE_ARMAZENA_MATERIAL eam ON e.id_estoque = eam.id_estoque
    WHERE eam.id_material = p_id_material;
    RETURN IFNULL(total, 0);
END //
DELIMITER ;

select id_material, QuantidadeMaterialEstoque(id_material) as MaterialEstoque
<<<<<<< HEAD
from Material;
=======
from MATERIAL;
>>>>>>> d010dca (criação controller e service fornecedor)
