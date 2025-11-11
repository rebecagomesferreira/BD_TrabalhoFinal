<<<<<<< HEAD
# BD_TrabalhoFinal
=======
# BD_TrabalhoFinal
Empresa de Reciclagem — API Java + MySQL + MongoDB

API em Java (Spring Boot) integrando MySQL (dados relacionais) e MongoDB (documentos) para o domínio de reciclagem (Coletas, Estoque, Funcionários, Materiais, Vendas).

Este repositório contém:

SQL/: scripts de criação (tabelas, índices, views, triggers, functions, procedures) e inserts;

MongoDB/: coleções .json para carga inicial;

backend/: aplicação Java (Spring Boot) com models, repositórios, services e controllers;

docker-compose.yml: orquestra MySQL, MongoDB e a API;

scripts/init-mongo.sh: importa os .json no MongoDB.

Requisitos

Docker e Docker Compose (docker run hello-world deve funcionar)

Java 17+ (recomendado Java 21) e Maven (se você for rodar o backend localmente)

Se for buildar a imagem do backend via Docker (com backend/Dockerfile), Maven no host é opcional.

(Opcional) VS Code + Extension Pack for Java

Variáveis de ambiente (conexões da API)

A API lê as conexões via variáveis; no docker-compose elas já estão setadas:

# MySQL (dentro da rede do compose)
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/EmpresaDeReciclagem?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=admin123

# MongoDB (root criado pelo compose → precisa authSource=admin)
SPRING_DATA_MONGODB_URI=mongodb://admin:admin123@mongodb:27017/EmpresaDeReciclagem?authSource=admin


Para rodar local (fora do Docker), troque mysql/mongodb por localhost:

MySQL: jdbc:mysql://localhost:3307/EmpresaDeReciclagem?...

Mongo: mongodb://admin:admin123@localhost:27017/EmpresaDeReciclagem?authSource=admin

Subindo tudo com Docker Compose

Na raiz do projeto:

docker compose up -d --build
docker compose ps


Serviços esperados:

mysql (mysql-reciclagem) exposto em localhost:3307

mongodb (mongo-reciclagem) exposto em localhost:27017

app (spring-boot-reciclagem) exposto em localhost:8080

Dica (healthchecks recomendados no compose):

MySQL:

healthcheck:
  test: ["CMD","mysqladmin","ping","-h","127.0.0.1","-uroot","-proot123"]
  interval: 5s
  timeout: 5s
  retries: 20


MongoDB:

healthcheck:
  test: ["CMD","mongosh","--username","admin","--password","admin123","--authenticationDatabase","admin","--eval","db.adminCommand('ping')"]
  interval: 5s
  timeout: 5s
  retries: 20


Logs:

docker compose logs -f mysql
docker compose logs -f mongodb
docker compose logs -f app

Carga inicial dos bancos
MySQL (automático com /docker-entrypoint-initdb.d)

Seus arquivos em SQL/ são montados no container e executados na primeira inicialização do MySQL.

Se precisar reaplicar manualmente:

docker exec -i mysql-reciclagem mysql -uadmin -padmin123 EmpresaDeReciclagem < "SQL/Criacao_de_Tabelas.sql"
docker exec -i mysql-reciclagem mysql -uadmin -padmin123 EmpresaDeReciclagem < "SQL/Índices.sql"
docker exec -i mysql-reciclagem mysql -uadmin -padmin123 EmpresaDeReciclagem < "SQL/Views.sql"
docker exec -i mysql-reciclagem mysql -uadmin -padmin123 EmpresaDeReciclagem < "SQL/Trigger.sql"
docker exec -i mysql-reciclagem mysql -uadmin -padmin123 EmpresaDeReciclagem < "SQL/functions.sql"
docker exec -i mysql-reciclagem mysql -uadmin -padmin123 EmpresaDeReciclagem < "SQL/procedures.sql"
docker exec -i mysql-reciclagem mysql -uadmin -padmin123 EmpresaDeReciclagem < "SQL/INSERT.sql"


Verificar:

mysql -h127.0.0.1 -P3307 -uadmin -padmin123 -e "SHOW DATABASES; USE EmpresaDeReciclagem; SHOW TABLES;"

MongoDB (via script)

O docker-compose.yml monta:

./MongoDB:/docker-entrypoint-initdb.d

./scripts/init-mongo.sh:/docker-entrypoint-initdb.d/init-mongo.sh

