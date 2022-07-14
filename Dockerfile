FROM library/openjdk:18 as build
COPY . /project
RUN cd /project && \
    ./mvnw package

FROM amd64/openjdk:18-oracle as prod
RUN mkdir /app &&  \
    useradd app && \
    chown app /app
COPY --from=build /project/target/test-backend-java-1.0-SNAPSHOT.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
CMD "java" "-Dspring.datasource.url=${DB_URL}" "-Dspring.datasource.driver-class-name=${JDBC_CLASS}" "-Dspring.datasource.username=${DB_USERNAME}" "-Dspring.datasource.password=${DB_PASSWORD}" "-jar" "app.jar"

