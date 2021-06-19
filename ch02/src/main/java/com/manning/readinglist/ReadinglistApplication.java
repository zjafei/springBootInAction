package com.manning.readinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication将三个有用的注解组合在了一起。
*	 Spring的@Configuration：标明该类使用Spring基于Java的配置。虽然本书不会写太多配置，但我们会更倾向于使用基于Java而不是XML的配置。
*	 Spring的@ComponentScan：启用组件扫描，这样你写的Web控制器类和其他组件才能被自动发现并注册为Spring应用程序上下文里的Bean。本章稍后会写一个简单的Spring MVC控制器，使用@Controller进行注解，这样组件扫描才能找到它。
*	 Spring Boot 的 @EnableAutoConfiguration ：这个不起眼的小注解也可以称为@Abracadabra①，就是这一行配置开启了Spring Boot自动配置的魔力，让你不用再写成篇的配置了
 */
@SpringBootApplication
public class ReadinglistApplication {

	public static void main(String[] args) {
		System.out.println("----------- spring boot 动起来 -----------------");
		SpringApplication.run(ReadinglistApplication.class, args);
	}

}
