# how to design

### functional design

###### auth (signup/login/auth/logout)

    jwt token

###### profile api (get/update/delete)

    api:
        1. restful api, but for the understanding-cost of every api, abandon the http put/delete method, just get/post
        2. api url style: camel case over snake case
    storage: 
        t_user (index and deleted flag)

###### follow/friend relations

    api
        1. follow/unfollow
        2. remove fans/block (now not available)
    storage
        1. considering the increasing number of users over time
            maybe data sharding, create 2 redundant relational tables
            maybe distributed transaction (like eventually consistency) to keep the consistency
            maybe redis to improve the api performance(unavailable now)
        2. t_following(my_id, following_id)
           t_follower(my_id, follower_id)

###### nearby friends

    api
        given the username, list nearby friends
    lib
        using redis geo to do this

### non-functional design

    third party login like oauth2

### uncertain

    1. docker deploy (if i have enough time)
    2. front-end project
    3. online demo (aliyun?)
    4. https

### lib/framework decision

    1. framework: spring/spring-mvc/spring-boot/spring-security
    2. db: mysql (with innodb engine)
    3. orm: mybatis-plus
    4. redis
        for auth-token
        for geohash

# how to run

### env preparation

    jdk 1.8+
    docker and docker-compose 

###### 1. run mysql/redis in docker

```shell
cd ./docker
docker compose up -d
```

###### 2. app

    run the java spring boot app (application.yml)

### 3. access api doc

    http://127.0.0.1/swagger-ui.html

### api flow

###### login api

