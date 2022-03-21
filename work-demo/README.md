* 使用java8
* 使用lombok插件
* 缓存使用ehcache
* 刷入mysql.sql
* 启动工程 run com.zhangyongxin.demo.Application
* api文档：`http://127.0.0.1:8080/demo/swagger-ui.html`
* helloWorld : `http://127.0.0.1:8080/demo/test/hello`
* 所有数据接口均返回Result，如：

    ```json
      {
        "code": 200,
        "message": "SUCCESS",
        "data": "Hello World!"
      }
    ```

* 分页使用PageHelper
* 权限控制使用spring security
* dao代码生成：Run -> Edit Configurations -> + Maven commend line ```mybatis-generator:generate -e```
