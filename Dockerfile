# Build aşaması
FROM maven:3.9.4-eclipse-temurin AS build

# Uygulamanın kaynak kodunu kopyala
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Maven ile uygulamayı build et
RUN mvn clean package -DskipTests

# Run aşaması
FROM amazoncorretto:21

# Çalışma dizinini ayarla
WORKDIR /app

# Build aşamasında üretilen jar dosyasını kopyala
COPY --from=build /app/target/courier-tracker-*.jar app.jar

# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
