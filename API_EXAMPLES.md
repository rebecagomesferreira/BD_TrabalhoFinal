# Exemplos de Requisições HTTP

## Usando cURL

### Listar materiais
```bash
curl -X GET http://localhost:8080/api/materiais
```

### Criar material
```bash
curl -X POST http://localhost:8080/api/materiais \
  -H "Content-Type: application/json" \
  -d '{
    "idMaterial": "MAT-TEST-001",
    "tipo": "Plástico PET",
    "descricao": "Garrafas PET recicladas",
    "precoKg": 3.50
  }'
```

### Listar coletas
```bash
curl -X GET http://localhost:8080/api/coletas
```

### Listar vendas por cliente
```bash
curl -X GET http://localhost:8080/api/vendas/cliente/CLI-EcoBrasil
```

## Autenticação e Permissões

O sistema possui controle de acesso baseado em roles (RBAC - Role-Based Access Control) com 4 níveis de permissão:

- **Administrador**: Acesso total ao sistema com permissões de GRANT
- **Gerente**: SELECT, INSERT, UPDATE, DELETE, EXECUTE (funções)
- **Operador**: SELECT, INSERT, EXECUTE (funções)
- **Visualizador**: SELECT (somente leitura)

### Conexão MySQL com Autenticação

Para conectar ao banco MySQL usando um usuário específico:

```bash
# Como Administrador
mysql -h localhost -P 3307 -u admin_master -p'admADM-2025-0001' EmpresaDeReciclagem

# Como Gerente
mysql -h localhost -P 3307 -u carlos_pereira -p'opGER-2025-2282' EmpresaDeReciclagem

# Como Operador
mysql -h localhost -P 3307 -u ana_souza -p'opCOL-2025-5208' EmpresaDeReciclagem

# Como Visualizador (Cliente)
mysql -h localhost -P 3307 -u empresa_verde -p'cliCLI-2025-9865' EmpresaDeReciclagem
```

### Testar Permissões via Docker

#### 1. Verificar usuário e role atual
```bash
docker exec mysql-reciclagem mysql -ucarlos_pereira -p'opGER-2025-2282' \
  -e "SELECT CURRENT_USER(), CURRENT_ROLE();"
```

**Resposta esperada:**
```
CURRENT_USER()         CURRENT_ROLE()
carlos_pereira@%       `Gerente`@`%`
```

#### 2. Listar todos os usuários (como admin)
```bash
docker exec mysql-reciclagem mysql -uadmin_master -p'admADM-2025-0001' \
  -e "SELECT User, Host FROM mysql.user WHERE User NOT LIKE 'mysql%' AND User != 'root' ORDER BY User;"
```

#### 3. Ver permissões de uma role
```bash
docker exec mysql-reciclagem mysql -uroot -proot123 \
  -e "SHOW GRANTS FOR 'Gerente';"
```

**Resposta:**
```
GRANT USAGE ON *.* TO `Gerente`@`%`
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON `EmpresaDeReciclagem`.* TO `Gerente`@`%`
```

#### 4. Ver permissões de um usuário específico
```bash
docker exec mysql-reciclagem mysql -uroot -proot123 \
  -e "SHOW GRANTS FOR 'carlos_pereira'@'%';"
```

#### 5. Testar consulta com usuário Visualizador
```bash
docker exec mysql-reciclagem mysql -uempresa_verde -p'cliCLI-2025-9865' \
  -e "USE EmpresaDeReciclagem; SELECT * FROM MATERIAL LIMIT 3;"
```

#### 6. Testar INSERT com usuário Operador
```bash
docker exec mysql-reciclagem mysql -uana_souza -p'opCOL-2025-5208' \
  -e "USE EmpresaDeReciclagem; INSERT INTO CLIENTE (id_cliente, nome, cpf_cnpj, telefone, email) VALUES ('CLI-TEST-999', 'Cliente Teste', '999.999.999-99', '11999999999', 'teste@test.com');"
```

#### 7. Testar UPDATE com usuário Gerente
```bash
docker exec mysql-reciclagem mysql -ucarlos_pereira -p'opGER-2025-2282' \
  -e "USE EmpresaDeReciclagem; UPDATE CLIENTE SET telefone = '11988888888' WHERE id_cliente = 'CLI-TEST-999';"
```

#### 8. Executar função personalizada
```bash
docker exec mysql-reciclagem mysql -ucarlos_pereira -p'opGER-2025-2282' \
  -e "USE EmpresaDeReciclagem; SELECT TotalGastoCliente('CLI-2025-5004') as total_gasto;"
```

