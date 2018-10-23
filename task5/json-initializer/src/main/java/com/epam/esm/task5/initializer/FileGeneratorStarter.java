package com.epam.esm.task5.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@ComponentScan({"com.epam.esm.task5.initializer", "com.epam.esm.task5.repository"})
@SpringBootApplication
public class FileGeneratorStarter {

  public static void main(String[] args) throws IOException {
    ApplicationContext context = SpringApplication.run(FileGeneratorStarter.class, args);
    FileGeneratorTaskManager initializer = context.getBean(FileGeneratorTaskManager.class);
    initializer.start();
  }
}
