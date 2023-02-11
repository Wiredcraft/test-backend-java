# 更新日志

### 2023-2-10
1. 初始化项目，采用Srpingboot+H2来实现简单的CRUD，API满足restful规范
2. 添加UserService单元测试
3. 封装统一返回参数JsonResult

由于是简单的demo，所以在代码可维护的前提下，没有直接采用maven分模块的形式来分层，而是采用包的形式

启动入口是UserApplication的main方法

### 2023-2-11
1. 添加CHANGE_LOG.md
2. 添加TokenService，说明终端用户的访问控制
3. 添加logback-spring.xml
4. 添加FriendsController描述如何维护关注和取消关注API等
5. 在FriendsController添加如何获取附近的朋友API