## 需求分析
Build a RESTful API that can get/create/update/delete user data from a persistence database  
C: 一般为注册用户  
R: 看rest接口的需要，可以是根据用户名获取用户其他信息，如出生年月，昵称，等级，etc  
U: 更新用户信息，包括改密码，改昵称等  
D: 用户注销(一般来说实际上的应用不会用这个操作而是用删除标志替代)  

目前也不知道这个设计的用户系统针对的是什么，那么就假设一个场景：论坛  

预期访问量约为100QPS以下（一个一般的小论坛访问量也不大，现在论坛也式微了，除非是twitter这种SNS）

语言：java  
HTTP服务选择用SpringBoot  
persistance用Spring data JPA  
测试框架选择JUnit4  
如果需要mock就加个Mockito  
CI选择Github Actions  
部署选择阿里云/AWS
DB选择：用RDBMS就行，当然文档存储也不是不可以（以用户名为key，但这样就不允许重名)。  
目前选择最熟悉的RDBMS(本地用sqlite，云上用Mysql/RDS，视厂商提供而定)。  
文档/API设计选择用swagger，这样可以把文档直接写在代码里面，用annotation表示，可以生成为html代码。   

Entity设计:  
```
{
    "id": "xxx",                  // user ID(self-increment/random generated)
    "name": "test",               // user name
    "password": "",               // user password(hashed and salt-added)
    "dob": "",                    // date of birth
    "address": "",                // user address
    "description": "",            // user description
    "createdAt": ""               // user created date
}
```