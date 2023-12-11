# elm_springboot

## 项目简介

elm_springboot是一个基于Spring Boot的Web应用程序，设计用于处理业务订单、购物车、用户管理等功能。项目利用了多种技术，如JWT用于身份验证、MyBatis Plus用于ORM、以及Spring Boot用于Web服务。
[前端项目](https://github.com/davidwushi1145/elm_vue)
## 技术栈

- **Spring Boot**: 项目使用 Spring Boot 2.7.5，它简化了基于 Spring 的应用程序的配置和部署。
- **Java**: 使用 Java 8 进行开发。
- **Maven**: 作为项目构建和依赖管理工具。
- **MySQL**: 使用 MySQL 数据库连接器。
- **Lombok**: 简化实体类的编写。
- **JWT**: 用于实现基于 Token 的身份验证。
- **MyBatis Plus**: 作为 ORM 框架简化数据库操作。
- **Hibernate Validator**: 提供输入验证功能。
- **JavaFX**: 用于开发富客户端应用程序。
- **FastJSON**: 用于处理 JSON 数据。

## 主要组件

### 1. 业务逻辑层 (Service Layer)

- **BusinessServiceImpl**: 处理与业务相关的逻辑。
- **CartServiceImpl**: 购物车功能的实现。
- **DeliveryAddressServiceImpl**: 管理送货地址的逻辑。
- **FoodServiceImpl**: 食品相关功能的实现。
- **OrdersServiceImpl**: 订单处理的逻辑。
- **PointServiceImpl**: 积分系统的实现。
- **UserServiceImpl**: 用户管理逻辑。
- **VirtualWalletServiceImpl**: 虚拟钱包功能的实现。

### 2. 控制器层 (Controller Layer)

- **BusinessController**: 业务相关的Web接口。
- **CartController**: 购物车相关的Web接口。
- **DeliveryAddressController**: 地址管理相关的Web接口。
- **FoodController**: 食品相关的Web接口。
- **OrdersController**: 订单管理的Web接口。
- **UserController**: 用户相关的Web接口。
- **VirtualWalletController**: 虚拟钱包的Web接口。

### 3. 数据访问层 (Data Access Layer)

- **Mapper接口**: 如 `BusinessMapper`, `CartMapper` 等，用于与数据库交互。

### 4. 数据传输对象 (DTOs)

- **VO类**: 如 `BusinessVo`, `CartVo` 等，用于在不同层之间传递数据。

### 5. 实体类 (Entities)

- **实体类**: 如 `Business`, `Cart` 答等，映射数据库表。

### 6. 异常处理

- **BusinessException**: 业务异常类。
- **ConditionException**: 条件异常处理。

### 7. 工具类 (Utilities)

- **JWTUtil**: JWT令牌处理。
- **MD5Util**: MD5加密工具。
- **DateUtil**: 日期处理工具。

## 如何构建

确保您的开发环境中已安装 Java 8 和 Maven。

1. 克隆仓库：

   ```bash
   git clone
   
   cd elm_springboot
   ```

2. 使用 Maven 构建项目：

   ```bash
   mvn clean install
   ```

3. 运行应用程序：

   ```bash
   mvn spring-boot:run
   ```

## 贡献

欢迎提交 pull request 以改进项目。请确保您的代码遵循项目的编码规范。
