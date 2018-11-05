# FoodPrints

An web application used to report different food options, made by a group of people. Showing what impact it has on the environment. The application will also make suggestions regarding what restaurant in the area that is suitable for having an environment friendly lunch.  
 
## Getting Started

You need to set following environment variables on your machine.

1. export FV_KEYSTORE_PASSWORD="changeme"
2. export spring_profiles_active=dev

### Prerequisites

Make sure the following programs are installed on your local machine.
 
 - [Java 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html) <br>
 - [Gradle](https://gradle.org/) 


### Installing

Open a terminal/cmd and locate the root folder of the application. Typ the following to install/run the application: gradle bootrun

The web application will start in development mode and generate a h2 database filled with mock data. 


For customisation see the following files located in resource folder:

```
application.properties
application-dev.properties
application-prod.properties
```

Resources exposed
```
https://localhost:8443/
https://localhost:8443/stats
https://localhost:8443/h2-console/
```

Api Resources exposed
```
"v1/restaurants/suggestion" - GET
"v1/statistics/year/per/month" - GET
"v1/statistics" - GET
"v1/goal" - GET
"v1/votes/" - POST
"v1/categories/" - GET

```

## Running the tests

Make sure spring_profiles_active is set to "dev" and go to root folder and run the following command: gradle test

## Deployment

When deploying the application do the following:

1. export spring_profiles_active=prod
2. FV_KEYSTORE_PASSWORDDB="yourDbPassword"
3. FV_KEYSTORE_USERNAMEDB="yourDbUsername"
4. open application-prod.properties add the url to your database

Add additional notes about how to deploy this on a live system

## Built With

* [Springboot](https://spring.io/projects/spring-boot) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management

## Authors

* **Initial work** - [Harald](https://github.com/Harald-billstein)
* **Initial work** - [Gabriella](https://github.com/gbjrk)
* **Initial work** - [Henrik](https://github.com/Jaernbrand)


See also the list of [contributors](https://github.com/harald-billstein/foodvoting/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

