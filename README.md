# Spring Boot 实战(Spring Boot In Action)

- ## 1. 入门

  - ### 1.1 Spring 风云再起
    - #### 1.1.1 重新认识 Spring
      - ##### sprin 开发应用
        - 一个项目结构，其中有一个包含必要依赖的 Maven 或者 Gradle 构建文件，最起码要有 Spring MVC 和 Servlet API 这些依赖。
        - 一个 web.xml 文件（或者一个 WebApplicationInitializer 实现），其中声明了 Spring 的 DispatcherServlet。
        - 一个启用了 Spring MVC 的 Spring 配置
        - 一个控制器类，以“Hello World”响应 HTTP 请求。
        - 一个用于部署应用程序的 Web 应用服务器，比如 Tomcat。
    - #### 1.1.2 Spring Boot 精要
      - ##### Spring Boot 开发应用
        - 自动配置(Automatic configuration)：针对很多 Spring 应用程序常见的应用功能，Spring Boot 能自动提供相关配置。
        - 起步依赖(Starter dependencies)：告诉 Spring Boot 需要什么功能，它就能引入需要的库。
        - 命令行界面(Command-line interface CLI)：这是 Spring Boot 的可选特性，借此你只需写代码就能完成完整的应用程序，无需传统项目构建。
        - Actuator：让你能够深入运行中的 Spring Boot 应用程序，一探究竟。
    - #### 1.1.3 Spring Boot 不是什么
      - 不是应用服务器
      - 没有实现 JPA 或 JMS
      - 没有代码生成
        **spring boot 就是 spring**
  - ### 1.2 Spring Boot 入门

    - #### 初始化 Spring Boot 项目有以下四种方式：

      - 使用网站接口 (http://start.spring.io)

        1. 在浏览器中输入http://start.spring.io，输入项目依赖和其它信息，点击按钮生成并下载一个zip项目压缩包。重要输入项如下：
           - 构建工具：gradle 或 maven
           - Spring Boot 版本
           - 项目元数据：Group 和 Artifact
           - 依赖的 Spring Starters
        2. 生成一个项目名为 com.example.demo 的 maven 项目，依赖于 Web、Thymeleaf、JPA、H2，生成的 project 基本结构，如下：

        ```cmd
        readinglist
        ├─ ch01
        └─ src
            ├─ main
            │   ├─ java
            │   │    └─ readinglist
            │   │        └─ ReadingListApplication.java
            │   └─ resources
            │        ├─ application.properties
            │        ├─ static
            │        └─ templates
            └─ test
                └─ java
                    └─ readinglist
                        └─ ReadingListApplicationTests.java
        ```

        ReadingListApplication.java 文件内容如下：

        ```java
        package readinglist;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;

        @SpringBootApplication
        public class ReadingListApplication {
            public static void main(String[] args) {
                SpringApplication.run(ReadingListApplication.class, args);
            }
        }
        ```

        **注意两点：**

        1. @SpringBootApplication 由@Configuration、@ComponentScan、@EnableAutoConfiguration 三个注解组成，使 Spring 能够自动扫描 bean 和自动化配置。
        2. SpringApplication.run 将启动应用程序。

      - 通过 IDE 工具
        通过 Spring Initializr 创建 spring boot 项目
      - 使用 Spring Boot CLI
        示例如下：

      ```cmd
      spring init -dweb,data-jpa,h2,thymeleaf --build gradle readinglist
      ```

- ## 2. 开发第一个应用程序

  - ### 2.1 运用 Spring Boot

    - #### 2.1.1 查看初始化的 Spring Boot 新项目

      - 构建说明文件
      - ReadingListApplication.java：应用程序的启动引导类（bootstrap class），也是主要的 Spring 配置类
      - application.properties：用于配置应用程序和 Spring Boot 的属性
      - ReadingListApplicationTests.java：一个基本的集成测试类。

      * ##### 2.1.1.1 启动引导 Spring

      ```java
      package readinglist;

      import org.springframework.boot.SpringApplication;
      import org.springframework.boot.autoconfigure.SpringBootApplication;

      @SpringBootApplication // 开启组件扫描和自动配置
      public class ReadingListApplication {

          public static void main(String[] args) {
              SpringApplication.run(ReadingListApplication.class, args); // 负责启动引导应用程序
          }
      }
      ```

      `@SpringBootApplication`开启组件扫描和自动配置, 它由三个有用的注解组合在了一起

      - @Configuration: 标明该类使用 Spring 基于 Java 的配置
      - @ComponentScan: 启用组件扫描
      - @EnableAutoConfiguration: 开启了 Spring Boot 自动配置
        通过构建工具就可以生成 jar 包, java jar 运行

      ```cmd
      java -jar path_to_jar_packege/readinglist-0.0.1-SNAPSHOT.jar
      ```

      在打包的时候我们还以指定排除某些的依赖

      ```xml
      <!-- pom.xml -->
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
          <!-- 排除 jar包 -->
          <exclusions>
              <exclusion>
                  <groupId>com.fasterxml.jackson.core</groupId>
              </exclusion>
          </exclusions>
      </dependency>
      <!-- 指定版本的jackson jar包 -->
      <dependency>
          <groupId>com.fasterxml.jackson.core</groupId>
          <artifactId>jackson-databind</artifactId>
          <version>2.4.3</version>
      </dependency>
      ```

      - ##### 2.1.1.2 测试 Spring Boot 应用程序

      ```java
      package readinglist;

      import org.junit.Test;
      import org.junit.runner.RunWith;
      import org.springframework.boot.test.SpringApplicationConfiguration;
      import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
      import org.springframework.test.context.web.WebAppConfiguration;

      import readinglist.ReadingListApplication;

      @RunWith(SpringJUnit4ClassRunner.class)
      @SpringApplicationConfiguration(classes = ReadingListApplication.class) // 通过Spring Boot加载上下文
      @WebAppConfiguration
      public class ReadingListApplicationTests {
        @Test
        public void contextLoads() { // 测试加载的上下
        }
      }
      ```

      ReadingListApplicationTests 使用 @SpringApplicationConfiguration 注解从 ReadingListApplication 配置类里加载 Spring 应用程序上下文。

      - ##### 2.1.1.3 配置应用程序属性
        使用 application.yml 配置 spring 例如:

      ```yml
      server:
        port: 8000 # 设置应用程序的启动端口
      ```

    - #### 2.1.2 Spring Boot 项目构建过程解析

      可以选择 Gradle 或 Maven 作为构建工具

      - build.gradle

      ```gradle
      buildscript {
          ext {
              springBootVersion = '1.4.7.RELEASE' // 定义变量
          }
          repositories {
              maven { url "http://repo.spring.io/milestone" }
              mavenCentral()
          }
          dependencies {
              classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}") // 依赖SpringBoot插件
          }
      }

      apply plugin: 'java'
      apply plugin: 'eclipse'
      apply plugin: 'idea'
      apply plugin: 'org.springframework.boot' // 运用Spring Boot插件

      jar {
          baseName = 'demo'
          version = '0.0.1-SNAPSHOT'
      }
      sourceCompatibility = 1.8
      targetCompatibility = 1.8

      repositories {
          maven { url "http://repo.spring.io/milestone" }
          mavenCentral()
      }

      dependencies {
          compile("org.springframework.boot:spring-boot-starter-actuator") //
          compile("org.springframework.boot:spring-boot-starter-web")
          compile("org.springframework.boot:spring-boot-starter-data-jpa")
          compile("org.springframework.boot:spring-boot-starter-thymeleaf")
          compile("com.h2database:h2")
          testCompile("org.springframework.boot:spring-boot-starter-test")
      }

      eclipse {
          classpath {
              containers.remove('org.eclipse.jdt.launching.JRE_CONTAINER')
              containers 'org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.7'
          }
      }

      task wrapper(type: Wrapper) {
          gradleVersion = '3.5.1'
      }
      ```

      - pom.xml

      ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
          <modelVersion>4.0.0</modelVersion>
          <groupId>com.manning</groupId>
          <artifactId>readinglist</artifactId>
          <version>0.0.1-SNAPSHOT</version>
          <packaging>jar</packaging>
          <name>ReadingList</name>
          <description>Reading List Demo</description>
          <properties>
              <spring.boot.version>1.4.7.RELEASE</spring.boot.version>
          </properties>
          <parent>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-parent</artifactId> <!-- 将spring-boot-starter-parent作为上一级，这样一来就能利用Maven的依赖管理功能，继承很多常用库的依赖版本，在你声明依赖时就不用再去指定版本号了。-->
              <version>${spring.boot.version}</version>
              <relativePath /> <!-- lookup parent from repository -->
          </parent>
          <dependencies>
              <dependency>
                  <groupId>org.springframework.boot</groupId><!-- 起步依赖 -->
                  <artifactId>spring-boot-starter-web</artifactId>
              </dependency>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-data-jpa</artifactId>
              </dependency>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-thymeleaf</artifactId>
              </dependency>
              <dependency>
                  <groupId>com.h2database</groupId>
                  <artifactId>h2</artifactId>
              </dependency>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-test</artifactId>
                  <scope>test</scope>
              </dependency>
          </dependencies>
          <properties>
              <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
              <start-class>readinglist.Application</start-class>
              <java.version>1.7</java.version>
          </properties>
          <build>
              <plugins> <!-- 运用Spring Boot插件 -->
                  <plugin>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-maven-plugin</artifactId>
                  </plugin>
              </plugins>
          </build>
      </project>
      ```

      spring-boot-starter-前缀的都是 Spring Boot 起步依赖，它们都有助于 Spring Boot 应用程序的构建。

  - ### 2.2 使用起步依赖

    在构建文件里指定功能，让构建过程自己搞明白我们要什么东西，这正是 Spring Boot 起步依赖的功能。

    - #### 2.2.1 指定基于功能的依赖

    ```cmd
    # 查看关系树
    $ gradle dependencies
    $ mvn dependency:tree
    ```

    - #### 2.2.2 覆盖起步依赖引入的传递依赖
      为你的项目瘦身

    ```gradle
    // 在Gradle中，你可以这样排除传递依赖
    compile("org.springframework.boot:spring-boot-starter-web") {
        exclude group: 'com.fasterxml.jackson.core'
    }
    ```

    ```xml
    // 在Maven中，你可以这样排除传递依赖
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>com.fasterxml.jackson.core</groupId>
            </exclusion>
        </exclusions>
    </dependency>
    ```

    如果你需要指定依赖版本

    ```gradle
    compile("com.fasterxml.jackson.core:jackson-databind:2.4.3")
    ```

    ```xml
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.4.3</version>
    </dependency>
    ```

  - ### 2.3 使用自动配置

    Spring Boot 的自动配置会在应**用程序启动**时，考虑了众多因素，才决定 Spring 配置应该用哪个，不该用哪个。每当应用程序启动的时候，Spring Boot 的自动配置都要做将近 200 个这样的决定，涵盖安全、集成、持久化、Web 开发等诸多方面。所有这些自动配置就是为了尽量不让你自己写配置
    例如：

    1. 如果在 classpath 路径下的 JdbcTemplate 是否可用？如果存在 DataSource bean，将会自动配置一个 JdbcTemplate bean
    2. classpath 下是否存在 Thymeleaf？如果存在，将自动配置一个 Thymeleaf 模板 resolver、view resolver 和 template engine。
    3. classpath 下是否存在 Spring Security？如果存在，配置一个基本 web 安全模式。

    - #### 2.3.1 专注于应用程序功能

    1. 定义领域模型

    ```java
    // /src/main/java/readinglist/Book.java
    package readinglist;

    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.GenerationType;
    import javax.persistence.Id;

    @Entity
    public class Book {
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private String reader;
        private String isbn;
        private String title;
        private String author;
        private String description;

        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getReader() {
            return reader;
        }
        public void setReader(String reader) {
            this.reader = reader;
        }
        public String getIsbn() {
            return isbn;
        }
        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getAuthor() {
            return author;
        }
        public void setAuthor(String author) {
            this.author = author;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
    }
    ```

    - @Entity 注解表明它是一个 JPA 实体
    - id 属性加了@Id 和@GeneratedValue 注解，说明这个字段是实体的唯一标识，并且这个字段的值是自动生成的

    2. 定义仓库接口

    ```java
    // /src/main/java/readinglist/ReadingListRepository.java
    package readinglist;

    import java.util.List;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface ReadingListRepository extends JpaRepository<Book, Long> {
        List<Book> findByReader(String reader);
    }
    ```

    - 通过扩展 JpaRepository，ReadingListRepository 直接继承了 18 个执行常用持久化操作的方法
    - JpaRepository 是个泛型接口，有两个参数：仓库操作的领域对象类型，及其 ID 属性的类型
    - findByReader(): 根据读者的用户名来查找阅读列表

    3. 创建 Web 界面
       控制器

    ```java
    // /src/main/java/readinglist/ReadingListController.java
    package readinglist;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestMethod;
    import java.util.List;

    @Controller
    @RequestMapping("/")
    public class ReadingListController {
        private ReadingListRepository readingListRepository;

        @Autowired
        public ReadingListController(ReadingListRepository readingListRepository) {// 注入
            this.readingListRepository = readingListRepository;
        }

        @RequestMapping(value="/{reader}", method=RequestMethod.GET)
        public String readersBooks(@PathVariable("reader") String reader, Model model) {
            List<Book> readingList =
            readingListRepository.findByReader(reader);
            if (readingList != null) {
                model.addAttribute("books", readingList);
            }
            return "readingList";
        }

        @RequestMapping(value="/{reader}", method=RequestMethod.POST)
        public String addToReadingList(@PathVariable("reader") String reader, Book book) {
            book.setReader(reader);
            readingListRepository.save(book);
            return "redirect:/{reader}";
        }
    }
    ```

    - @Controller: 组件扫描会自动将其注册为 Spring 应用程序上下文里的一个 Bean
    - @RequestMapping: 将其中所有的处理器方法都映射到了“/”这个 URL 路径上
    - @RequestMapping(value="/{reader}", method=RequestMethod.POST): 指定 url 和 method 的处理器
    - @PathVariable("reader"): 接受指定的路径参数

    在src/main/resources/templates里添加模板

    ```html
    <!--  src/main/resources/templates/readingList.html -->
    <html>
        <head>
            <title>Reading List</title>
            <link rel="stylesheet" th:href="@{/style.css}"></link>
        </head>
        <body>
            <h2>Your Reading List</h2>
            <div th:unless="${#lists.isEmpty(books)}">
                <dl th:each="book : ${books}">
                    <dt class="bookHeadline">
                        <span th:text="${book.title}">Title</span> by
                        <span th:text="${book.author}">Author</span>
                        (ISBN: <span th:text="${book.isbn}">ISBN</span>)
                    </dt>
                    <dd class="bookDescription">
                        <span th:if="${book.description}" th:text="${book.description}">Description</span>
                        <span th:if="${book.description eq null}">No description available</span>
                    </dd>
                </dl>
            </div>
            <div th:if="${#lists.isEmpty(books)}">
                <p>You have no books in your book list</p>
            </div>
            <hr/>
            <h3>Add a book</h3>
            <form method="POST">
                <label for="title">Title:</label>
                <input type="text" name="title" size="50"></input><br/>
                <label for="author">Author:</label>
                <input type="text" name="author" size="50"></input><br/>
                <label for="isbn">ISBN:</label>
                <input type="text" name="isbn" size="15"></input><br/>
                <label for="description">Description:</label><br/>
                <textarea name="description" cols="80" rows="5">
                </textarea><br/>
                <input type="submit"></input>
            </form>
        </body>
    </html>
    ```
    在src/main/resources/static中添加样式
    ```css
    /* src/main/resources/static/style.css */
    body {
        background-color: #cccccc;
        font-family: arial,helvetica,sans-serif;
    }
    .bookHeadline {
        font-size: 12pt;
        font-weight: bold;
    }
    .bookDescription {
        font-size: 10pt;
    }
    label {
        font-weight: bold;
    }
    ```
    - #### 2.3.2 运行应用程序
    - #### 2.3.4 刚刚发生了什么
        在添加Spring Boot到应用程序中时，会添加spring-boot-autoconfigure.jar，它包含大量地配置类。这些配置类在应用程序的classpath环境都可用，除非你明确指定了这些配置覆盖它们。那些实现对这些配置类中的配置的覆盖呢？——使用条件注解@Condition 例如在应用程序中指定了JdbcTemplate，就会使用用户自定义，否则使用默认配置类中的JdbcTemplate。实现这一目标的自定义Condition注解如下：
        ```java
        package readinglist;
        import org.springframework.context.annotation.Condition;
        import org.springframework.context.annotation.ConditionContext;
        import org.springframework.core.type.AnnotatedTypeMetadata;

        public class JdbcTemplateCondition implements Condition {
            @Override
            public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
                try {
                    context.getClassLoader().loadClass("org.springframework.jdbc.core.JdbcTemplate");
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        ```

        ```java
        // 如果在classpath路径下JdbcTemplate可用，就会创建MyService bean，否则不创建。
        @Conditional(JdbcTemplateCondition.class)
        public MyService myService() {
            //...
        }
        ```
        ##### Spring Boot定义了很多这样的条件化注解(Conditional 依附 依赖)
        |  条件化注解   | 条件化注解  |
        |  ----  | ----  |
        |@ConditionalOnBean             |配置了某个特定Bean 
        |@ConditionalOnMissingBean      |没有配置特定的Bean 
        |@ConditionalOnClass            |Classpath里有指定的类
        |@ConditionalOnMissingClass     |Classpath里缺少指定的类
        |@ConditionalOnExpression       |给定的Spring Expression Language（SpEL）表达式计算结果为true
        |@ConditionalOnJava             |Java的版本匹配特定值或者一个范围值
        |@ConditionalOnJndi             |参数中给定的JNDI位置必须存在一个，如果没有给参数，则要有JNDI InitialContext
        |@ConditionalOnProperty         |指定的配置属性要有一个明确的值
        |@ConditionalOnResource         |Classpath里有指定的资源
        |@ConditionalOnWebApplication   |这是一个Web应用程序
        |@ConditionalOnNotWebApplication|这不是一个Web应用程序

        举例说明
        ```java
        @Configuration // 只是一个配置类
        @ConditionalOnClass({ DataSource.class, EmbeddedDatabaseType.class }) // 依附于 DataSource.class 和 EmbeddedDatabaseType.class
        @EnableConfigurationProperties(DataSourceProperties.class) // 指定的配置的属性生效
        @Import({ Registrar.class, DataSourcePoolMetadataProvidersConfiguration.class }) // 导入 Registrar.class 和 DataSourcePoolMetadataProvidersConfiguration.class
        public class DataSourceAutoConfiguration { 
            ... 
        }
        ```