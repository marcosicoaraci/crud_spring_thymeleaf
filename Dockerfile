# Usar a imagem oficial do OpenJDK 17
FROM openjdk:17-jdk-slim

# Definir diretório de trabalho dentro do container
WORKDIR /app

# Copiar o jar gerado pelo Maven/Gradle
COPY target/desafio-cast-1.0-SNAPSHOT.jar app.jar

# Definir comando de inicialização
ENTRYPOINT ["java","-jar","/app/app.jar"]

