## 本地运行

## 前置依赖

本地运行前请先安装 [Docker](https://www.docker.com/products/docker-desktop/)。

## 启动数据库

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