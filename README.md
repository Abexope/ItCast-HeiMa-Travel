# ItCast-HeiMa-Travel
Java Web 实践项目：黑马旅游网
项目课程链接：https://www.bilibili.com/video/BV1CE411E7h4
完整课程连接：https://www.bilibili.com/video/BV1uJ411k7wy

## 技术选型

采用经典三层架构：**web层 - service层 - dao层** 

### 1. web层

- Servlet：前端控制程序
- HTML：前端页面（前后端分离架构，不适用JSP）
- Filter：过滤器
- BeanUtils：数据封装
- Jackson：json 序列化工具，前后端数据通用传输格式

### 2. service层

- JavaMail：Java 邮件工具
- Redis：nosql 内存数据库
- Jedis：Java 的 Jedis 客户端

### 3. dao层

- mysql：关系数据库
- druid：数据库连接池
- Spring Jdbc Template：数据库连接工具

## 数据库设计

```mysql
-- 船建数据库
CREATE DATABASE travel;
-- 使用数据库
USE travel;
```
其它建表 sql 语句详见：*连接待上传GitHub后指定*

![数据库设计](./img/数据库设计.png)



## 功能实现

### 注册

- 功能描述：注册表单如下，前台填充信息并点击`注册`按钮后实现账户注册。

<img src="D:\iJava\ItCast-HeiMa-Travel\img\注册表单.png" alt="注册表单" style="zoom: 50%;" />

- 逻辑分析：

![注册功能分析](D:\iJava\ItCast-HeiMa-Travel\img\注册功能分析.bmp)

- 代码实现：*连接待上传GitHub后指定*

1. 前台效果
   1. 表单校验：前端接收外部输入，在浏览器端利用正则表达式实现校验并以CSS样式提示用户输入是否符合相关字段的条件。
   2. 异步（AJAX）提交表单：在此使用异步提交表单是为了获取服务器响应的数据。因为我们前台使用html作为视图层，不能够直接从servlet相关的域对象获取值，只能通过 AJAX 获取响应数据。

### 激活

为什么要进行邮件激活？为了保证用户填写的邮箱是正确的。将来可以推广一些宣传信息，到用户邮箱中。

1. 发送邮件：在service层完成用户注册后，后端向用户提交的邮箱中发送激活邮件。
2. 用户点击邮件激活：构造 User Bean 对象时会同时设置`激活码`和`激活状态`属性，向用户发送的激活邮件中会带有激活码和激活连接。用户操作后，根据提交的激活码判断用户是否合法，合法则跳转至新的页面提示激活成功

![邮件激活逻辑](D:\iJava\ItCast-HeiMa-Travel\img\邮件激活逻辑.bmp)

### 登陆



### 退出







