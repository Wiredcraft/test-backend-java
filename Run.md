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

If you want to get documents of apis it provided, you can just run:  
```
mvn -Dexec.mainClass=com.lyt.backend.MainTest -Dexec.classpathScope=test test-compile exec:java
```
then access [Swagger-UI](http://localhost:8080/swagger-ui/index.html#/Documents%20of%20homework%20test-backend%2C%20containing%20all%20crud%20apis%20for%20user) on localhost for docs.You can test api through swagger-ui.If you want to view api document without running maven, you can access [api-docs](http://localhost:8080/v3/api-docs) on localhost and copy the json from the browser, you can use this json on a standalone swagger instance, visit [Swaggeer-UI](https://swagger.io/tools/swagger-ui/) to get more help.