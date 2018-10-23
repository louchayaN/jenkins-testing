package com.epam.esm.task5.parser;

import com.epam.esm.task5.parser.dto.ParserPropertiesDTO;
import com.epam.esm.task5.service.QuestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ParserStarter {

  private static final Logger LOGGER = LoggerFactory.getLogger(ParserStarter.class);

  private static final long WITHOUT_DELAY_ON_START = 0L;

  @Autowired private ParserPropertiesDTO config;
  @Autowired private QuestService questService;

  public void init() {
    LOGGER.info("Json file parser started to work...");

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    ThreadPoolExecutor executorService =
        (ThreadPoolExecutor) Executors.newFixedThreadPool(config.getThreadPoolSize());

   executor.scheduleWithFixedDelay(
        new JsonFileParserTaskManager(executorService, questService, config),
        WITHOUT_DELAY_ON_START,
        (long) (config.getScanDelay() * 1000),
        TimeUnit.MILLISECONDS);
  }
}
