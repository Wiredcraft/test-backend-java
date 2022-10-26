# Wiredcraft Back-end Developer Test

## Requirements:
- JDK >= 8
- Mysql >=5.7, 8.0 is perfect
- Maven

## Environment Set Up
- create two environment variables

| variable_name | desc | samle|
|---------------|------|------|
| DB_USERNAME   | user name for login db | root
| DB_PASSWORD   | password for login db | 123456

- Install/Run Mysql
- Run DDL.sql

By default, this sql has create a user called 'admin', which password is also 'admin'
```
INSERT INTO
t_user(
  name,
  dob,
  address,
  description,
  created_at,
  updated_at,
  deleted,
  password
)
VALUES
(
  'admin',
  NULL,
  NULL,
  'admin user',
  '2022-10-25 15:05:25',
  '2022-10-25 15:05:25',
  0,
  X'4341443231454343373142373633334341334246453031383832324632344331343345464641313530354630333046434346304533363935'
);
```
## API Documentation
After start the application, visit http://localhost:8080/swagger-ui/index.html to get the API documentation

## Basic User Info
The basic user info is stored in the the table t_user
| column | desc |
|---------------|------|
| name   | user name    |
| dob    | date of birth |
| address | user address |
| description | user description |
| created_at  | record create time |
| updated_at  | record update time |
| deleted | logic delete flag, where deleted flag is 1|
| password | user password, format: Salt + MD5(password_str) |

## Auth
- This application use Http Bearer Authentication
- Visit /auth/authenticate to get the JWT token
- When visit other APIs, set token to the Authorization header. eg. Authorization: Bearer 123123
- By default, the token expires after 24 hours. You can set the expiration in config security.jwt.expiration

## Geographic Support
- User's geographic information is stored in t_user_coordinates

| column | desc |
|---------------|------|
| user_id   | id in t_user    |
| last_coord| user address position, using mysql point type |
| log_time  | record time |
- Using Mysql geographic data type

## Follower/Following 
- The follower realtion is stored in t_user_follower

| column | desc |
|---------------|------|
| user_id   | id in t_user    |
| follower_id | id in t_user, the user who follow the user_id record |
| created_at | create time |
| updated_at | update time |
| deleted | logic delete flag |
