## Clone the project

```
git clone https://github.com/ktibrahim/springboot-react-dockerized-app-tutorial.git
```
## Frontend
Frontend is built in React and integrated in the spring boot application. It is available in the frontend folder. The spring boot application is configured to build and package React with Spring Boot through maven. You do not need to do anything extra to build frontend separately, maven will take care of it via frontend-maven-plugin.
* After building the application, instructions provided in [section](#testing-and-building-the-project), you can access frontend from browser at "localhost:5000".
* Both frontend and backed are using the same port i.e. 5000, the difference is in path, for backend "/api" is appended in the path whereas frontend does not have it.
* Everything will work just fine if you start at the root, since React will handle routing. To make sure you are able to reload the React application in the browser, a filter in ReactRequestForwardFilter class intercepts the requests and conditionally forwards to the React app.

## Backend
* The backend is implemented as a spring boot application in Java 17. It uses MongoDB as a local database with no enabled access control, it listens on port 27017 and is exposed at port 27071. Details available in [section](#pull-&-run-mongodb-docker-container-in-the-local-machine)
* The application exposes 3 REST APIs for storing and retrieving information regarding ontologies.
* The application is containerised through docker, instructions to build and run the docker image for the project is provided in [section](#dockerize-the-project section).

### Backend APIs
* Get Ontologies list: this is a GET method with path **/api/ontologies**
* Get Ontology by Id: this is a GET method with path **/api/ontologies/{ontologyId}** where "{ontologyId}" is the ontology Id
* Post Ontology: This is the POST method with path **/api/ontologies**. It accepts the data in json format. OntologyId, title and description fields are mandatory. Sample json provided for your reference.
  * ontologyId - String
  * title - String
  * description - String
  * definitionProperties - List of String
  * synonymProperties  - List of String

```
{
"ontologyId": "mondo",
"title": "Mondo Disease Ontology",
"description": "A semi-automatically constructed ontology that merges in multiple disease resources to yield a coherent merged ontology.",
"definitionProperties": ["a", "b"],
"synonymProperties": ["c", "d"]
}
```

**application.properties**
```
server.servlet.context-path=/
server.port=5000
spring.data.mongodb.uri=mongodb://localhost:27071/mongodb
```
## Dockerfile
Here, we are using open JDK 17, we set up the application build in Dockerfile.
* **FROM**: from where we are getting the image
* **ADD**: build path and the deploying application JAR name — This should be same as the finalName in the pom xml.
* **ENTRYPOINT**: entry command to RUN the JAR file connected with docker. Notice spring.data.mongodb.uri value passed as this would be needed for the spring boot app to connect to mongodb container when dockerizing the project

```
FROM openjdk:17
ADD target/talal-ibrahim-ontology-tools-test.jar app.jar
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mongodocker/mongodb", "-jar", "app.jar"]
```

## Docker Compose file
* Version: docker version
* Services: connecting applications/services — mongo and spring boot app
* Under services, we have defined the parameters accordingly. Service names (app and mongo-db) are just identifiers for those components.
* Container name for the Spring Boot app is defined as _app_. We will use the application image to create this container, after building it using docker-compose. We have to provide source path to the build. Here we use “.” to denote the current root folder. external port is 5001 and internal 5000.
* In contrast to above service setup, we have to set up mongo container using MongoDB base image that we have pulled at the beginning. The container name is defined as mongodb. Internal port mongodb listening is 27017 and external port exposed is 27070.
* We need a network. Otherwise, we cannot communicate between mongodb and the backend application container. Both should be inside a network.
* When we use docker-compose, it automatically creates a network with a default name.

# Testing and building the project

## Pull & Run Mongodb docker container in the local machine
* This step is required to build and test the spring boot app locally via maven. 
* When dockerizing the application we will build mongodb along with the project in a single image.
* Before building the spring boot application, pull mongodb docker image if not present.

```
docker pull mongo
```

Run the mongodb docker image by executing below command. It runs the db with the name mongodb & exposes port 27071.

```
docker run --name mongodb -p 27071:27017 -d mongo
```

You can check if the mongodb container is running by executing below command.

```
docker ps
```

## Build and install the application
Now in the project's root directory execute below command to build the project's JAR, this will also run tests. Java 17 is required to build the project.

```
mvn clean install
```

* It will take sometime to install dependencies and build the project.
* The jar will be created in target folder with the name _talal-ibrahim-ontology-tools-test.jar_
* You can run the application by mvn spring-boot command.

```
mvn spring-boot:run
```

You can test the ontology API from the browser via following URL
_http://localhost:5000/api/ontologies/efo_

You can test the ontology React App from the browser via following URL
_http://localhost:5000_

# Dockerize the project

## We will use Docker-compose to build the project's docker image

**Build the Docker Image**
In the project's root directory, execute below command to build the project's docker image.

```
docker-compose build
```

Run _docker images_ command to see the images available. You should see _ontology-tools-developer-test-app_ image created

### Run the containers
Run below command to run the containers in detached mode with a "-d" flag. The containers will run in the background

```
docker-compose up -d
```

There should be 2 steps completed.
* Spring boot application container should be created **backend-app**. This was defined as the container name in docker-compose file.
* Mongodb container is also created **mongodocker** through a service defined in docker-compose file.

If you want to see the running containers, execute below command in terminal

```
docker ps
```

## Test API
Try get ontology id endpoint

```
http://localhost:5001/api/ontologies
```

Frontend

```
http://localhost:5001
```

