FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN ./mvnw -B -q dependency:go-offline

COPY src src
RUN ./mvnw -B -q package -DskipTests

EXPOSE 3003

CMD ["java", "-jar", "target/event-balance-0.0.1-SNAPSHOT.jar"]