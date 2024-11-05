FROM openjdk:21-slim-buster
# Sistema operacional base
# Linux + Open JDK 21
# É uma outra imagem docker

LABEL authors="Squad4"
#Adiciona o métadado autor

WORKDIR /app
# app é a pasta onde o código irá ficar

COPY target/*.jar app.jar
# Ele copia qualquer arquivo .jar da pasta target
# Esse arquivo dento do docker irá se chamar app.jar

ENV SERVER_PORT=8081
# Variavel de ambiente da porta 8081
# Essa variavel subistitui a server.port do application.properties

EXPOSE 8081
# A porta 8081 está exposta para o Docker
# Não para a maquina ainda

ENTRYPOINT ["java", "-jar", "app.jar"]
# java -jar app.jar -> comando no Bash Linux
# comando usando o openJDK do linux para executar o jar dentro da pasta app