### Usuários e Senhas de Teste

| Usuário | Senha | Role | Permissões |
|---------|-------|------|------------|
| admin_master | admADM-2025-0001 | Administrador | ALL PRIVILEGES + GRANT OPTION |
| carlos_pereira | opGER-2025-2282 | Gerente | SELECT, INSERT, UPDATE, DELETE, EXECUTE |
| ana_souza | opCOL-2025-5208 | Operador | SELECT, INSERT, EXECUTE |
| roberto_lima | opSEP-2025-9195 | Operador | SELECT, INSERT, EXECUTE |
| fernanda_costa | opATE-2025-0355 | Operador | SELECT, INSERT, EXECUTE |
| marcos_pinto | opMOT-2025-4185 | Operador | SELECT, INSERT, EXECUTE |
| empresa_verde | cliCLI-2025-9865 | Visualizador | SELECT (somente leitura) |
| joao_martins | cliCLI-2025-6771 | Visualizador | SELECT (somente leitura) |
| lara_gomes | cliCLI-2025-4260 | Visualizador | SELECT (somente leitura) |
| eco_brasil | cliCLI-2025-0988 | Visualizador | SELECT (somente leitura) |

### Exemplos de Restrições de Permissão

#### Visualizador tentando fazer INSERT (FALHA)
```bash
docker exec mysql-reciclagem mysql -uempresa_verde -p'cliCLI-2025-9865' \
  -e "USE EmpresaDeReciclagem; INSERT INTO CLIENTE (id_cliente, nome) VALUES ('TEST', 'Teste');"
```

**Erro esperado:**
```
ERROR 1142 (42000): INSERT command denied to user 'empresa_verde'@'%' for table 'CLIENTE'
```

#### Operador tentando fazer DELETE (FALHA)
```bash
docker exec mysql-reciclagem mysql -uana_souza -p'opCOL-2025-5208' \
  -e "USE EmpresaDeReciclagem; DELETE FROM CLIENTE WHERE id_cliente = 'CLI-TEST-999';"
```

**Erro esperado:**
```
ERROR 1142 (42000): DELETE command denied to user 'ana_souza'@'%' for table 'CLIENTE'
```

### Conectar via Spring Boot com usuário específico

Para conectar a aplicação Spring Boot com um usuário específico, modifique o `application.properties`:

```properties
# Conexão como Gerente (recomendado para API)
spring.datasource.url=jdbc:mysql://localhost:3307/EmpresaDeReciclagem
spring.datasource.username=carlos_pereira
spring.datasource.password=opGER-2025-2282

# Ou como Operador (apenas leitura e inserção)
spring.datasource.username=ana_souza
spring.datasource.password=opCOL-2025-5208
```

### Auditoria e Segurança

#### Verificar último login de usuários
```bash
docker exec mysql-reciclagem mysql -uroot -proot123 \
  -e "SELECT user, host, last_login FROM mysql.user WHERE user LIKE '%carlos%';"
```

#### Listar roles ativas
```bash
docker exec mysql-reciclagem mysql -uroot -proot123 \
  -e "SELECT * FROM mysql.role_edges;"
```

#### Verificar privilégios de tabela específica
```bash
docker exec mysql-reciclagem mysql -uroot -proot123 \
  -e "SELECT * FROM mysql.tables_priv WHERE User = 'carlos_pereira';"
```

### Boas Práticas de Segurança

1. **Não use root em produção**: Sempre use usuários com permissões específicas
2. **Princípio do menor privilégio**: Dê apenas as permissões necessárias
3. **Senhas fortes**: As senhas de exemplo devem ser alteradas em produção
4. **Auditoria**: Monitore acessos e operações sensíveis
5. **Conexões seguras**: Use SSL/TLS para conexões remotas

### Troubleshooting de Permissões

Se encontrar erros de permissão:

```bash
# 1. Verificar se a role está ativa
docker exec mysql-reciclagem mysql -ucarlos_pereira -p'opGER-2025-2282' \
  -e "SELECT CURRENT_ROLE();"

# 2. Verificar grants do usuário
docker exec mysql-reciclagem mysql -uroot -proot123 \
  -e "SHOW GRANTS FOR 'carlos_pereira'@'%';"

# 3. Reativar roles padrão (se necessário)
docker exec mysql-reciclagem mysql -uroot -proot123 \
  -e "SET DEFAULT ROLE ALL TO 'carlos_pereira'@'%';"

# 4. Aplicar mudanças
docker exec mysql-reciclagem mysql -uroot -proot123 \
  -e "FLUSH PRIVILEGES;"
```