1. signup to get token (for front-end project, it may store in localstorage, the user will auto login)
    - curl
   ````
   curl -X POST "http://localhost:8080/user/signUp" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"address\": \"shanghai\", \"description\": \"muggle\", \"name\": \"demo1\", \"passwd\": 123}" 
   ````
    - resp: the data is response token
   ````json
   {"code": "0", "message": "success", "data": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5YmIzMmM4Y2RiZGU0YzRiOGE4YWQ5Zjg3ZmNjNDc2YSIsInN1YiI6IjUiLCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjg0NzUwMDE5LCJleHAiOjE2ODQ3NTM2MTl9.txCeKEsgO9Vz0GUjSVeTxXrQe-vrEiFq6VY8VjNfJKs"}
   ````

2. login with name/password to get token
    - curl
   ```
   curl -X POST "http://localhost:8080/user/login" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"demo1\", \"passwd\": 123}"
   ```
    - resp: the data is response token
   ````json
   {"code": "0", "message": "success", "data": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1N2VkYTI5YWU0Mzk0MTFjYTZkNzM3YmQzMjEwOWY0MSIsInN1YiI6IjUiLCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjg0NzUwOTUyLCJleHAiOjE2ODQ3NTQ1NTJ9.CYolQrGnzWunah-2kQiqeDAmqVsZvvfn-dDYXDZ6Dwk"}
   ````

3. logout with token
    - curl
   ```
   curl -X POST "http://localhost:8080/user/logout" -H "accept: */*" -H "token: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1N2VkYTI5YWU0Mzk0MTFjYTZkNzM3YmQzMjEwOWY0MSIsInN1YiI6IjUiLCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjg0NzUwOTUyLCJleHAiOjE2ODQ3NTQ1NTJ9.CYolQrGnzWunah-2kQiqeDAmqVsZvvfn-dDYXDZ6Dwk"
   ```
    - resp
   ```
   { "code": "0", "message": "success", "data": null }
   ```

###### profile api

1. get profile with token
    - curl
   ```
   curl -X GET "http://localhost:8080/user/myProfile" -H "accept: */*" -H "token: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlZWYyZjE5ZWU0Njk0YjBmYTZkZTVlYjk3Mzg5MGEzZiIsInN1YiI6IjUiLCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjg0NzUxMjU4LCJleHAiOjE2ODQ3NTQ4NTh9.odUmj-_gO1LVu-nMbek6F1Ga1FjA-RZM2LA6ayzH21A"
   ```
    - resp
   ```
   { "code": "0", "message": "success", "data": { "id": 5, "name": "demo1", "dob": null, "address": "shanghai", "description": "muggle", "createdAt": null } }
   ```
2. update profile with token
    - curl
   ```
   curl -X POST "http://localhost:8080/user/updateProfile" -H "accept: */*" -H "token: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlZWYyZjE5ZWU0Njk0YjBmYTZkZTVlYjk3Mzg5MGEzZiIsInN1YiI6IjUiLCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjg0NzUxMjU4LCJleHAiOjE2ODQ3NTQ4NTh9.odUmj-_gO1LVu-nMbek6F1Ga1FjA-RZM2LA6ayzH21A" -H "Content-Type: application/json" -d "{ \"address\": \"shanghai\", \"description\": \"new muggle\"}"
   ```
    - resp
   ```
   { "code": "0", "message": "success", "data": null }
   ```
3. update password with token
    - curl
   ```
   curl -X POST "http://localhost:8080/user/updatePasswd" -H "accept: */*" -H "token: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlZWYyZjE5ZWU0Njk0YjBmYTZkZTVlYjk3Mzg5MGEzZiIsInN1YiI6IjUiLCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjg0NzUxMjU4LCJleHAiOjE2ODQ3NTQ4NTh9.odUmj-_gO1LVu-nMbek6F1Ga1FjA-RZM2LA6ayzH21A" -H "Content-Type: application/json" -d "{ \"passwd\": \"123new\"}"
   ```
    - resp
   ```
   { "code": "0", "message": "success", "data": null }
   ```
4. del account
    - curl
   ```
   curl -X POST "http://localhost:8080/user/delAccount" -H "accept: */*" -H "token: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlZWYyZjE5ZWU0Njk0YjBmYTZkZTVlYjk3Mzg5MGEzZiIsInN1YiI6IjUiLCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjg0NzUxMjU4LCJleHAiOjE2ODQ3NTQ4NTh9.odUmj-_gO1LVu-nMbek6F1Ga1FjA-RZM2LA6ayzH21A"
   ```
    - resp
   ```
   { "code": "0", "message": "success", "data": null }
   ```

###### follow api

1. add following with token
    - curl
   ```
   curl -X POST "http://localhost:8080/follow/addFollowing?userId=6" -H "accept: */*" -H "token: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyMWM3NTYzYzI4MjQ0ZTdmOTkxMzk0YTUyYWExMDM1NiIsInN1YiI6IjEiLCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjg0NzUxODQyLCJleHAiOjE2ODQ3NTU0NDJ9.Biw29hryvbKUdu2dUhS6zD3_ils4t2VrgTERDuA5_i8"
   ```
    - resp
   ```
   { "code": "0", "message": "success", "data": null }
   ```
2. remove following with token
    - curl
   ```
   curl -X POST "http://localhost:8080/follow/delFollowing?userId=6" -H "accept: */*" -H "token: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyMWM3NTYzYzI4MjQ0ZTdmOTkxMzk0YTUyYWExMDM1NiIsInN1YiI6IjEiLCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjg0NzUxODQyLCJleHAiOjE2ODQ3NTU0NDJ9.Biw29hryvbKUdu2dUhS6zD3_ils4t2VrgTERDuA5_i8"
   ```
    - resp
   ```
   { "code": "0", "message": "success", "data": null }
   ```
3. list followings with token
    - curl
   ```
   curl -X GET "http://localhost:8080/follow/listMyFollowings" -H "accept: */*" -H "token: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkN2ZiZDA4NTg2MjA0NmY4OWFkZmQwMDcwMDJlZjYyMiIsInN1YiI6IjUiLCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjg0OTI3OTc0LCJleHAiOjE2ODQ5MzE1NzR9.77uzfUigLrTFBrxfnGass9NVvPIoRzk0S5yiFUz7fJ4"
   ```
    - resp
   ```json
   { "code": "0", "message": "success", "data": [ { "id": 9, "name": "demo1115", "dob": "2023-05-24T08:57:22.000+00:00", "address": "shanghai", "description": "muggle", "beingFriend": false }, { "id": 8, "name": "demo1114", "dob": "2023-05-24T08:57:22.000+00:00", "address": "shanghai", "description": "muggle", "beingFriend": false }, { "id": 7, "name": "demo1113", "dob": "2023-05-24T08:57:22.000+00:00", "address": "shanghai", "description": "muggle", "beingFriend": false }, { "id": 6, "name": "demo1112", "dob": "2023-05-24T08:57:22.000+00:00", "address": "shanghai", "description": "muggle", "beingFriend": false } ] } 
   ```

4. list followers with token(not available now)
5. block follower (not available)

###### nearby friend api

list nearby friends with geo-hash(not available)
