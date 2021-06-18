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
      * ##### 2.1.1.2 测试Spring Boot应用程序
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
      ReadingListApplicationTests 使用 @SpringApplicationConfiguration 注解从 ReadingListApplication 配置类里加载Spring应用程序上下文。
      * ##### 2.1.1.3 配置应用程序属性
      使用 application.yml 配置 spring 例如:
      ```yml
      server:
        port: 8000 # 设置应用程序的启动端口 
      ```
    - #### 2.1.2 Spring Boot 项目构建过程解析
      可以选择Gradle或Maven作为构建工具

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
      spring-boot-starter-前缀的都是Spring Boot起步依赖，它们都有助于Spring Boot应用程序的构建。
  - ### 2.2 使用起步依赖