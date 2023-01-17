# My homework





### User Model 

```
{
  "id": "xxx",                  // user ID 
  "name": "test",               // user name
  "dob": "",                    // date of birth
  "address": "",                // user address
  "description": "",            // user description
  "createdAt": ""               // user created date
}
```

## Environment Requirements

- JDK 1.8
- mysql 8.0 root/123 127.0.0.1:3306 database name : wiredcraft_user
- mongoDB 6.0 mongodb://localhost:27017/   use for geo search
- clone the code in Intellij IDEA ,  it will download library automatically  for you

## Initial database structure

```
/sql/user.sql for mysql
/sql/mongoDB.json for mongoDB
```



### Tech stack

| Framework              | Version | Description                                |
| ---------------------- | ------- | ------------------------------------------ |
| SpringBoot             | 2.7.0   | MVC framework                              |
| SpringSecurity         | 5.7.1   | Authentication and authorization framework |
| MyBatis                | 3.5.9   | ORM framework                              |
| MyBatis-Plus           | 3.5.1   | MyBatis plus tool                          |
| MyBatis-Plus Generator | 3.5.1   | ORM code generation tool                   |
| Swagger-UI             | 3.0.0   | document generation tools                  |
| Druid                  | 1.2.9   | database connection tools                  |
| JWT                    | 0.9.1   | support jwt login                          |
| Lombok                 | 1.18.24 | Simplified object encapsulation tool       |

### Run the project

If you have prepared the environment  and executed the sql script ， Just run the main function that starts class WiredCraftApplication

### API Document

after you started the project,you can click http://localhost:8081/swagger-ui/#/ to read the API Document.

  ![Image text](https://github.com/nashifanhua/test-backend-java/raw/master/img/api_Iist.png)

## Guidline

> SpringSecurity is used to implement authentication and authorization. Some interfaces require a token to access. The access process requires authentication and authorization interfaces as follows.

- Access the Swagger-UI interface documentation： http://localhost:8081/swagger-ui/#/

- Call registration interface  /user/regiser , rember your password 
  ![Image text](https://github.com/nashifanhua/test-backend-java/raw/master/img/register.png)
- Invoke the login interface to get the token   request body like that:
  ![Image text](https://github.com/nashifanhua/test-backend-java/raw/master/img/login_for_token.png)


- Click on the Authorize button in the upper right corner to input the token and then access the related interfaces.

  ![Image text](https://github.com/nashifanhua/test-backend-java/raw/master/img/authorization.png)
  ```
  curl -X GET "http://localhost:8081/user/friend/list?pageNum=1&pageSize=5" -H "accept: */*" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5dWFvIiwiY3JlYXRlZCI6MTY3Mzg4Mzc1MDY4MiwiZXhwIjoxNjc0NDg4NTUwfQ.zHyLRto4hxI7Ky9rZ6K0S3lSpW2rtzDvN7Kru0TlwoNAQWbssxOsD6ODJwbstdP6G9zTlH4bTF7qHNdnkJCqUg"
  ```



### What have I implemented for the advanced requirements

- > Provide a complete user auth (authentication/authorization/etc.) strategy, such as OAuth. This should provide a way to allow end users to securely login, autenticate requests and only access their own information.
  >
  > I use jwt and springSecurtiy to complete the login function,  please follow the progress to register your account
- > Imagine we have a new requirement right now that the user instances need to link to each other, i.e., a list of "followers/following" or "friends". Can you find out how you would design the model structure and what API you would build for querying or modifying it?
  >
  > 
  >
  >  I create two table(**follower** and **friend**) to complete theses feature ，let me explain my design
  >
  > If you follow somebody, I will create a record on **follower** table 
  >
  > If you want get followers ,just use sql (select * from follower where follower_id = your id)
  >
  > If you want get list who you are following  ,just use sql (select * from follower where user_id = your id)
  >
  > But if  we need to get the list of friend， it will gets complicated，we need use poor performance sql to query the follower table . So when somebody follow someone ，I will check whether they had followed each other  ，if they did， I will create two record in **friend** table ,then it will be easier to return their relationship.   Also ,if any one of them unfollowed his friend , I will delete the two record in **friend** table
  >
  >  - This design can reduce the burden of the database




- > Related to the requirement above, suppose the address of user now includes a geographic coordinate(i.e., latitude and longitude), can you build an API that,
  > - given a user name
  > - return the nearby friends         
  >
  > I use mongoDB to implement this feature.It has a index type named 2dsphere.It is convenient for us to search by location.The implementation logic is as follows
  >
  > 1. there is url call .... you can report your location by using this interface 
  > 2. get list of your friend (I have implemented in previous feature)
  > 3. use a mongoD to find your closet friends


## What else can I do

- Use English to explain the demo running on my computer

- Explains the extension of these features to large systems

  


