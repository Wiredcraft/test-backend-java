# Wiredcraft Back-end Test Project

## What is this about?

This application let you make friends with people nearby, it has the following features:

- See people nearby  (location required)
- Follow someone you're interested in.
- Unfollow someone you're no longer interested.
- Update your profile.
- Sign in & sign up.

What is even cooler is that we let you see people nearby as guest!

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

### First about frond-end

- [Vue.js](https://vuejs.org/) 
- [Element](https://element.eleme.cn/#/zh-CN)




## Running in local environment

### 0. Prerequisites

Please install [Docker](https://www.docker.com/products/docker-desktop/) first on your local machine, this is the only prerequisite.

### 1. Start MySQL

This application requires MySQL database for user data persistence.

You can quickly start a MySQL database instance by executing the following command:

```shell
docker run --name mysql-server -p3306:3306 -e MYSQL_ROOT_PASSWORD='?2bW$PlGaY@7JKU#xY' -e MYSQL_DATABASE=user_service -e MYSQL_USER=user_service -e MYSQL_PASSWORD='^Mdh2jsk6sh#k2$' -d mysql:8
```

上述命令后台启动了一个 8.0 版本的 MySQL 数据库实例，实例信息如下：

| 属性    | 值         |
|-------|-----------|
| 数据库版本 | MySQL 8.0 |
| 主机地址  | 127.0.0.1 |
| 端口    | 3306      |
| 数据库名称   | user_service       |
| 用户名     | user_service       |
| 用户密码    | ^Mdh2jsk6sh#k2$    |
| root 密码 | ?2bW$PlGaY@7JKU#xY |

通过下述指令可以进入容器，以便了解 MySQL 实例情况：

```shell
docker exec -it mysql-server /bin/bash
```

如果启动异常则可以通过以下指令获取 MySQL 启动日志：

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

Most modern browser has a rich set of API, you get coordinates data by calling JS.

But it only works in HTTPS, therefor, for this demo, you have to input your coordinates manually.

**About security.**

It's typical a bad idea to store credential in VCS, again this is only for demo purpose.