# 桑榆晚情社区居家养老服务平台系统

基于Spring Boot开发的社区居家养老服务平台系统，为老年人提供全面的档案管理和健康服务。

## 功能特性

- 用户登录认证系统
- 老人档案管理（增删改查）
- 健康评估记录
- 环境评估记录
- 护理记录管理
- 响应式Web界面

## 技术栈

- 后端：Spring Boot 3.2.0
- 数据库：MySQL 8.0
- 前端：HTML5, CSS3, JavaScript
- 持久层：Spring Data JPA
- 构建工具：Maven

## 快速开始

### 环境要求

- JDK 21
- Maven 3.x
- MySQL 8.0

### 数据库配置

1. 创建MySQL数据库：
```sql
CREATE DATABASE IF NOT EXISTS elderlycare CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 更新配置文件 `src/main/resources/application.properties` 中的数据库连接信息

### 运行项目

```bash
mvn spring-boot:run
```

访问地址：http://localhost:8080

默认管理员账号：
- 用户名：admin
- 密码：111111

## 项目结构

```
src/
├── main/
│   ├── java/com/elderlycare/
│   │   ├── config/          # 配置类
│   │   ├── controller/      # 控制器
│   │   ├── entity/          # 实体类
│   │   ├── repository/      # 数据访问层
│   │   └── service/         # 业务逻辑层
│   └── resources/
│       ├── static/          # 静态资源
│       └── application.properties  # 应用配置
```

## 开发说明

项目已配置自动加载示例数据，首次启动时会自动创建：
- 默认管理员用户
- 8条老人档案示例数据

## 许可证

MIT License