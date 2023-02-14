## User Guide

##### 1. jump to target folder from project root, and build mysql container.
```
$: cd ./docker/mysql
$: docker-compose up -d
```
##### 2. jump to target folder from project root, and build redis container.
```
$: cd ./docker/redis
$: docker-compose up -d
```
##### 3. run project successfully. view all api list by swagger. 
```
http://127.0.0.1/swagger-ui/index.html#/
```