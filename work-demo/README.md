* 使用java8
* 使用lombok插件
* 使用ehcache
* 刷入mysql.sql
* api文档：`http://127.0.0.1:8888/snail/swagger-ui.html`
* helloWorld : `http://127.0.0.1:8888/snail/hello/admin`
* 所有数据接口均返回Result，如：

    ```json
      {
        "code": 200,
        "message": "SUCCESS",
        "data": "admin"
      }
    ```

* 分页使用PageHelper
* 权限控制使用spring security，开发环境下不做登录校验
* 代码生成：Run -> Edit Configurations -> + Maven commend line ```mybatis-generator:generate -e```
