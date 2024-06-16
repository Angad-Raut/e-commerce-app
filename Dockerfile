FROM openjdk:17
EXPOSE 8082
ADD target/e-commerce-app-0.0.1-SNAPSHOT.jar e-commerce-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","e-commerce-app-0.0.1-SNAPSHOT.jar"]