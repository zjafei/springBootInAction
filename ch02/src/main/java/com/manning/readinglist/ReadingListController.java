package com.manning.readinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// TODO Spring boot 配置的添加选择注解
// @ConditionalOnBean               配置了某个特定Bean 
// @ConditionalOnMissingBean        没有配置特定的Bean 
// @ConditionalOnClass              Classpath里有指定的类
// @ConditionalOnMissingClass       Classpath里缺少指定的类
// @ConditionalOnExpression         给定的Spring Expression Language（SpEL）表达式计算结果为true
// @ConditionalOnJava               Java的版本匹配特定值或者一个范围值
// @ConditionalOnJndi               参数中给定的JNDI位置必须存在一个，如果没有给参数，则要有JNDIInitialContext
// @ConditionalOnProperty           指定的配置属性要有一个明确的值
// @ConditionalOnResource           Classpath里有指定的资源
// @ConditionalOnWebApplication     这是一个Web应用程序
// @ConditionalOnNotWebApplication  这不是一个Web应用程序

// @Controller注解，这样组件扫描会自动将其注册为Spring应用程序上下文里的一个Bean。
@Controller
// @Controller注解，这样组件扫描会自动将其注册为Spring应用程序上下文里的一个Bean。
@RequestMapping("/")
public class ReadingListController {
    private ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader, Model model) {
        List<Book> readingList = readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
    public String addReadingList(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/{reader}";
    }
}
