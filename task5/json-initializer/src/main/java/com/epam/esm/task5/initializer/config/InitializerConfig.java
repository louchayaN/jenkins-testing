package com.epam.esm.task5.initializer.config;

import com.epam.esm.task5.initializer.dto.InitializerPropertiesDTO;
import com.epam.esm.task5.initializer.exception.InitializerPropertiesException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:initializer.properties")
public class InitializerConfig {

  @Value("${rootFolder.path}")
  private String rootFolderPath;

  @Value("${errorFolder.path}")
  private String errorFolderPath;

  @Value("${initializer.subfoldersInRootFolder.count}")
  private int subfoldersCount;

  @Value("${initializer.perFolder.files.totalCount}")
  private int filesPerFolder;

  @Value("${initializer.perFolder.filesWithInvalidJsonFormat.percentInTotalCount}")
  private int filesWithInvalidJsonFormatPercent;

  @Value("${initializer.perFolder.filesWithInvalidFieldNames.percentInTotalCount}")
  private int filesWithInvalidFieldNamesPercent;

  @Value("${initializer.perFolder.filesWithInvalidBean.percentInTotalCount}")
  private int filesWithInvalidBeanPercent;

  @Value("${initializer.perFolder.filesWithDbConstraintViolation.percentInTotalCount}")
  private int filesWithDbConstraintViolationPercent;

  @Value("${initializer.questsPerFile.count}")
  private int questsPerFile;

  @Value("${initializer.initializationDelay.seconds}")
  private double delayBetweenInitialisationsSeconds;

  @Value("${initializer.testTime.seconds}")
  private int testTimeInSeconds;

  @Value("${initializer.totalUtilityWorkTime.seconds}")
  private int totalUtilityWorkTimeInSeconds;

  @Bean
  public InitializerPropertiesDTO initializerProperties() {
    InitializerPropertiesDTO config = new InitializerPropertiesDTO();

    config.setRootFolderPath(rootFolderPath);
    config.setErrorFolderPath(errorFolderPath);
    config.setSubfoldersCount(subfoldersCount);

    config.setFilesPerFolder(filesPerFolder);
    config.setFilesWithInvalidJsonFormat(filesPerFolder * filesWithInvalidJsonFormatPercent / 100);
    config.setFilesWithInvalidFieldNames(filesPerFolder * filesWithInvalidFieldNamesPercent / 100);
    config.setFilesWithInvalidBean(filesPerFolder * filesWithInvalidBeanPercent / 100);
    config.setFilesWithDbConstraintViolation(
        filesPerFolder * filesWithDbConstraintViolationPercent / 100);

    config.setValidFiles(
        config.getFilesPerFolder()
            - config.getFilesWithInvalidJsonFormat()
            - config.getFilesWithInvalidFieldNames()
            - config.getFilesWithInvalidBean()
            - config.getFilesWithDbConstraintViolation());

    config.setQuestsPerFile(questsPerFile);
    config.setDelayBetweenInitialisationsSeconds(delayBetweenInitialisationsSeconds);
    config.setTestTimeInSeconds(testTimeInSeconds);
    config.setTotalUtilityWorkTimeInSeconds(totalUtilityWorkTimeInSeconds);

    validateInitializerProperties(config);

    return config;
  }

  private void validateInitializerProperties(InitializerPropertiesDTO config) {
    if (config.getSubfoldersCount() < 0 || config.getSubfoldersCount() > 100) {
      throw new InitializerPropertiesException(
          "Quantity of subfolders in root folder should be between 0 and 100");
    }

    if (config.getFilesPerFolder() <1 || config.getFilesPerFolder() > 10000) {
      throw new InitializerPropertiesException("Quantity of files per folder should be between 1 and 10000");
    }

    int totalInvalidFilesPercent =
        filesWithInvalidJsonFormatPercent
            + filesWithInvalidFieldNamesPercent
            + filesWithInvalidBeanPercent
            + filesWithDbConstraintViolationPercent;
    if (totalInvalidFilesPercent > 100) {
      throw new InitializerPropertiesException(
          "Total percent of invalid files in all files can't be no more than 100");
    }

    if (config.getQuestsPerFile() < 3) {
      throw new InitializerPropertiesException("Minimum quantity of quests per file is 3");
    }

    if (config.getDelayBetweenInitialisationsSeconds() < 0.001) {
      throw new InitializerPropertiesException(
          "Minimum size of delay between initialisations is 0.001 seconds");
    }

    if (config.getTestTimeInSeconds() < 1) {
      throw new InitializerPropertiesException("Minimum duration of test is 1 seconds");
    }

    if (config.getTotalUtilityWorkTimeInSeconds() < 1) {
      throw new InitializerPropertiesException("Minimum total duration of test utility work is 1 seconds");
    }
  }
}
