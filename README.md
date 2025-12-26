# Flow
The entire flow consists of two main services that work together to process and manage orders.
1. cashinvoice-auth-service - handles user authentication and JWT token generation.
2. cashinvoice-order-service - create order and save the order as a JSON file.
3. cashinvoice-integration-service - poll the order JSON file and process it further (e.g., send to an external system).



# cashinvoice-order-service

This is a Spring Boot–based Order Service that provides REST APIs for creating and retrieving orders.
In addition to handling API requests, the service also persists order data as JSON files in the local filesystem.

Tech Stack

Java        : 21
Spring Boot : 3.3.5
Build Tool  : Maven

Build and Run Instructions
1.	Build the Project

From the project root directory, run:

mvn clean package

This command generates an executable JAR file in the target directory.
2.	Run the Application

Navigate to the target directory and run:

java -jar order-service-0.0.1-SNAPSHOT.jar

Once started, the application will run on:

http://localhost:9091

API Documentation (Swagger / OpenAPI)

The application exposes API documentation using SpringDoc OpenAPI.

Swagger UI can be accessed at:

http://localhost:9091/swagger-ui/index.html

All available endpoints can be explored and tested from this interface.

Available API Endpoints

The Order Service supports the following operations:
•	Create Order
•	Get Order by Order ID
•	Get Orders by Customer ID

File Persistence

Along with API processing, the service performs file-based persistence:
•	Each created order is written as a JSON file
•	JSON files are stored in the following directory:

input/orders

Notes
•	Java 21 must be installed and configured before running the application.
•	Ensure port 9091 is available on the local machine.



