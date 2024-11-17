FROM openjdk:17-alpine
WORKDIR /app

COPY target/zad2enigma-0.0.1-SNAPSHOT.jar /app/zadanie2.jar

CMD ["java", "-jar", "zadanie2.jar"]