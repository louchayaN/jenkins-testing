package com.epam.esm.task5.parser;

import com.epam.esm.task5.parser.dto.ParserPropertiesDTO;
import com.epam.esm.task5.service.QuestService;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

public class JsonFileParserTaskManager implements Runnable {

  private ThreadPoolExecutor executor;
  private QuestService questService;
  private ParserPropertiesDTO config;

  JsonFileParserTaskManager(
      ThreadPoolExecutor executor, QuestService questService, ParserPropertiesDTO config) {
    this.executor = executor;
    this.questService = questService;
    this.config = config;
  }

  @Override
  public void run() {
    if (executor.getActiveCount() == 0) {
      try (Stream<Path> paths = Files.walk(Paths.get(config.getRootFolderPath()))) {
        paths
            .filter(Files::isRegularFile)
            .forEach(file -> executor.submit(new JsonFileParserTask(questService, config, file)));
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
  }
}
