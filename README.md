# Questions-Base

## Project Description
  This project is an API whose main goals are: store exam's questions, retrieve it and check the user answer, as well as dealing with authentication.

## Main Technologies:
  - Java
  - Spring Boot (Spring Security, Spring Data Jpa, OAuth, Java Mail Sender...)
  - Mysql
  - Redis
  - Swagger

## Why I Choose These Technologies?
  Java was chosen because it is the best choice to work in an ORM environment, since Java is basically a pure object oriented language. Spring Boot was chosen because it offers an integrated way of dealing with basic auth, roles and OAuth 2.0 and an organised/standardized approach with the plain old MVC architecture. Mysql was chosen mainly because it is relational database in which I have the highest affinity. Redis was chosen because it stores the data all together, which is better for scalability, especially in a future implementation of microservices.

## How to Setup
  - Install [Java 17](https://www.oracle.com/java/technologies/downloads)
  - Install [Mysql](https://dev.mysql.com/downloads/mysql)
  - Install [Redis](https://redis.io/download) / [Redis For Windows](https://github.com/MicrosoftArchive/redis/releases/download/win-3.2.100/Redis-x64-3.2.100.msi)
  - Install [Maven](https://maven.apache.org/install.html)
  - Fork the project
  - Generate [Google OAuth Credentials](https://www.youtube.com/watch?v=xH6hAW3EqLk)
  - Turn on [Access to a Less Secure App](https://myaccount.google.com/lesssecureapps)
  - Change .env variables in application.properties
    - Change ${DB_USERNAME} to your mysql username (default root)
    - Change ${DB_PASSWORD} to your mysql password (default is nothing)
    - Change ${GOOGLE_CLIENT_ID} to your google client id
    - Change ${GOOGLE_CLIENT_SECRET} to your google client secret
    - Change ${SPRING_MAIL_USERNAME} to your gmail email
    - Change ${SPRING_MAIL_USERNAME} to your gmail password
   - Open the terminal in the root folder of the application and run ```mvn spring-boot:run```
