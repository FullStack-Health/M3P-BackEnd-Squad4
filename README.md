# LABMedical

## Descrição do Projeto
VitalCare by LABMedical é uma API RESTful para Back-End, de gerenciamento de prontuário médico-hospitalar.
Foi desenvolvida em Java e Spring Boot e gerenciada com Maven, visando resolver a dificuldade de gerenciar informações de pacientes em um ambiente médico.
Isso inclui o armazenamento seguro de dados pessoais e médicos como informações de consultas, exames e prontuários de pacientes, bem como a atribuição de perfis de acesso de usuário ao sistema.


## Tecnologias Utilizadas
- Java
- Spring Boot
- Spring Security
- OAuth2
- JWT
- Spring Data JPA
- Maven
- PostgreSQL
- Swagger
- JUnit

## Funcionalidades
O sistema oferece:
- Operações CRUD de pacientes, consultas, exames e prontuários;
- Controle de acesso de perfis de acesso dos tipos 'ADMIN', 'MÉDICO' e 'PACIENTE' usando JWT, Spring Security e a encriptação de senhas.


## Como Executar o Projeto
1. Clone o repositório para a sua máquina local usando `git clone`.
2. Navegue até a pasta do projeto e execute `mvn spring-boot:run` para iniciar a aplicação.
3. A aplicação estará disponível em `http://localhost:8080`.


# Autenticação e Primeiro Acesso ao Sistema

## Passo 1: Inicialização do Sistema
Quando o sistema é inicializado pela primeira vez, um usuário admin é criado automaticamente. As credenciais deste usuário são:

- Email: admin@example.com
- Senha: admin123

## Passo 2: Obtenção do Token JWT
Para obter o token JWT para o usuário admin, faça uma requisição POST para o endpoint "/login" com as credenciais do usuário admin no corpo da requisição, este token tem validade de 24h.

Exemplo de requisição usando cURL:

```bash
curl -X POST -H "Content-Type: application/json" -d 

'{
	"email":"admin@example.com",
	"password":"admin123"
}'

http://localhost:8080/login
```

A resposta desta requisição deve conter o token JWT.

Exemplo de resposta:

```json
{
  "token": "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImV4cCI6MTczMDg0NjU0OCwicGFjaWVudGVJZCI6IiIsImlhdCI6MTczMDc2MDE0OCwic2NvcGUiOiJBRE1JTiJ9.OQQjTC0JcCgk7AGRXcbU7sMBfSAqd44MuahpMm4Agito_QphcrWAkab_QlghSLe4Bw4NVuVpQ0laH2-YFeabMdMfHUNWClojkBd86nsfHNzsXMMn2ax1PO_kslj7qODT4tu5W20NDyz33l6O4EYy9NX9On9jFu4740PVn2sq3VahNWbCPi9puU8XsqEjsP8VDwZCe0fJGfznBl4pj0B2a9-rGSxtuqA9nx8hlOoOrLDZ0mIAMTK8axaw35UIhKmkP-v0dzO8nOKwPLn0MN084uEetiyqb4HiTU6s1SQdEcKD0oyPxuzicCW0FowIx8lXJNvtQVopA5tbEgjbZvuhiw",
  "tempoExpiracao": 86400,
  "listaNomesPerfis": [
    "ADMIN"
  ],
  "pacienteId": "",
  "usuarioId": "1",
  "email": "admin@example.com",
  "nome": "Administrador"
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
Lembre-se de que o token JWT tem um tempo de expiração de 24h, então você precisará obter um novo token quando o atual expirar. Para obter um novo token, repita o passo 2.


## Acessando a Documentação
A documentação da API pode ser acessada através do Swagger UI, disponível em http://localhost:8080/swagger-ui.html, enquanto o projeto estiver inicializado no sistema.


## Requisições do Insomnia
As requisições do Insomnia para este projeto estão incluídas como um arquivo anexo. Você pode importar este arquivo no Insomnia para testar facilmente todas as rotas e funcionalidades da API.


## Equipe de Desenvolvimento do Sistema
- André Junckes da Silva Mattos
- Felipe Augusto Antunes Da Crus
- Heloise Adriano Pereira
- Marcos Grechi Anastacio

