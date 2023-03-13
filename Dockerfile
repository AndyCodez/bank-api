FROM openjdk:17
ADD target/money-transfer-0.0.1.jar money-transfer-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "money-transfer-0.0.1.jar"]