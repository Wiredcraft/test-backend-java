# How to Run
db type: mysql
db user: root
db password: 123456
db name: wiredcraft
db init sql:
CREATE TABLE `user` (
`id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'user ID',
`name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'user name',
`dob` date DEFAULT NULL COMMENT 'date of birth',
`address` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'user address',
`description` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'user description',
`created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'user created date',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

# cmd
mvn spring-boot:run -D"spring-boot.run.profiles"=dev

# API DOC URL
http://localhost:8080/api/swagger-ui.html




# Wiredcraft Back-end Developer Test

Make sure you read the whole document carefully and follow the guidelines in it.

## Context

Build a RESTful API that can `get/create/update/delete` user data from a persistence database

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

## Requirements

### Functionality

- The API should follow typical RESTful API design pattern.
- The data should be saved in the DB.
- Provide proper unit test.
- Provide proper API document.

### Tech stack

- Use Java and any framework.
- Use any DB.

### Bonus

- Write clear documentation on how it's designed and how to run the code.
- Write good in-code comments.
- Write good commit messages.
- An online demo is always welcome.

### Advanced requirements

*These are used for some further challenges. You can safely skip them if you are not asked to do any, but feel free to try out.*

- Provide a complete user auth (authentication/authorization/etc.) strategy, such as OAuth. This should provide a way to allow end users to securely login, autenticate requests and only access their own information.
- Provide a complete logging (when/how/etc.) strategy.
- Imagine we have a new requirement right now that the user instances need to link to each other, i.e., a list of "followers/following" or "friends". Can you find out how you would design the model structure and what API you would build for querying or modifying it?
- Related to the requirement above, suppose the address of user now includes a geographic coordinate(i.e., latitude and longitude), can you build an API that,
  - given a user name
  - return the nearby friends


## What We Care About

Feel free to use any open-source library as you see fit, but remember that we are evaluating your coding skills and problem solving skills.

Here's what you should aim for:

- Good use of current Java & API design best practices.
- Good testing approach.
- Extensible code.

## FAQ

> Where should I send back the result when I'm done?

Fork this repo and send us a pull request when you think it's ready for review. You don't have to finish everything prior and you can continue to work on it. We don't have a deadline for the task.

> What if I have a question?

Feel free to make your own assumptions about the scope of this task but try to document those. You can also reach to us for questions.
