package com.epam.esm.task5.parser.config;

import com.epam.esm.task5.parser.dto.ParserPropertiesDTO;
import com.epam.esm.task5.parser.exception.ParserPropertiesException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:parser.properties")
public class ParserConfig {

  @Value("${rootFolder.path}")
  private String rootFolderPath;

  @Value("${errorFolder.path}")
  private String errorFolderPath;

  @Value("${parser.scanDelay.seconds}")
  private double scanDelay;

  @Value("${parser.threadPool.size}")
  private int threadPoolSize;

  @Bean
  public ParserPropertiesDTO parserPropertiesDTO() {
    ParserPropertiesDTO config = new ParserPropertiesDTO();

    config.setRootFolderPath(rootFolderPath);
    config.setErrorFolderPath(errorFolderPath);
    config.setScanDelay(scanDelay);
    config.setThreadPoolSize(threadPoolSize);

    validateParserProperties(config);

    return config;
  }

  private void validateParserProperties(ParserPropertiesDTO config) {
    if (config.getScanDelay() < 0.001) {
      throw new ParserPropertiesException(
          "Minimum size of delay between parser scanning is 0.001 seconds");
    }

    if (config.getThreadPoolSize() <= 1) {
      throw new ParserPropertiesException("Minimum size of thread pool is 1");
    }
  }
}
