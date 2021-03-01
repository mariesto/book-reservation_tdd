# Book Reservation
[![codecov](https://codecov.io/gh/mariesto/book-reservation_tdd/branch/master/graph/badge.svg?token=JV3N4C8ZPC)](https://codecov.io/gh/mariesto/book-reservation_tdd)
[![Build Status](https://travis-ci.com/mariesto/book-reservation_tdd.svg?branch=master)](https://travis-ci.com/mariesto/book-reservation_tdd)

Build simple Rest API using Spring Boot by implementing TDD. 
Using Maven as dependency management, Mockito an JUnit as for testing extension and H2 as in-memory database. 
Create test case for each layer (persistence, service, controller).
Using Codecov for coverage, TravisCI for CI/CD and Docker for containerisation.

![image](src/main/resources/img/BookReservation.png)

## Getting Started

##### Prerequisites
Before using this app, you need to prepare several things listed below. 
If you're able to run all these commands below, you're good to go :partying_face:

1. Installed Java
   ```properties
   java -version
   ```  
2. Maven
    ```properties
   mvn -v
    ```  
3. Git
    ```
   git --version
   ```
4. Docker
    ```
   docker version
   ```

##### How to Run

To run this project, you have 3 options :
   1. Run in you local
      * Clone this repository 
      * You can run test and build the project by running `mvn clean package` 
      * Start the app by typing `mvn spring-boot:run` from the root project directory to start the application.
      * Now you can hit `localhost:8080/api/v1/books/` either using cUrl or Postman and play with it :nerd_face:
      * Since we're using H2 as our in-memory database, you can launch the console by visiting http://localhost:8080/h2-console
      * :soon: Will add API documentation using Swagger
    
   2. Run using docker 
      * First, pull the docker image `docker pull mariesto/book-reservation-spring-boot`
      * Make sure it's already pulled `docker image ls`
      * Run the docker `docker run -p 8080:8080 book-reservation-spring-boot`
      * Now you can use the API as you wish 
    
   3. Save the best for the last (Play with Docker) :star_struck:
      * You don't need docker installed in your local to use this, but you need to create your Docker Hub account first.
      * Then you can visit this page https://labs.play-with-docker.com/
      * In the terminal you can just run `docker run -p 8080:8080 mariesto/book-reservation-spring-boot`
      * Now you can click on the “Open Port” button and type in 8080
      * Adjust the URL with `/api/v1/books/` and there you go :boom:
      * You can also visit this page for reference https://docs.docker.com/get-started/04_sharing_app/#run-the-image-on-a-new-instance  
