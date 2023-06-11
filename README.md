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
项目文档：[博客API文档](http://47.113.219.142:5173/api/doc.html#/home)

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
| IDEA                  | Java 开发工具 IDE  |
| VSCode                | Vue 开发工具 IDE   |
| Navicat               | MySQL 远程连接工具 |
| Redis Desktop Manager | Redis 远程连接工具 |
| Xshell                | Linux 远程连接工具 |
| Xftp                  | Linux 文件上传工具 |

| 开发环境 | 版本   |
| -------- | ------ |
| OpenJDK  | 11     |
| MySQL    | 8.0.27 |
| Redis    | 6.2.6  |
|          |        |
| RabbitMQ | 3.9.11 |
