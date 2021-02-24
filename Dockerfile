FROM openjdk:latest
ADD target/book-reservation-spring-boot.jar book-reservation-spring-boot.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/book-reservation-spring-boot.jar"]