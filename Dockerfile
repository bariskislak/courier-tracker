# Build aşaması
FROM maven:3.9.4-eclipse-temurin AS build

# Uygulamanın kaynak kodunu kopyala
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY wait-for-it.sh /usr/local/bin/

# Maven ile uygulamayı build et
RUN chmod +x /usr/local/bin/wait-for-it.sh && mvn clean package -DskipTests

# Run aşaması
FROM amazoncorretto:21

# Çalışma dizinini ayarla
WORKDIR /app

# Build aşamasında üretilen jar dosyasını kopyala
COPY --from=build /app/target/courier-tracker-*.jar app.jar
COPY --from=build /usr/local/bin/wait-for-it.sh /usr/local/bin/

# Uygulamayı başlat
ENTRYPOINT ["/usr/local/bin/wait-for-it.sh", "postgres:5432", "--", "java", "-jar", "/app/app.jar"]
