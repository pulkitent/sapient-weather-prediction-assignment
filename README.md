# Weather Prediction Service

This service purpose is to predict hourly weather conditions for a city

### Pre-requisites (for running the service)

* [Docker](https://docs.docker.com/compose/install/)

### Run using Docker

1. Run `docker build --tag=weather-prediction-service:latest .` to build Docker image
2. Run `docker run -p 8080:8080 weather-prediction-service:latest` to run the service
3. Example CURL to fetch some data `curl --location 'http://localhost:8080/api/v1/weathers?city=delhi&uit=metric'`

### API documentation (Swagger)

1. Start the service using above steps
2. Open `http://localhost:8080/swagger-ui/` in your browser

### IntelliJ Setup

1. Add lombok plugin (Preferences > Plugins > Search for Lombok)
2. Enable annotation processing (Preferences > Build, Execution, Deployment > Compiler > Annotation Processors)

### Setting up your local environment for development

1. Install [Docker](https://www.docker.com/community-edition)
2. Install Java 11
3. Install Gradle 7
4. Run `./gradlew clean test` to run the unit tests
5. Run `./gradlew clean build` to build the service
6. Run `./gradlew clean bootRun` to start service
7. Run `./gradlew clean bootJar` to create fat JAR

## Things I have tried to cover

* I have used **dependency injection** as much as possible.
* I have tried to make logical & small commits.
* I have integrated Swagger for documentation.
* I have used Factory & Strategy pattern to evaluate multiple weather conditions.
* I have added ci-cd.yml to setup CI CD pipeline in Github flow.
* I have also tried to Dockerize the application.
* I have created immutable states with value objects wherever possible.
* I have added readable methods & variables naming to clear the intention (**4 rules of simple design**).
* I have added independent unit tests in **"Given When Then"** format with 83% coverage & with mocking wherever
  required & possible.
* I didn't make interfaces for all the classes as per **YAGNI** principles because for now, I don't feel the need for
  the same. Yes, I am aware of this principle also - "Program to interfaces rather than concrete implementation".
* I have tried to accommodate SOLID principles as much as possible.

## Things I could have covered if given more time

* I could have added an integration level test (SITs) by mocking external API call.
* I could have encrypted the API key of OpenWeather APIs.
* I could have added the support offline mode with toggles.
* I could have added basic UI on React/Angular.

## Implementation approach

This is a simple RESTful Web service create using SpringBoot framework using 3 layered architecture. I wanted to make
this application maintainable & extensible. I have a vision that someone should be able to change
business logic or add new requirements with minimalistic code changes.

So in order to achieve that I used [conditions.yml](src%2Fmain%2Fresources%2Fconditions.yml) where I have mentioned 3
main things

1. operator
2. value
3. advice

So one can try update the advice & operator by just restarting the service so to refresh in-memory configurations.