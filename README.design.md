# solution design

## functional design
1. auth (register/login/auth/logout)
   - jwt token
1. profile api (get/update/delete)
   - api
      - restful api, but for the understanding-cost of every api, abandon the http put/delete method, just get/post  
      - api url style: camel case over snake case
   - storage
     - t_user (index and deleted flag)
1. follow/friend relations
   - api
     - follow/unfollow
     - remove fans/block (now not available)
   - storage
     - considering the increasing number of users over time 
       - maybe data sharding, create 2 redundant relational tables
       - maybe distributed transaction (like eventually consistency) to keep the consistency
       - maybe redis to improve the api performance(unavailable now)
     - t_following(my_id, following_id)
     - t_follower(my_id, follower_id)
1. nearby friends
   - api
     - given the username, list nearby friends
   - lib
     - using redis geo to do this

## non-functional design
1. third party login like oauth2

## uncertain
1. front-end project
1. docker deploy (if i have enough time)
1. online demo (aliyun?)
1. https

## lib/framework decision 
1. framework: spring/spring-mvc/spring-boot/spring-security
1. db: mysql (with innodb engine)
1. orm: mybatis-plus
1. redis
   - for auth-token
   - for geohash