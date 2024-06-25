FROM openjdk:17
EXPOSE 8082
ADD target/e-commerce-app.jar e-commerce-app.jar
ENTRYPOINT ["java","-jar","e-commerce-app.jar"]