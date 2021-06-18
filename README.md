# Spring Boot 实战(Spring Boot In Action) 
- ## 1. 入门
    - ### 1.1 Spring 风云再起
        - #### 1.1.1 重新认识 Spring
            - ##### sprin 开发应用
                * 一个项目结构，其中有一个包含必要依赖的Maven或者Gradle构建文件，最起码要有Spring MVC和Servlet API这些依赖。
                * 一个web.xml文件（或者一个WebApplicationInitializer实现），其中声明了Spring的DispatcherServlet。
                * 一个启用了Spring MVC的Spring配置
                * 一个控制器类，以“Hello World”响应HTTP请求。
                * 一个用于部署应用程序的Web应用服务器，比如Tomcat。
        - #### 1.1.2 Spring Boot 精要
            - ##### Spring Boot开发应用
                * 自动配置(Automatic configuration)：针对很多Spring应用程序常见的应用功能，Spring Boot能自动提供相关配置。
                * 起步依赖(Starter dependencies)：告诉Spring Boot需要什么功能，它就能引入需要的库。
                * 命令行界面(Command-line interface CLI)：这是Spring Boot的可选特性，借此你只需写代码就能完成完整的应用程序，无需传统项目构建。
                * Actuator：让你能够深入运行中的Spring Boot应用程序，一探究竟。
        - #### 1.1.3 Spring Boot 不是什么
            * 不是应用服务器
            * 没有实现 JPA 或 JMS
            * 没有代码生成
            **spring boot 就是 spring** 
    - ### 1.2 Spring Boot 入门
        - #### 初始化Spring Boot项目有以下四种方式：
            * 使用网站接口 (http://start.spring.io)
                1. 在浏览器中输入http://start.spring.io，输入项目依赖和其它信息，点击按钮生成并下载一个zip项目压缩包。重要输入项如下：
                    * 构建工具：gradle或maven
                    * Spring Boot版本
                    * 项目元数据：Group和Artifact
                    * 依赖的Spring Starters
                2. 生成一个项目名为com.example.demo的maven项目，依赖于Web、Thymeleaf、JPA、H2，生成的project基本结构，如下：
                ```
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
                ReadingListApplication.java文件内容如下：
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
                1. @SpringBootApplication由@Configuration、@ComponentScan、@EnableAutoConfiguration三个注解组成，使Spring能够自动扫描bean和自动化配置。
                2. SpringApplication.run将启动应用程序。
            * 通过IDE工具
            通过 Spring Initializr 创建 spring boot 项目
            * 使用Spring Boot CLI
            示例如下：
            ```cmd
            spring init -dweb,data-jpa,h2,thymeleaf --build gradle readinglist
            ```