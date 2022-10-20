
## About project 
I am glad if you use mvn install in the first place because it's a maven project with children module. Plus, I build 
the project by spring-boot framework, and I use jpa to operate database. I am sure you guys are good at this. Other than
that, I supply test cases for api I supplied base `userInfoService`. And I chose H2 database as a test data source,so 
you can run any case without extra config.

## how to run this application
I guess the key point is that you need to replace your own mysql link,username and password. And I reaffirm that it's 
the only thing you need to do or else you might fail to run this application. After that, Run Application you can see
the logs about the container is running. If you find that there is any error I didn't mention here,just contact me plz.

## about document
you can visit http://localhost:8080/swagger-ui.html if you run this application in your local env. Then you will see
the documents for UserController. I believe it is clear enough to get anything you need.

## about advance requirement
1. If you wanna to have a completed auth system, you can find what you want at [omen-auth](https://github.com/JeremyZhangSuxing/omen-auth "completed oauth implements")。
2. We often add logs when error occurs, like IoException or SystemException, we can log the key data. It helps a lot when you trace the issues.
3. I see an advanced requirement is about dealing social-ship like weibo or facebook. I give my data model below here.
````json
{
  "follower_uuid": "uuid of a user who follows others",
  "followed_user_uuid": "uuid of a user who is be followed",
  "followed_status": "int value of [0 1] means that you follow sb or not",
  "be_followed_status": "int value of [0 1] means that you are followed sb or not"
}
````
example about the model
````yaml
userA follow userB 
uuid_a uuid_b 1 0
uuid_b uuid_a 0 1

userB follow userA
uuid_a uuid_b 1 1
uuid_b uuid_a 1 1

userA cancel follow userA
uuid_a uuid_b 0 1
uuid_b uuid_a 1 0

userB cancel follow userA
uuid_a uuid_b 0 0
uuid_b uuid_a 0 0
````
if you wanna to talk about more detail about this , I will be glad to have a conversation with you. you could contact me by **jeresuxing@163.com**