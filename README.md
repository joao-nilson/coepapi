## Como rodar o projeto no pc (ubuntu)

## precisa do postgre instalado, caso não tenha:

```bash
sudo apt install postgresql -y
sudo systemctl start postgresql
```

## projeto utiliza java 17, caso não tenha:

```bash
sudo apt update
sudo apt install openjdk-17-jdk -y
```

## Criar o banco de dados

Nome do banco: coepapi_db
user: postgres
senha: postgres

entrar no banco:

```bash
sudo -u postgres psql
```

dentro do banco, colar:

```sql
ALTER USER postgres WITH PASSWORD 'postgres';
CREATE DATABASE coepapi_db;
\q
```

## Rodar o projeto

dentro da pasta do projeto:

```bash
./mvnw spring-boot:run
```

quando aparecer "Started SgpoepapiApplication" está rodando.

## Testar

abrir no navegador:

```
http://localhost:8080/swagger-ui.html
```
