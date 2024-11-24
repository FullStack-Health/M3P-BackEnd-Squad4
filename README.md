# LABMedical 🏥

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)](https://jwt.io/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://swagger.io/)
[![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)

## 📋 Descrição do Projeto

VitalCare by LABMedical é uma API RESTful para Back-End, de gerenciamento de prontuário médico-hospitalar.
Foi desenvolvida em Java e Spring Boot e gerenciada com Maven, visando resolver a dificuldade de gerenciar informações de pacientes em um ambiente médico.

### 🎯 Principais Objetivos:
- Armazenamento seguro de dados pessoais e médicos
- Gerenciamento de consultas e exames
- Controle de prontuários de pacientes
- Sistema robusto de autenticação e autorização

## 🛠️ Tecnologias Utilizadas

<div align="left-align">

| Tecnologia                                                                                                                     | Finalidade          |
|--------------------------------------------------------------------------------------------------------------------------------|---------------------|
| ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)                               | Linguagem principal |
| ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)             | Framework Back-End  |
| ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=spring-security&logoColor=white) | Segurança           |
| ![OAuth2](https://img.shields.io/badge/OAuth2-2F2F2F?style=flat-square&logo=oauth&logoColor=white)                             | Autenticação        |
| ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat-square&logo=postgresql&logoColor=white)                | Banco de dados      |
| ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=black)                         | Documentação API    |

</div>

## ⚙️ Funcionalidades

- 📝 Operações CRUD completas para:
  - Pacientes
  - Consultas
  - Exames
  - Prontuários
- 🔐 Sistema de controle de acesso com perfis:
  - ADMIN
  - MÉDICO
  - PACIENTE

## 🚀 Como Executar o Projeto

```bash
# Clone o repositório
git clone 

# Entre na pasta do projeto
cd labmedical

# Execute o projeto
mvn spring-boot:run
```

Acesse `http://localhost:8080` 🌐

## 🔑 Autenticação e Primeiro Acesso

### Credenciais Iniciais
```json
{
  "email": "admin@example.com",
  "senha": "admin123"
}
```

### Exemplo de Request para Login

```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "email":"admin@example.com",
  "password":"admin123"
}' http://localhost:8080/login
```

### Exemplo de Resposta

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

## 📚 Documentação

Acesse a documentação Swagger em: `http://localhost:8080/swagger-ui.html`

## 👥 Equipe de Desenvolvimento

<div align="left-align">

| Desenvolvedor                   | GitHub                                                                                                                                    |
|---------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| André Junckes da Silva Mattos   | [![GitHub ](https://img.shields.io/badge/GitHub-100000?style=flat-square&logo=github&logoColor=white)](https://github.com/andrejsmattos)  |
| Felipe Augusto Antunes Da Crus  | [![GitHub ](https://img.shields.io/badge/GitHub-100000?style=flat-square&logo=github&logoColor=white)](https://github.com/AFelipeAntunes) |
| Heloise Adriano Pereira         | [![GitHub ](https://img.shields.io/badge/GitHub-100000?style=flat-square&logo=github&logoColor=white)](https://github.com/heloiseap)      |
| Marcos Grechi Anastacio         | [![GitHub ](https://img.shields.io/badge/GitHub-100000?style=flat-square&logo=github&logoColor=white)](https://github.com/MarcosGrechi)   |

</div>
