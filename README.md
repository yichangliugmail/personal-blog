# 简介
项目基于Springboot+Vue搭建的技术交流论坛，也可也作为个人博客项目，项目持续更新迭代，不断融入新技术，欢迎各位拉取项目共同开发。
<br>本项目为作者参照各位大佬的开源项目，独立开发的一个论坛系统，本项目可作为Java学习项目，代码内包含大量注释，可供各位参考。
# 项目亮点

- 前台界面参考 Hexo 的 Shoka 和 Butterfly 设计，页面美观，响应式布局
- 后台管理基于若依二次开发，含有侧边栏，历史标签，面包屑等
- 采用 RABC 权限模型，使用 Sa-Token 进行权限管理
- 支持动态权限修改、动态菜单和路由
- 说说、开源链接、相册、留言弹幕墙
- 支持代码高亮、图片预览、黑夜模式、点赞、取消点赞等功能
- 发布评论、回复评论、表情包（待完善）
- 发送 HTML 邮件评论回复提醒，内容详细（计划中）
- 接入第三方登录，减少注册成本（计划中）
- 文章编辑使用 Markdown 编辑器
- 含有最新评论、文章目录、文章推荐和文章置顶功能
- 实现日志管理、定时任务管理、在线用户和下线用户（待完善）
- 代码支持多种搜索模式 MYSQL 或 Elasticsearch（待完善） 
- 支持多种文件上传模式OSS、COS、本地（待完善）
- 采用 Restful 风格的 API，注释完善，代码遵循阿里巴巴开发规范，有利于开发者学习

# 交流

项目已部署至阿里云服务器：http://47.113.219.142
项目文档：[博客API文档](http://localhost:5173/api/doc.html#/home)

## 技术介绍

**前端：** Vue3 + Pinia + Vue Router + TypeScript + Axios + Element Plus + Naive UI + Echarts + Swiper

**后端：** SpringBoot + Mysql + Redis + Quartz + Thymeleaf + Nginx + Docker + Sa-Token + Swagger2 + MyBatisPlus + RabbitMQ + Canal

**其他：** 

## 运行环境

**服务器：** 阿里云 2 核 2G CentOS7.9

**对象存储：** 腾讯云 COS

**最低配置：** 2 核 2G 服务器（关闭 ElasticSearch）

## 开发环境

| 开发工具              | 说明               |
| --------------------- | ------------------ |
| IDEA                  | Java 开发工具  |
| VSCode                | Vue 开发工具,也可使使用IDEA   |
| Navicat,DataGrip      | 数据库远程连接工具，任意一款 |
| Redis Desktop Manager | Redis 连接工具，任意一款 |
| Xshell                | Linux 远程连接工具，任意一款 |

| 开发环境       | 版本    | 说明|
| ------------- | ------ |------|
| OpenJDK       | 8,11   |必要，java环境，最好是11版本|
| MySQL         | 8.0.27 |必要，版本8+即可|
| Redis         | 6.2.6  |必要，版本6+即可|
| ElasticSearch | 7.17.3 |非必要，用于检索和存储大文本|
| RabbitMQ      | 3.9.11 |非必要，用于发送邮件，如登录验证码|
| NodeJs        | 16.20.2| 必要，前端包管理工具，16或18版本|

| 技术栈       | 介绍    |
| ------------| ------ |
| SpringBoot  | Java开发框架    |
| Quartz      | 任务调度框架，自动化运维平台中部分服务用的较为落后的定时任务调度，如断采服务，新项目通常使用XXL-JOB作为任务调度框架，如ITIL，智慧大脑使用的XXL-JOB调度框架|
| redis | redis是内存级缓存，Spring cache是代码级缓存|
| Sa-Token | 权限认证框架，智慧大脑用的sa-Token，自动化平台的不清楚|
| Swagger | 文档管理，如itil的sti服务整合了swagger，访问地址：133.0.184.23:8080/snc-sti/doc.html#/home |
| MyBatisPlus | MyBatis升级版本，作为mysql框架，简化数据库操作 |
| RabbitMQ | 消息中间件，安全，有消息确认机制，支持事务，但不支持负载均衡，与kafka相比吞吐量不足，消息处理效率较低，两者应用场景不同 |
| COS | 对象存储，本服务用的腾讯云对象存储，ITIL平台用的是华为云对象存储，如定级定级报告文件生成后存储至云端，需要时从云端下载 |
| markdown | 在线编辑工具，编辑博客文章，存储文件格式为XX.md，自动化平台运维配置，添加操作中的在线编辑脚本就是使用markdown实现 |
