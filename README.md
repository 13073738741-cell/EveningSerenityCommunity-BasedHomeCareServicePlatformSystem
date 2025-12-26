# 桑榆晚情社区居家养老服务平台系统

基于 Spring Boot + Vue 3 开发的社区居家养老服务平台系统，为老年人提供全面的档案管理和健康服务。

## 功能特性

### 核心功能
- **用户登录认证系统** - 支持管理员登录和权限管理
- **老人档案管理** - 完整的老人信息增删改查功能
  - 老人基本信息管理（姓名、性别、出生日期、身份证、联系方式等）
  - 老人健康状况显示
  - 老人档案搜索和分页查询
- **健康能力评估管理** - 记录和管理老人的健康评估信息
  - ADL（日常生活活动能力）评估
  - IADL（工具性日常生活活动能力）评估
  - 慢性疾病和用药情况记录
  - 心理健康和营养状况评估
  - 跌倒风险评估
- **家庭环境评估管理** - 评估老人的居住环境安全
  - 住房类型和楼层信息
  - 安全评分
  - 卫生间、厨房安全评估
  - 采光、通风、无障碍设施评估
  - 安全隐患识别和改进建议
- **关爱档案管理** - 记录老人的关爱服务记录
  - 关爱类型（电话问候、上门探访、节日慰问、陪同就医、应急探访）
  - 关爱内容和效果评估
  - 关爱人员信息
  - 下次关爱计划

### 界面特性
- 响应式Web界面设计
- 现代化的UI组件（基于Element Plus）
- 直观的数据展示和操作界面
- 完善的表单验证和提示

## 技术栈

### 后端技术
- **框架**: Spring Boot 3.2.0
- **数据库**: MySQL 8.0
- **持久层**: Spring Data JPA
- **构建工具**: Maven

### 前端技术
- **框架**: Vue 3.4.0
- **路由**: Vue Router 4.2.5
- **UI组件库**: Element Plus 2.4.4
- **HTTP客户端**: Axios 1.6.2
- **构建工具**: Vite 5.0.0

## 快速开始

### 环境要求

- JDK 21
- Maven 3.x
- MySQL 8.0
- Node.js 18+（用于前端开发）

### 数据库配置

#### 方法一：自动初始化（推荐）

1. 创建MySQL数据库：
```sql
CREATE DATABASE IF NOT EXISTS elderlycare CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 更新配置文件 `src/main/resources/application.properties` 中的数据库连接信息：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/elderlycare?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=your_password
```

3. 启动应用，系统会自动执行以下操作：
   - 创建所有必要的表（users, elderly_info, health_assessment, environment_assessment, care_record）
   - 插入默认管理员用户（admin/111111）
   - 插入8条老人档案示例数据

#### 方法二：手动执行SQL脚本

如果需要手动初始化数据库，可以使用项目根目录下的SQL文件：

```bash
# 初始化数据库和表结构
mysql -u root -p < init_database.sql

# 添加ADL分项字段（如需要）
mysql -u root -p elderlycare < add_adl_fields.sql

# 添加关爱等级字段（如需要）
mysql -u root -p elderlycare < add_care_level_field.sql
```

### 后端运行

```bash
# 在项目根目录下执行
mvn spring-boot:run
```

后端服务地址：http://localhost:8080

### 前端运行

```bash
# 进入前端目录
cd frontend

# 安装依赖（首次运行）
npm install

# 启动开发服务器
npm run dev
```

前端访问地址：http://localhost:3000

### 默认账号

- **用户名**: admin
- **密码**: 111111

## 项目结构

```
src/
├── main/
│   ├── java/com/elderlycare/
│   │   ├── config/          # 配置类（CORS、安全配置等）
│   │   ├── controller/      # REST API控制器
│   │   ├── entity/          # JPA实体类
│   │   ├── repository/      # 数据访问层
│   │   └── service/         # 业务逻辑层
│   └── resources/
│       ├── static/          # 静态资源
│       └── application.properties  # 应用配置

frontend/
├── src/
│   ├── api/                # API接口封装
│   ├── router/             # Vue Router路由配置
│   ├── styles/             # 全局样式
│   ├── utils/              # 工具函数
│   ├── views/              # 页面组件
│   ├── App.vue             # 根组件
│   └── main.js             # 应用入口
├── package.json           # 前端依赖配置
└── vite.config.js         # Vite构建配置
```

## 主要页面

- **登录页** (`/login`) - 用户登录认证
- **老人档案管理** (`/elderly-manage`) - 老人信息管理
- **健康评估管理** (`/health-assessment-manage`) - 健康评估记录管理
- **环境评估管理** (`/environment-assessment-manage`) - 环境评估记录管理
- **关爱档案管理** (`/care-record-manage`) - 关爱服务记录管理
- **健康评估表单** (`/health-assessment/:elderlyId`) - 为老人创建健康评估
- **环境评估表单** (`/environment-assessment/:elderlyId`) - 为老人创建环境评估
- **关爱档案详情** (`/care-record/:elderlyId`) - 查看老人关爱档案

## 开发说明

### 数据库初始化

项目已配置 `spring.jpa.hibernate.ddl-auto=update`，首次启动时会自动创建表结构。

生产环境建议将 `spring.sql.init.mode` 设置为 `never` 以避免重复执行初始化脚本。

### 示例数据

首次启动时会自动创建：
- 1个默认管理员用户（admin/111111）
- 8条老人档案示例数据

### API接口

后端提供RESTful API接口，所有接口以 `/api` 开头：
- `POST /api/auth/login` - 用户登录
- `GET /api/elderly` - 获取老人列表
- `POST /api/elderly` - 创建老人档案
- `PUT /api/elderly/{id}` - 更新老人档案
- `DELETE /api/elderly/{id}` - 删除老人档案
- `GET /api/health-assessment` - 获取健康评估列表
- `POST /api/health-assessment` - 创建健康评估
- `GET /api/environment-assessment` - 获取环境评估列表
- `POST /api/environment-assessment` - 创建环境评估
- `GET /api/care-record` - 获取关爱档案列表
- `POST /api/care-record` - 创建关爱档案

## 生产部署

### 后端打包

```bash
mvn clean package
java -jar target/your-app.jar
```

### 前端打包

```bash
cd frontend
npm run build
```

打包后的文件在 `frontend/dist` 目录，可部署到任何静态文件服务器。

## 许可证

MIT License

## 联系方式

如有问题或建议，欢迎提交Issue或Pull Request。