Confirme que scripts/init-mongo.sh está executável:

chmod +x scripts/init-mongo.sh


Modelo de init-mongo.sh robusto (sugestão):

#!/usr/bin/env bash
set -euo pipefail

DB="EmpresaDeReciclagem"
URI="mongodb://admin:admin123@localhost:27017/${DB}?authSource=admin"

# Espera o servidor
until mongosh "$URI" --eval 'db.runCommand({ ping: 1 })' >/dev/null 2>&1; do
  echo "Aguardando Mongo..."
  sleep 2
done

# Mapeie arquivo → coleção
declare -A MAP=(
  ["EmpresaDeReciclagem.Coletas.json"]="Coletas"
  ["EmpresaDeReciclagem.Estoque.json"]="Estoque"
  ["EmpresaDeReciclagem.Funcionarios.json"]="Funcionarios"
  ["EmpresaDeReciclagem.Materiais.json"]="Materiais"
  ["EmpresaDeReciclagem.Vendas.json"]="Vendas"
)

for FILE in "${!MAP[@]}"; do
  SRC="/docker-entrypoint-initdb.d/$FILE"
  COLL="${MAP[$FILE]}"
  if [[ -f "$SRC" ]]; then
    echo "Importando $FILE → $DB.$COLL ..."
    mongoimport --uri "$URI" --collection "$COLL" --file "$SRC" --jsonArray
  fi
done


Verificar:

mongosh "mongodb://admin:admin123@localhost:27017/?authSource=admin" \
  --eval 'db.getSiblingDB("EmpresaDeReciclagem").getCollectionNames()'

Rodando o backend (Java)
A) Via Docker (compose) — recomendado para entrega

O serviço app do compose usa backend/Dockerfile para buildar a aplicação e publicar em :8080.

B) Local (útil para dev)
cd backend

# (opcional) sobrescrever conexões locais
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3307/EmpresaDeReciclagem?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
export SPRING_DATASOURCE_USERNAME="admin"
export SPRING_DATASOURCE_PASSWORD="admin123"
export SPRING_DATA_MONGODB_URI="mongodb://admin:admin123@localhost:27017/EmpresaDeReciclagem?authSource=admin"

mvn -q -DskipTests package
java -jar target/*.jar


API: http://localhost:8080

Endpoints (exemplos)

Ajuste os caminhos conforme os seus controllers (padrão sugerido /api/...).

# Vendas
curl -s http://localhost:8080/api/vendas | jq .

# Coletas
curl -s http://localhost:8080/api/coletas | jq .

# Funcionários
curl -s http://localhost:8080/api/funcionarios | jq .

# Materiais
curl -s http://localhost:8080/api/materiais | jq .


Se tiver Spring Actuator, cheque saúde:

curl -s http://localhost:8080/actuator/health | jq .

Teste rápido de integração

Com bancos subidos:

# MySQL
mysql -h127.0.0.1 -P3307 -uadmin -padmin123 -e "SELECT COUNT(*) AS total FROM EmpresaDeReciclagem.CLIENTE;"

# Mongo
mongosh "mongodb://admin:admin123@localhost:27017/?authSource=admin" \
  --eval 'db.getSiblingDB("EmpresaDeReciclagem").Vendas.countDocuments()'


(Ajuste os nomes de tabela/coleção conforme seus scripts.)

Problemas comuns

MySQL “Access denied”
Verifique usuário/senha/DB. No compose: admin/admin123 e EmpresaDeReciclagem.

MySQL “Communications link failure”
Confirme que o container está UP e a porta mapeada é 3307 (host).

Mongo “ServerSelectionTimeoutException”
Use ?authSource=admin quando logar como admin do compose.

JSON não importou
docker-entrypoint-initdb.d não importa .json sozinho. O init-mongo.sh precisa chamar mongoimport (como no exemplo).

Portas ocupadas (3307/27017/8080)
Troque portas no compose ou pare serviços locais.

Licença

Defina a licença do projeto (MIT/Apache-2.0/etc.), se necessário.

Observações finais

Garanta que backend/src/main/resources/application.properties contenha URLs completas (sem “…” encurtando a string).

Se existir um JpaConfig.java com conteúdo de CORS por engano, pode remover (o Spring Boot auto-configura JPA) ou manter apenas @EnableJpaRepositories no pacote MySQL.
>>>>>>> d010dca (criação controller e service fornecedor)
