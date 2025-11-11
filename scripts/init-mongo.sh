#!/bin/bash

# Aguarda MongoDB iniciar
sleep 10

# Importa dados para MongoDB
mongoimport --host localhost --username admin --password admin123 --authenticationDatabase admin --db EmpresaDeReciclagem --collection Coletas --file /docker-entrypoint-initdb.d/EmpresaDeReciclagem.Coletas.json --jsonArray
mongoimport --host localhost --username admin --password admin123 --authenticationDatabase admin --db EmpresaDeReciclagem --collection Estoque --file /docker-entrypoint-initdb.d/EmpresaDeReciclagem.Estoque.json --jsonArray
mongoimport --host localhost --username admin --password admin123 --authenticationDatabase admin --db EmpresaDeReciclagem --collection Funcionarios --file /docker-entrypoint-initdb.d/EmpresaDeReciclagem.Funcionarios.json --jsonArray
mongoimport --host localhost --username admin --password admin123 --authenticationDatabase admin --db EmpresaDeReciclagem --collection Materiais --file /docker-entrypoint-initdb.d/EmpresaDeReciclagem.Materiais.json --jsonArray
mongoimport --host localhost --username admin --password admin123 --authenticationDatabase admin --db EmpresaDeReciclagem --collection Vendas --file /docker-entrypoint-initdb.d/EmpresaDeReciclagem.Vendas.json --jsonArray
