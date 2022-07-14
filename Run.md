## Steps to run the test-backend

Run   
```
mvn package
```
to package the program, then you can find test-backend-java-1.0-SNAPSHOT.jar in target/

Because there's no connection string and credentials and jdbc class in application.properties, you should give them    
in command line. Just replace these properties to corresponding values on your database and run. Btw, if you want to change from mysql to some of other dbs, remember to add maven dependency in pom.xml.
```
java  \
 -Dspring.datasource.url='jdbc:mysql://192.168.2.9:3306/test-backend' \
 -Dspring.datasource.driver-class-name=com.mysql.jdbc.Driver \
 -Dspring.datasource.username='root' \
 -Dspring.datasource.password='123456' \
 -jar test-backend-java-1.0-SNAPSHOT.jar
```

If you want to get documents of apis it provided and test api in local environment, you can just run:  
```
mvn -Dexec.mainClass=com.lyt.backend.MainTest -Dexec.classpathScope=test test-compile exec:java
```
then access [Swagger-UI](http://localhost:8080/swagger-ui/index.html#/Documents%20of%20homework%20test-backend%2C%20containing%20all%20crud%20apis%20for%20user) on localhost for docs.You can test api through swagger-ui.If you want to view api document without running maven, you can access [api-docs](http://localhost:8080/v3/api-docs) on localhost and copy the json from the browser, you can use this json on a standalone swagger instance, visit [Swaggeer-UI](https://swagger.io/tools/swagger-ui/) to get more help.  

## Docker
Run  
```
docker build . -t test_backend:latest
```
to build the image, then you can replace the environment variables of the following command with your own values and run it.  
```
docker run -it \
-e DB_URL=jdbc:dbtype://IP:Port/your-data-base \
-e JDBC_CLASS=your-jdbc-connector-class \
-e DB_USERNAME=your-db-username \
-e DB_PASSWORD=your-db-password \
-p 8080:8080 \
test_backend:latest
```
Then you can check the application at localhost:8080

## AWS 
The application is deployed on AWS by me, you can visit the endpoint at lb-wiredcraft-test-backend-1747272980.us-east-1.elb.amazonaws.com for testing before this interview ends.  

I used Amazon ECS to deploy the application and RDS(mysql) as persistent store, then exposed the service with an LB given above.