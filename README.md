# PaysafeApp
This application is a monitoring application for Paysafe server APIs.

# Requirements
For building and running the application you need:

* JDK 1.8
* Maven 3+
* Postman (optional)

# Running the application locally
There are several ways to run a Spring Boot application on your local machine.

	1. One way is to execute the main method in the com.paysafe.app.PaysafeApplication class from your IDE. Follow the steps below-
	
		# Download the zip or clone the Git repository.
		# Unzip the zip file (if you downloaded one)
		# Open Command Prompt and Change directory (cd) to folder containing pom.xml
		# Open Eclipse
		# File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
		# Select the project
		# Choose the Spring Boot Application file (search for @SpringBootApplication)
		# Right Click on the file and Run as Java Application

	2. Alternatively you can use the Spring Boot Maven plugin like so:
		mvn spring-boot:run
		
Refer pom.xml for all project dependencies
Refer resources/application.properties for application-wide properties
		
#### Use "http://localhost:8080/swagger-ui.html" url for Swagger documentation or executing REST endpoints
#### Use "http://localhost:8080/v2/api-docs" url for referring API docs.
#### Run http://localhost:8080 to open the HAL Browser
	* For GET requests, use the Explorer input field directly
	* For POST requests, 
		* - Navigate to "Go to Entry Point" on top if not already on entry point
		* - Click the NON-GET button at the bottom
		* - Use the dialogue box to create the request
		
### Use the Postman client or Swagger UI or HAL Browser to execute the available REST endpoints

There are three endpoints in the application-

# Start monitoring a service

To start monitoring a service, the server requires being provided with all the properties that are required to start monitoring

	1. url - service url to be monitored
	2. interval - monitoring interval for the service
	
server returns the HTTP 200 Ok status code
server returns an updated entity in the established version

### Sample Request

POST http://localhost:8080/paysafe-api/monitor/start

Request Headers - "Content-Type: application/json"

```
{
    "url": "https://api.test.paysafe.com/accountmanagement/monitor",
    "interval": "10000"
}

```

### Sample Response

```
{
    "url": "https://api.test.paysafe.com/cardpayments/monitor",
    "interval": 5000,
    "links": [
        {
            "rel": "stop-service-monitoring",
            "href": "http://localhost:8080/paysafe-api/monitor/stop"
        },
        {
            "rel": "server-status",
            "href": "http://localhost:8080/paysafe-api/monitor/status"
        }
    ]
} 

```

# Stop monitoring a service

To stop monitoring a service, the server requires being provided with all the properties that are required to stop monitoring

	1. url - service url to be monitored
	
server returns the HTTP 200 Ok status code
server returns an updated entity in the established version

### Sample Request

POST http://localhost:8080/paysafe-api/monitor/stop

Request Headers - "Content-Type: application/json"
 
 ```
{
    "url": "https://api.test.paysafe.com/accountmanagement/monitor",
}

```

### Sample Response

```
{
    "url": "https://api.test.paysafe.com/cardpayments/monitor",
    "links": [
        {
            "rel": "start-service-monitoring",
            "href": "http://localhost:8080/paysafe-api/monitor/start"
        },
        {
            "rel": "server-status",
            "href": "http://localhost:8080/paysafe-api/monitor/status"
        }
    ]
}

```


# Retrieve monitoring statistics

server returns the HTTP 200 Ok status code

### Sample Request

GET http://localhost:8080/paysafe-api/monitor/status

### Sample Response

```

{
    "https://api.test.paysafe.com/cardpayments/monitor": {
        "statusData": [
            {
                "status": "READY/UP",
                "currenttime": "2019-10-14T17:26:34.202",
                "monitoringInterval": 5000
            },
            {
                "status": "READY/UP",
                "currenttime": "2019-10-14T17:26:38.717",
                "monitoringInterval": 5000
            },
            {
                "status": "READY/UP",
                "currenttime": "2019-10-14T17:26:43.742",
                "monitoringInterval": 5000
            }
        ]
    },
    "https://api.test.paysafe.com/accountmanagement/monitor": {
        "statusData": [
            {
                "status": "READY/UP",
                "currenttime": "2019-10-14T17:25:15.14",
                "monitoringInterval": 10000
            },
            {
                "status": "READY/UP",
                "currenttime": "2019-10-14T17:25:24.639",
                "monitoringInterval": 10000
            }
        ]
    }
}

```






