# Money Transfer API
This is a simple REST API that allows users to create and manage accounts, and transfer money between them. 
The API is built using Java 17 and Spring Boot 3.0.4, and uses a PostgreSQL database to store account information and transaction history. 
It is designed to be stateless and uses JWT for authentication and authorization.

## Features
The API includes the following endpoints:

`POST /api/v1/accounts`: Creates a new account with a starting balance.

`GET /api/v1/accounts/{id}`: Retrieves the account information.

`POST /api/v1/transfers`: Transfers money from one account to another.

`POST /api/v1/register`: Generates a JWT access token for a user.

`POST /api/v1/authenticate`: Authenticates user and responds with a JWT access token.

The API enforces the following constraints:

- An account cannot have a negative balance.
- The transfer amount must be greater than zero.
- If the source account does not have enough funds, the transfer will fail.
- The API is designed to handle concurrent requests and avoid race conditions.

## Getting Started
To build and run the API locally, you'll need the following dependencies:

Java 17

PostgreSQL

Docker (optional)

## Database Setup

Before running the API, you'll need to create a new PostgreSQL database:

```
CREATE DATABASE bank-api;
```

## Running the API
To run the API, you can either run it directly from your IDE or build a Docker image and run it inside a container.

### Running from IDE
Clone the repository.

Open the project in your favorite IDE.

Rename `application.properties.example` to `application.properties`.

Configure the database connection in `application.properties` to match yor db and db credentials.

Run the MoneyTransferApplication class.

### Running with Docker

Clone the repository.

Build the Docker image:

```
mvn install

docker build -t money-transfer-0.0.1.jar .
```
Run the Docker container:
```
docker run -p 8080:8080 money-transfer-0.0.1.jar
```
## Authentication and Authorization

The API is stateless and uses JWT for authentication and authorization. 
To authenticate, clients must send a POST request to `/api/v1/authenticate` with a valid username and password. 
The API will respond with a JWT access token, which clients must include in the Authorization header of all subsequent requests.

By default, the API is configured to use the HS256 algorithm to sign and verify JWT tokens.

## Testing
To run the tests, you can use the following command:

```mvn test```

## License
This project is licensed under the MIT License. See the LICENSE file for details.
