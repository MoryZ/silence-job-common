# Silence Job Common

[![Java Version](https://img.shields.io/badge/Java-21-blue)](https://www.oracle.com/java/)
[![Maven Central](https://img.shields.io/badge/Maven-3.8.9+-green)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

> Silence Job Common 是一个为 Silence Job 框架提供公共功能和工具的 Maven 多模块项目。它包含通用的常量、枚举、数据模型、工具类和日志处理功能。

## 📦 项目概述

`silence-job-common` 是 Silence Job 框架的基础库，提供以下核心功能：

- **枚举管理** (`silence-job-common-enum`) - 系统中使用的所有枚举类型
- **常量定义** (`silence-job-common-constant`) - 统一的常量管理
- **数据模型** (`silence-job-common-model`) - 通用的数据模型和 DTO
- **工具类** (`silence-job-common-util`) - 文件、网络、数据处理等常用工具
- **日志功能** (`silence-job-common-log`) - 统一的日志管理和记录
- **核心功能** (`silence-job-common-core`) - gRPC 支持、窗口计算、核心算法
- **API 定义** (`silence-job-common-client-api`, `silence-job-common-server-api`) - 客户端和服务端 API

## 🛠️ 技术栈

- **Java 21** - 使用最新的 Java 特性
- **Spring Boot 3.2.0** - 现代化的 Spring 框架
- **Maven 3.8.9+** - 项目构建工具
- **gRPC 1.60.0** - 高性能 RPC 框架
- **Protocol Buffers** - 数据序列化格式
- **FastJSON2** - 高效的 JSON 处理库
- **Hutool** - 实用工具库集合

## 📂 项目结构

```
silence-job-common/
├── silence-job-common-enum/           # 枚举定义
├── silence-job-common-constant/       # 常量定义
├── silence-job-common-model/          # 数据模型
├── silence-job-common-util/           # 工具类
├── silence-job-common-log/            # 日志功能
├── silence-job-common-core/           # 核心功能（包含 gRPC）
├── silence-job-common-client-api/     # 客户端 API
├── silence-job-common-server-api/     # 服务端 API
├── pom.xml                             # 主 POM 配置
└── MAVEN_BEST_PRACTICES.md            # Maven 最佳实践指南
```

## 🚀 快速开始

### 前置要求

- Java 21+
- Maven 3.8.9+
- platform-parent 项目（作为 parent POM）

### 编译和安装

```bash
# 克隆项目
git clone <repository-url>
cd silence-job-common

# 构建所有模块
mvn clean install

# 跳过测试进行快速构建
mvn clean install -DskipTests

# 仅编译
mvn clean compile
```

### 依赖引入

在你的项目中引入此库：

```xml
<dependency>
    <groupId>com.old.silence</groupId>
    <artifactId>silence-job-common-core</artifactId>
    <version>1.8.0-SNAPSHOT</version>
</dependency>
```

或者根据需要选择特定模块：

```xml
<!-- 仅需要枚举和常量 -->
<dependency>
    <groupId>com.old.silence</groupId>
    <artifactId>silence-job-common-enum</artifactId>
    <version>1.8.0-SNAPSHOT</version>
</dependency>

<!-- 仅需要工具类 -->
<dependency>
    <groupId>com.old.silence</groupId>
    <artifactId>silence-job-common-util</artifactId>
    <version>1.8.0-SNAPSHOT</version>
</dependency>
```

## 📖 模块说明

### silence-job-common-enum
包含系统中使用的所有枚举类型，如：
- 任务类型、任务状态、执行器类型等
- 重试策略、通知场景等

**特点**：
- 实现 `EnumValue` 和 `DescribedEnumValue` 接口
- 提供统一的枚举值获取方式

### silence-job-common-constant
定义系统中的常量，包括：
- 系统常量、AT 标签、默认配置等
- 业务相关的静态常量

### silence-job-common-model
通用的数据模型和 DTO，用于模块间数据传递：
- 上下文信息模型
- 批处理、任务执行等模型
- Spring 框架集成模型

### silence-job-common-util
通用工具类库：
- **SilenceJobFileUtil** - 文件下载和处理
- **DingDingUtils** - 钉钉消息发送（使用 Java 11+ HttpClient）
- 其他业务工具方法

### silence-job-common-log
统一的日志管理：
- 支持 Log4j2 和 Logback
- 提供日志工厂和中心化日志处理
- Spring Boot 自动配置集成

### silence-job-common-core
核心功能模块：
- **gRPC 支持** - Protocol Buffers 定义和服务实现
- **LeapArray** - 高性能的循环数组窗口计算
- **表达式引擎** - 支持 Aviator 和 QLExpress
- 邮件、网络、数据验证等核心工具

### silence-job-common-client-api & silence-job-common-server-api
API 定义和契约：
- 客户端和服务端的 gRPC 接口定义
- API 请求响应模型

## 🔧 构建和开发

### 查看依赖树

```bash
mvn dependency:tree
```

### 检查过时依赖

```bash
mvn versions:display-dependency-updates
```

### 检查代码质量

```bash
mvn clean install
```

### 仅编译特定模块

```bash
mvn clean install -pl silence-job-common-core
```

## 📋 最佳实践

详见 [MAVEN_BEST_PRACTICES.md](docs/MAVEN_BEST_PRACTICES.md)

本项目遵循的关键实践：

✅ **依赖管理**
- 使用 `dependencyManagement` 统一管理所有依赖版本
- 通过 BOM (Bill of Materials) 导入 Spring Boot 和 gRPC 依赖
- 所有依赖都有明确的版本号定义

✅ **版本管理**
- 使用固定版本号 `1.8.0-SNAPSHOT` 而非 CI-Friendly Versions
- 继承 `platform-parent` 进行统一的依赖管理

✅ **代码现代化**
- 使用 Java 21 最新特性
- 使用 `java.net.http.HttpClient` 替代过时的 HTTP 方案
- 使用 `Thread.currentThread().threadId()` 替代过时的 `getId()`

✅ **构建规范**
- 配置 Maven 编译器插件版本和 Java 源/目标版本
- 移除不必要的插件（如 flatten-maven-plugin）

## 🐛 常见问题

### Q: 如何解决 Maven 依赖冲突？
A: 运行 `mvn dependency:tree` 查看依赖树，在 `dependencyManagement` 中显式声明版本。

### Q: 为什么编译失败说找不到 platform-core？
A: 确保 `platform-parent` 已安装到本地仓库，运行 `mvn install` 安装 platform-parent。

### Q: 如何只编译不运行测试？
A: 使用 `mvn clean install -DskipTests` 跳过测试。

### Q: gRPC 生成的代码在哪里？
A: 生成的代码位于 `target/generated-sources/` 目录下。

## 📝 License

MIT License - 详见 [LICENSE](../LICENSE) 文件

## 👥 贡献指南

欢迎提交 Issue 和 Pull Request！

## 📞 联系方式

- 项目维护者：[维护者名称]
- 问题反馈：GitHub Issues
- 讨论交流：GitHub Discussions

---

**最后更新**：2026-02-04
