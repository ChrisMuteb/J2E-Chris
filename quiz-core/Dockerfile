FROM adoptopenjdk:17-jdk-hotspot

WORKDIR /app

COPY target/quiz-core-1.0.0-SNAPSHOT.jar /app/quiz-core.jar
COPY src/test /app/src/test

RUN mvn test

CMD ["java", "-jar", "quiz-core.jar"]
