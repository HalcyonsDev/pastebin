FROM openjdk:17-jdk

WORKDIR /app

COPY target/pastebin-0.0.1-SNAPSHOT.jar /app/pastebin.jar

EXPOSE 8080

CMD ["java", "-jar", "pastebin.jar"]