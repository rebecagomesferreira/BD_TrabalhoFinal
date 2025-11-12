use EmpresaDeReciclagem;

-- PROCEDURES

-- registra nova venda
DELIMITER //
CREATE PROCEDURE RegistrarVenda(
    IN p_data DATE,
    IN p_valor DECIMAL(10,2),
    IN p_id_cliente CHAR(15),
    IN p_id_funcionario CHAR(15),
    IN p_id_tipo_pagamento INT
)
BEGIN
    INSERT INTO DOCUMENTO_VENDA (data, valor, id_cliente, id_funcionario, id_tipo_pagamento)
    VALUES (p_data, p_valor, p_id_cliente, p_id_funcionario, p_id_tipo_pagamento);
END //
DELIMITER ;

--  atualiza estoque automaticamente após a compra
DELIMITER //
CREATE PROCEDURE AtualizarEstoqueColeta(
    IN p_id_material CHAR(15),
    IN p_quantidade INT
)
BEGIN
    UPDATE ESTOQUE
    SET nivel_atual = nivel_atual + p_quantidade
    WHERE id_estoque IN (
        SELECT id_estoque
        FROM ESTOQUE_ARMAZENA_MATERIAL
        WHERE id_material = p_id_material
    );
END //
DELIMITER ;

-- cria um relatório de vendas por cliente
DELIMITER //
CREATE PROCEDURE RelatorioVendasCliente()
BEGIN
    SELECT c.nome AS Cliente, SUM(dv.valor) AS TotalGasto
    FROM DOCUMENTO_VENDA dv
    JOIN CLIENTE c ON dv.id_cliente = c.id_cliente
    GROUP BY c.nome
    ORDER BY TotalGasto DESC;
END //
DELIMITER ;