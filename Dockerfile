FROM maven:3.9.4-eclipse-temurin-17

# Maven cache klasörü
VOLUME /root/.m2

# Bağımlılıkları daha önceden cache etmek istersen:
COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .