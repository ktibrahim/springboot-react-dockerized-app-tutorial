FROM openjdk:17
COPY target/talal-ibrahim-ontology-tools-test.jar app.jar
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mongodocker/mongodb", "-jar", "app.jar"]