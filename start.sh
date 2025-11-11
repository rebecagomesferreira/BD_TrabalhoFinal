#!/bin/bash

echo "Iniciando containers..."
docker-compose up -d mysql mongodb

echo "Aguardando MySQL inicializar..."
sleep 20

echo "Aguardando MongoDB inicializar..."
sleep 10

echo "Construindo e iniciando aplicação Spring Boot..."
docker-compose up --build app

echo "Sistema iniciado!"
echo "MySQL: localhost:3306"
echo "MongoDB: localhost:27017"
echo "API Spring Boot: http://localhost:8080"
