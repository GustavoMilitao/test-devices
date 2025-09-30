# Devices API

## Overview
The Devices API is a RESTful service for managing device resources. It allows users to create, update, fetch, and delete devices, as well as filter devices by brand and state. The API is built using Spring Boot and follows best practices for RESTful design.

## Features
- Create a new device
- Fully and partially update existing devices
- Fetch a single device by ID
- Fetch all devices
- Fetch devices by brand
- Fetch devices by state
- Delete a single device

## Domain Model
The Device entity includes the following properties:
- **Id**: Unique identifier for the device
- **Name**: Name of the device
- **Brand**: Brand of the device
- **State**: Current state of the device (available, in-use, inactive)
- **Creation Time**: Timestamp of when the device was created (cannot be updated)

## Validations
- Creation time cannot be updated.
- Name and brand cannot be updated if the device is in use.
- Devices that are in use cannot be deleted.

## Setup Instructions
1. Clone the repository:
   ```
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```
   cd devices-api
   ```
3. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   > **Note:** You must have a MySQL server running and accessible as configured in `src/main/resources/application.properties` (see the `spring.datasource.*` properties) before running the application. You can use Docker Compose as described below.

   ```
   mvn spring-boot:run
   ```

## API Documentation
API endpoints and their functionalities are documented in the `src/main/resources/api-docs.yaml` file.

## Testing
Unit tests are included for the controller, service, and repository layers. To run the tests, use:
```
mvn test
```

## Docker
#### Before you can start

If you have previously run a MySQL container named `mysql-db`, you may see an error like:

```
Error response from daemon: Conflict. The container name "/mysql-db" is already in use by container ...
```

To resolve this, remove the existing container before running Docker Compose:

```
docker rm -f mysql-db
```

> **Note:** The API requires a running MySQL server, configured as in `src/main/resources/application.properties`.

### Option 1: Run everything with Docker Compose (recommended)

If you don't have Docker Compose, install it: https://docs.docker.com/compose/install/

Then, simply run:
```
docker compose up --build
```
This will start both the MySQL database and the API, with the correct dependencies and environment variables.

### Option 2: Use your own MySQL instance

You can run MySQL in any way you prefer. For a quick start with Docker, see the official MySQL Docker documentation: https://hub.docker.com/_/mysql

After your MySQL server is running and accessible, build and run the API:
```
docker build -t devices-api .
docker run --name devices-api -p 8080:8080 devices-api
```

## Contribution
Feel free to contribute to the project by submitting issues or pull requests.

## License
This project is licensed under the MIT License.