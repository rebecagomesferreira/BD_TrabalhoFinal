USE EmpresaDeReciclagem;


-- VIEW: estoque_critico
-- Objetivo: Identificar materiais cujo ESTOQUE está abaixo do nível mínimo.
CREATE OR REPLACE VIEW estoque_critico AS
SELECT 
    e.id_estoque,
    e.localizacao,
    e.nivel_atual,
    e.nivel_minimo,
    m.id_material,
    m.tipo,
    m.descricao
FROM ESTOQUE e
JOIN ESTOQUE_ARMAZENA_MATERIAL eam ON e.id_estoque = eam.id_estoque
JOIN MATERIAL m ON eam.id_material = m.id_material
WHERE e.nivel_atual < e.nivel_minimo;

SELECT * FROM estoque_critico;


-- VIEW: funcionario_por_cargo
-- Objetivo: Controlar a quantidade de funcionários por cargo.
CREATE OR REPLACE VIEW funcionario_por_cargo AS
SELECT 
    f.cargo,
    COUNT(f.id_funcionario) AS total_funcionarios
FROM FUNCIONARIO f
GROUP BY f.cargo
ORDER BY total_funcionarios DESC;

SELECT * FROM funcionario_por_cargo;


-- VIEW: dados_clientes
-- Objetivo: Garantir confidencialidade dos clientes (CPF/CNPJ mascarado).

CREATE OR REPLACE VIEW dados_clientes AS
SELECT 
    c.id_cliente,
    c.nome,
    CONCAT(SUBSTRING(c.cpf_cnpj,1,3), '.***.***-', SUBSTRING(c.cpf_cnpj,-2)) AS cpf_mascarado,
    c.telefone,
    c.email
FROM CLIENTE c;

SELECT * FROM dados_clientes;


-- VIEW: material_estoque
-- Objetivo: Mostrar a localização exata de cada MATERIAL no ESTOQUE.
CREATE OR REPLACE VIEW material_estoque AS
SELECT 
    e.id_estoque,
    e.localizacao,
    m.id_material,
    m.tipo,
    m.descricao,
    e.nivel_atual,
    e.capacidade
FROM ESTOQUE e
JOIN ESTOQUE_ARMAZENA_MATERIAL eam ON e.id_estoque = eam.id_estoque
JOIN MATERIAL m ON eam.id_material = m.id_material
ORDER BY e.localizacao;

SELECT * FROM material_estoque;


-- VIEW: coleta
-- Objetivo: Registrar informações detalhadas sobre coletas realizadas.
CREATE OR REPLACE VIEW coleta AS
SELECT 
    dc.id_doc_coleta,
    dc.data AS data_coleta,
    dc.local,
    f.nome AS funcionario_responsavel,
    fr.nome AS FORNECEDOR
FROM DOCUMENTO_COLETA dc
JOIN FUNCIONARIO f ON dc.id_funcionario = f.id_funcionario
JOIN FORNECEDOR fr ON dc.id_fornecedor = fr.id_fornecedor
ORDER BY dc.data DESC;

SELECT * FROM coleta;


-- VIEW: historico_coleta
-- Objetivo: Analisar o volume de coletas por período.
CREATE OR REPLACE VIEW historico_coleta AS
SELECT 
    YEAR(dc.data) AS ano,
    MONTH(dc.data) AS mes,
    COUNT(dc.id_doc_coleta) AS total_coletas,
    SUM(dcm.quantidade) AS total_material_coletado
FROM DOCUMENTO_COLETA dc
JOIN DOC_COLETA_CONTEM_MATERIAL dcm ON dc.id_doc_coleta = dcm.id_doc_coleta
GROUP BY ano, mes
ORDER BY ano DESC, mes DESC;

SELECT * FROM historico_coleta;


-- VIEW: historico_vendas
-- Objetivo: Acompanhar volume e valor das vendas por período.
CREATE OR REPLACE VIEW historico_vendas AS
SELECT 
    YEAR(dv.data) AS ano,
    MONTH(dv.data) AS mes,
    COUNT(dv.id_doc_venda) AS total_vendas,
    SUM(dv.valor) AS valor_total_vendido
FROM DOCUMENTO_VENDA dv
GROUP BY YEAR(dv.data), MONTH(dv.data)
ORDER BY ano DESC, mes DESC;

select*from historico_vendas;
