# Wiredcraft Back-end Test Project

## What is this about?

This application let you make friends with people nearby, it has the following features:

- See people nearby  (location required)
- Follow someone you're interested in.
- Unfollow someone you're no longer interested.
- Update your profile.
- Sign in & sign up.

What is even cooler is that we let you see people nearby as guest!

## Online demo

I've set up a online demo at [http://15.165.161.56/index.html](http://15.165.161.56/index.html)

The following accounts are provided for demo, you can try to sign in with anyone of them or create your own:
- steve
- jerry
- alan
- james
- joshua
- richard

They share the same password `123456`.

## Online API Docs

[http://15.165.161.56/swagger-ui/index.html](http://15.165.161.56/swagger-ui/index.html)

## Tech stack

### First about backend

**Persistence**
- Database is MySQL 8.0.
- [Docker](https://docker.com/) is for quickly setting up database.
- [Flyway](https://flywaydb.org/documentation/getstarted/firststeps/maven) for database versioning.

**Web framework**
- [Spring Boot](https://spring.io/projects/spring-boot), provides nice auto configuration features.
- Spring Web, absolute must-have.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa), nice persistence power, requires minimum configuration.

**Authentication & Authorization**

We support modern JWT token authentication with HS265 algorithm for better security guarantee.

The secret and token expire time are configurable via `application.properties` file:

- `security.access-token.expire-hours` Token Expire Time in hours, default is 24 hours.
- `security.access-token.secret` Secret for HS265, default is I&PYEkqGenob9H$zdwm7$TH6D9i@w&rc

- [Spring Security](https://spring.io/projects/spring-security)
- [Auth0 JWT](https://github.com/auth0/java-jwt)

**API Docs**

- [SpringFox](https://springfox.github.io/springfox/)

### Then about frond-end

Instead of installing npm, I try to use HTML pages to write the frontend demo, keep myself concentrate on the backend part.

- [Vue.js](https://vuejs.org/) 
- [Element](https://element.eleme.cn/#/zh-CN)

## Design ideas & Implications

The requirements are somewhat discrete, so I decided to design and develop an application that meets these requirements,
more importantly it's actually usable!

### Model design
[https://www.processon.com/diagraming/61df965a07912973ef20f7af](https://www.processon.com/diagraming/61df965a07912973ef20f7af)

### Architecture design
[https://www.processon.com/diagraming/6278bdc91e0853075333dbfe](https://www.processon.com/diagraming/6278bdc91e0853075333dbfe)

### Implications

The actual implementation may differ from the requirements.

## Running in local environment

### 0. Prerequisites

Please install [Docker](https://www.docker.com/products/docker-desktop/) first on your local machine, this is the only prerequisite.

### 1. Start MySQL

This application requires MySQL database for user data persistence.

You can quickly start a MySQL database instance by executing the following command:

```shell
docker run --name mysql-server -p3306:3306 -e MYSQL_ROOT_PASSWORD='?2bW$PlGaY@7JKU#xY' -e MYSQL_DATABASE=user_service -e MYSQL_USER=user_service -e MYSQL_PASSWORD='^Mdh2jsk6sh#k2$' -d mysql:8
```

The above command will start a MySQL 8.0 database instance, the instance infomation is as follows:

| Property      | Value              |
|---------------|--------------------|
| Version       | MySQL 8.0          |
| Host          | 127.0.0.1          |
| Port          | 3306               |
| Database name | user_service       |
| Username      | user_service       |
| Password      | ^Mdh2jsk6sh#k2$    |
| root Password | ?2bW$PlGaY@7JKU#xY |

Use following command to enter MySQL container to inspect MySQL status:

```shell
docker exec -it mysql-server /bin/bash
```

If there's any problem, you can check out MySQL running log by issuing：

```shell
docker logs mysql-server
```

### 3. Boot Application

Please use your favorite IDE or Maven to boot this application.

### 4. API Docs

This API uses Swagger for API documentations, the docs is at [http://localhost/swagger-ui/index.html#/](http://localhost/swagger-ui/index.html#/)

### 5. Demo

For better experience, this application provides a small demo based on Vue.js & Element.io.

You are free to explore the demo on [http://localhost/index.html](http://localhost/index.html)

## Caveats & Security

**About Coordinates**
Most modern browser has a rich set of API, you get coordinates data by calling JS.

But it only works in HTTPS, therefor, for this demo, you have to input your coordinates manually.

**About security.**

1. It's typical a bad idea to store credential in VCS, again this is only for demo purpose.
2. HTTPS will be required for actual application.

**About Lombok**

IDEA CE version seems having issue with lombok, so it's not used in this application.