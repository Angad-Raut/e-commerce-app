FROM openjdk:17
EXPOSE:8082
ADD target/e-commerce-app.war e-commerce-app.war
ENTRYPOINT ["java","-jar","e-commerce-app.war"]