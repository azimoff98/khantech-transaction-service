# Khantech Transaction Service

Khantech Transaction Service is a Spring Boot 3.3.5 application written in Java 21. 
This service handles user transactions, supports manual approvals for transactions exceeding a predefined limit, and provides robust API documentation using Swagger.

## Features

* Transaction Processing: Manage user transactions including credit and debit operations.
* Manual Approval: Transactions exceeding a configurable limit require manual approval.
* Validation: Built-in transaction validation for balance sufficiency and transaction types.
* In-Memory Database: H2 database for easy setup and testing.
* API Documentation: Swagger integration for API exploration and testing.

## Technologies Used

* Java 21
* Spring Boot 3.3.5
* Spring Web
* Spring Data JPA
* Spring Validation
* Spring Transaction Management
* H2 Database (In-Memory)
* Lombok for boilerplate code reduction
* Swagger for API visualization and testing
* JUnit 5 and Spock Framework for unit and integration tests
* SLF4J for logging

## Room for improvement
* Use **Quarts Scheduler** to handle scheduling effectively in multi-instance environment. 
* Transitioning to a robust persistent database like PostgreSQL or MySQL to ensure data durability and better support for large-scale transactions
* Implement distributed locking mechanisms, such as RLock, to support parallel processing across multiple instances. 

## Setup and Running the Application

### Prerequisites

* Java 21 or higher
* Maven

### Clone the Repository

`git clone https://github.com/yourusername/khantech-transaction-service.git`

`cd khantech-transaction-service`

### Build and Run the Application

`./mvnw clean install`

`./mvnw spring-boot:run`

The application will start on http://localhost:8080.

## API Documentation

Swagger is available to explore and test the APIs.
Visit: http://localhost:8080/swagger-ui/index.html

## H2 Database Console


Access the H2 database console at: http://localhost:8080/h2-console

* JDBC URL: `jdbc:h2:mem:testdb`
* Username: `sa`
* Password: 


## Configuration

Manual Approval Limit
The manual approval limit is configurable via the `application.yml` file:

`transaction-service:`
    `transactions:`
        `manual-approval-limit: 1000`

