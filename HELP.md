# Wiredcraft Back-end Test Project

This service provides user management feature.

## API

### User management

#### Query user

```
GET /api/v1/users/{id}
```
**Path Variables**
- id — String, user ID, **required**.

#### Create user

```
POST /api/v1/users/
Content-Type: application/json

{
    "name":"jerrychin",
    "dob":"2022-01-01",
    "address": "your address",
    "description": "your description"
}
```

**Request Payload**
- name — String, username, **required**, max 64 chars.
- dob — String, date of birth in `yyyy-MM-dd` format, optional,
- address — String, user address, optional, max 255 chars.
- description — String, user address, optional, max 255 chars.

```
{
    "id": "3a52917893c14b18b2d7645fd7815886",
    "name":"jerrychin",
    "dob":"2022-01-01",
    "address": "your address",
    "description": "your description",
    "createdAt": "2022-05-07 0:0:0"
}
```

**Response Payload**
- id - unique user ID.
- name — see above.
- dob — see above.
- address — see above.
- description — see above.
- createdAt — String, user created time in `yyyy-MM-dd hh:mm:ss` format, **required**.

#### Update user

```
POST /api/v1/users/{id}
Content-Type: application/json

{
    "name":"jerrychin",
    "dob":"2022-01-01",
    "address": "your address",
    "description": "your description"
}
```
**Path Variables**
- id — String, user ID, **required**.

**Request Payload**
Same as above.

**Response Payload**
Same as above.

#### Delete user

```
DELETE /api/v1/users/{id}
```
**Path Variables**
- id — String, user ID, **required**.

## 本地运行

## 0. 前置依赖

本地运行前请先安装 [Docker](https://www.docker.com/products/docker-desktop/)。

## 1. 启动数据库

本服务依赖 MySQL 数据库，用于存储用户数据。

简单起见，本服务使用 Docker 运行 MySQL 数据库，启动命令为：

```shell
docker run --name mysql-server -p3306:3306 -e MYSQL_ROOT_PASSWORD='?2bW$PlGaY@7JKU#xY' -e MYSQL_DATABASE=user_service -e MYSQL_USER=user_service -e MYSQL_PASSWORD='^Mdh2jsk6sh#k2$' -d mysql:8
```

上述命令后台启动了一个 8.0 版本的 MySQL 数据库实例，实例信息如下：

| 属性    | 值         |
|-------|-----------|
| 数据库版本 | MySQL 8.0 |
| 主机地址  | 127.0.0.1 |
| 端口    | 3306      |
| 数据库名称   | user_service       |
| 用户名     | user_service       |
| 用户密码    | ^Mdh2jsk6sh#k2$    |
| root 密码 | ?2bW$PlGaY@7JKU#xY |

通过下述指令可以进入容器，以便了解 MySQL 实例情况：

```shell
docker exec -it mysql-server /bin/bash
```

如果启动异常则可以通过以下指令获取 MySQL 启动日志：

```shell
docker logs mysql-server
```

## 3. 启动应用

使用 Idea 或者 Maven 方式启动应用

## 4. 测试接口是否符合预期

**查询指定用户的信息**
```shell
curl http://localhost/api/v1/users/{id}
```

**创建用户信息**
```shell
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"jerrychin", "dob":"2022-01-01", "address": "address", "description": "description" }' \
  http://localhost/api/v1/users
```

**更新用户信息**
```shell
curl --header "Content-Type: application/json" \
  --request PUT \
  --data '{"name":"jerrychi2", "dob":"2022-01-02", "address": "new address", "description": "new description" }' \
  http://localhost/api/v1/users/{id}
```

**删除用户信息**
```shell
curl --request DELETE \
  http://localhost/api/v1/users/{id}
```