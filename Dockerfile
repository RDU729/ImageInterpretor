FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
ADD target/image-interpretor-v2.jar image-interpretor-v2.jar
ENTRYPOINT ["java","-jar","image-interpretor-v2.jar"]