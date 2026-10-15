# Gerenciador de Gastos

API REST para controlar despesas parceladas, com status de pagamento por parcela

## Tecnologias Utilizadas
Java 21, Spring Boot, Spring Data JPA, PostgreSQL, Docker, JUnit/Mockito

## Funcionalidades Implementadas
- Cadastro de categorias e despesas
- Divisão automática em parcelas
- Controle de parcelas pagas e não pagas

## Como rodar
### Pré-requisitos
Java 21 e Docker

### Passos
```bash
cp .env.example .env        # preencha DB_USER e DB_PASSWORD
docker compose up -d        # sobe o Postgres
./mvnw spring-boot:run      # sobe a API
```
Por padrão, a API fica disponivel em `http://localhost:8080`

## Endpoints

### Expense
| Método | Rota | Descrição |
|---|---|---|
| POST | /expense | Cria despesa e gera as parcelas |
| GET | /expense | Retorna todas as despesas |
| GET | /expense/{id} | Retorna despesa que possui o ID informado |
| DELETE | /expense/{id} | Deleta despesa que possui o ID informado |

### Category
| Método | Rota | Descrição |
|---|---|---|
| POST | /category | Cria categoria |
| GET | /category | Retorna todas as c | ategorias
| GET | /category/{id} | Retorna categoria que possui o ID informado |
| DELETE | /category/{id} | Deleta categoria que possui o ID informado |


### Installment
| Método | Rota | Descrição |
|---|---|---|
| PATCH | /installment/{id} | Usado para alterar status de pagamento de uma parcela |


## Testes
```bash
./mvnw test
```