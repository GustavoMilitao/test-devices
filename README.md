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
To build the Docker image, run:
```
docker build -t devices-api .
```
To run the Docker container:
```
docker run -p 8080:8080 devices-api
```

## Contribution
Feel free to contribute to the project by submitting issues or pull requests.

## License
This project is licensed under the MIT License.