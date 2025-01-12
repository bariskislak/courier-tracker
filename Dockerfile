# Build aşaması
FROM maven:3.9.4-eclipse-temurin AS build

# Uygulamanın kaynak kodunu kopyala
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY .env /app/.env

# Maven ile uygulamayı build et
RUN mvn clean package -DskipTests

# Run aşaması
FROM amazoncorretto:21

# Çalışma dizinini ayarla
WORKDIR /app
# Build aşamasında üretilen jar dosyasını kopyala
COPY --from=build /app/target/courier-tracker-*.jar app.jar
COPY --from=build /app/.env .env

RUN curl -o /usr/local/bin/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
    && chmod +x /usr/local/bin/wait-for-it.sh
# Uygulamayı başlat
ENTRYPOINT ["/usr/local/bin/wait-for-it.sh", "postgres:5432", "--", "java", "-jar", "/app/app.jar"]
