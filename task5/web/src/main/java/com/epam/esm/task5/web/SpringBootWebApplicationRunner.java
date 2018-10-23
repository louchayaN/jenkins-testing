package com.epam.esm.task5.web;

import com.epam.esm.task5.parser.ParserStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = "com.epam.esm.task5")
@SpringBootApplication
@PropertySource("classpath:parser.properties")
public class SpringBootWebApplicationRunner {

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(SpringBootWebApplicationRunner.class, args);

    ParserStarter parser = context.getBean(ParserStarter.class);
    parser.init();
  }

}
