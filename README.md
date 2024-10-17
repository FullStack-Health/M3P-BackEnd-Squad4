# LABMedical

## Descrição do Projeto
LABMedical é uma API RESTful para Back-End, construída em Java e Spring Boot e gerenciada com Maven, que visa resolver a dificuldade de gerenciar informações de pacientes em um ambiente médico. Isso inclui o armazenamento seguro de dados pessoais, e dados médicos como informações de consultas, exames e prontuários de pacientes, bem como a atribuição de perfis de acesso de usuário.


## Tecnologias Utilizadas
- Java
- Spring Boot
- Spring Security
- OAuth2
- JWT
- Spring Data JPA
- Maven
- PostgreSQL

## Funcionalidades
O sistema oferece:
- Operações CRUD de pacientes, consultas, exames e prontuários;
- Controle de acesso usando JWT, Spring Security e a encriptação de senhas.


## Como Executar o Projeto
1. Clone o repositório para a sua máquina local usando `git clone`.
2. Navegue até a pasta do projeto e execute `mvn spring-boot:run` para iniciar a aplicação.
3. A aplicação estará disponível em `http://localhost:8080`.


# Autenticação e Primeiro Acesso ao Sistema

## Passo 1: Inicialização do Sistema
Quando o sistema é inicializado pela primeira vez, um usuário admin é criado automaticamente. As credenciais deste usuário são:

- Email: admin@example.com
- Senha: admin

## Passo 2: Obtenção do Token JWT
Para obter o token JWT para o usuário admin, faça uma requisição POST para o endpoint "/login" com as credenciais do usuário admin no corpo da requisição.

Exemplo de requisição usando cURL:

```bash
curl -X POST -H "Content-Type: application/json" -d 

'{
	"email":"admin@example.com",
	"password":"admin"
}'

http://localhost:8080/login
```

A resposta desta requisição deve conter o token JWT.

Exemplo de resposta:

```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImV4cCI6MTYxNzU0NTIwMH0.rrVO4hz5H8KJxYISHXY4YFZACwBZ3ZLBIdV8Jy6GfHg",
    "expiresIn": 36000
}
```

## Passo 3: Autenticação de Requisições
Para autenticar as próximas requisições, inclua o token JWT no cabeçalho 'Authorization' de suas requisições. O formato deve ser "Bearer <token>", onde <token> é o token JWT obtido no passo 2.

Exemplo de requisição autenticada usando cURL:

```bash
curl -X GET -H "Authorization: Bearer <token>" http://localhost:8080/resource
```

Substitua "<token>" pelo token JWT do usuário admin.

## Nota
Lembre-se de que o token JWT tem um tempo de expiração, então você precisará obter um novo token quando o atual expirar. Para obter um novo token, repita o passo 2.


## Requisições do Insomnia
As requisições do Insomnia para este projeto estão incluídas como um arquivo anexo. Você pode importar este arquivo no Insomnia para testar facilmente todas as rotas e funcionalidades da API.


## Vídeo de Apresentação do Projeto\
Veja a apresentação do projeto no link a seguir: https://drive.google.com/file/d/1bIH2XTFCyOPkyJPH8feaY48rAvYCwhDH/view?usp=sharing
