# Maven 多模块项目最佳实践指南

## 一、依赖管理原则

### 1.1 版本管理策略
- ✅ **推荐**：使用 `dependencyManagement` 统一管理所有依赖版本
- ✅ **推荐**：直接使用固定版本号（如 `1.8.0-SNAPSHOT`），而非 `${revision}`
- ❌ **避免**：子模块依赖中出现无版本号的依赖声明
- ⚠️ **注意**：如果使用 `${revision}` 进行 CI/CD，必须配合 `flatten-maven-plugin` 和正确的版本配置

### 1.2 依赖版本来源优先级
```
1. 子模块自己的 pom.xml (override)
2. 项目自身的 dependencyManagement
3. Parent POM 的 dependencyManagement
4. 导入的 BOM (Bill of Materials)
```

### 1.3 BOM 导入最佳实践
```xml
<dependencyManagement>
    <dependencies>
        <!-- 首先导入 Spring Boot BOM -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${spring-boot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        
        <!-- 其次导入其他 BOM -->
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-bom</artifactId>
            <version>${grpc.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

---

## 二、多模块项目结构规范

### 2.1 Parent POM 必须包含
```
✅ <packaging>pom</packaging>
✅ <modules>...</modules>
✅ <properties> 定义所有版本号
✅ <dependencyManagement>
✅ <pluginManagement> (编译器、资源处理等)
✅ <build> 中的通用插件配置
```

### 2.2 子模块 POM 应该
```
✅ 明确指定 parent（带 relativePath）
✅ 只声明必需的依赖（无需重复版本号）
✅ 继承 parent 的依赖版本
✅ 不重复定义 parent 中已有的属性
```

### 2.3 版本号管理清单
```markdown
- [ ] Parent POM 中定义了 <revision> 或使用固定版本
- [ ] 所有子模块都使用统一的版本号
- [ ] dependencyManagement 中的所有依赖都有版本号
- [ ] 不存在版本号为空的依赖声明
- [ ] 所有 BOM 都通过 import scope 导入
```

---

## 三、编译器配置规范

### 3.1 避免的做法
```xml
❌ <source>${java.version}</source>  <!-- 包含完整版本号如 21.0.8 -->
❌ <target>${java.version}</target>
```

### 3.2 正确的做法
```xml
✅ <maven.compiler.source>21</maven.compiler.source>
✅ <maven.compiler.target>21</maven.compiler.target>

<!-- 在 pluginManagement 中统一配置 -->
<pluginManagement>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <source>${maven.compiler.source}</source>
                <target>${maven.compiler.target}</target>
                <encoding>UTF-8</encoding>
            </configuration>
        </plugin>
    </plugins>
</pluginManagement>
```

---

## 四、代码现代化检查清单

### 4.1 过时 API 替换
| 过时代码 | 现代替代 | 最小 Java 版本 |
|---------|---------|----------------|
| `new URL(str)` | `URI.create(str).toURL()` | 7+ |
| `Thread.currentThread().getId()` | `Thread.currentThread().threadId()` | 20+ |
| `new HttpRequest()/new HttpResponse()` | `java.net.http.HttpClient` | 11+ |
| Hutool HttpRequest | 标准库 HttpClient 或 Spring RestTemplate | 11+ |

### 4.2 资源管理最佳实践
```java
❌ 错误做法
HttpClient client = HttpClient.newHttpClient();
HttpResponse response = client.send(request, ...);

✅ 正确做法
private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
// 在静态初始化中创建，复用连接池
```

---

## 五、构建检查清单

### 5.1 构建前检查
```bash
# 1. 验证依赖树
mvn dependency:tree

# 2. 检查过时依赖
mvn versions:display-dependency-updates

# 3. 检查 CVE
mvn org.owasp:dependency-check-maven:check

# 4. 检查代码质量
mvn clean install -DskipTests
```

### 5.2 构建过程验证
- [ ] 无 FATAL 错误
- [ ] 无版本缺失的依赖
- [ ] 无 Parent POM 无法解析
- [ ] 所有模块都成功编译
- [ ] 没有过时 API 警告

---

## 六、特殊场景处理

### 6.1 Parent POM 继承链
```
项目 Parent (silence-job-common)
    ↓
Platform Parent (platform-parent) 
    ↓
Spring Boot Parent (可选)
```

**规则**：只能有一个 parent，其他依赖通过 BOM import

### 6.2 Flatten Maven Plugin 使用场景
**何时需要**：
- 使用 `${revision}` 进行版本管理
- 需要在 CI/CD 中动态设置版本

**何时不需要**：
- 使用固定版本号
- 依赖 parent POM 的版本管理

---

## 七、项目初始化检查清单

创建新项目时：
- [ ] 确定 Parent POM（platform-parent 还是直接创建）
- [ ] 定义版本策略（固定 vs `${revision}`）
- [ ] 创建 dependencyManagement section
- [ ] 配置 pluginManagement section
- [ ] 检查所有子模块的 parent 引用
- [ ] 验证第一次构建 `mvn clean install`

---

## 八、IDE 配置建议

### IntelliJ IDEA
1. 设置 → Maven → 启用"Skip Tests"
2. 右键项目 → Maven → Reload Projects
3. 运行 `mvn clean install` 前先刷新 IDE 缓存

### VS Code
1. 安装 Extension Pack for Java
2. 使用 Maven Explorer 视图
3. 验证 pom.xml 中的错误标记

---

## 九、常见问题排查

| 问题 | 原因 | 解决方案 |
|------|------|---------|
| 找不到版本号的依赖 | dependencyManagement 缺少版本 | 在 parent 的 DM 中添加 |
| Parent POM 无法解析 | 版本号使用了未定义的属性 | 在 properties 中定义属性或使用固定版本 |
| 编译错误：无效的目标版本 | ${java.version} 包含完整版本 | 改用 ${maven.compiler.target} |
| 构建变慢 | 每次创建新 HttpClient | 使用 static final 单例 |
| 代码有警告 | 使用过时 API | 用现代替代方案 |

---

## 十、定期维护任务

**每月**：
- [ ] 检查依赖更新：`mvn versions:display-dependency-updates`
- [ ] 检查 CVE：`mvn dependency-check:check`

**每季度**：
- [ ] 更新 Spring Boot 版本
- [ ] 更新主要依赖版本
- [ ] 检查 Java 版本兼容性

**每年**：
- [ ] 审计所有依赖
- [ ] 清理无用的依赖
- [ ] 重新评估 parent POM 策略

---

## 参考资源

- [Maven 官方文档 - Dependency Management](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html)
- [Spring Boot 依赖管理](https://spring.io/projects/spring-boot)
- [Java API 现代化指南](https://docs.oracle.com/en/java/javase/21/docs/)
