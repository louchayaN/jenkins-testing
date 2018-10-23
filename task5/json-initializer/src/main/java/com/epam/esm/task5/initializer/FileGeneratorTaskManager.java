package com.epam.esm.task5.initializer;

import com.epam.esm.task5.initializer.dto.InitializerPropertiesDTO;
import com.epam.esm.task5.initializer.util.FileUtil;
import com.epam.esm.task5.repository.QuestRepository;
import com.epam.esm.task5.repository.specification.quest.CountAllQuestsSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Component
public class FileGeneratorTaskManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileGeneratorTaskManager.class);

  private static final int ROOT_FOLDER_COUNT = 1;
  private static final int SUBFOLDERS_COUNT_IN_ROOT_FOLDER = 3;

  private static final long WITHOUT_DELAY_ON_START = 0L;
  private static final long ALL_FILES_WERE_PARSED_CHECKING_DELAY = 5L;

  private static final AtomicLong allTasksIterationCount = new AtomicLong(0L);

  private CountDownLatch activeTasksCount;

  @Autowired private InitializerPropertiesDTO config;
  @Autowired private QuestRepository questRepository;

  public void start() throws IOException {
    LOGGER.info("===========================================");
    LOGGER.info("TEST JSON FILE INITIALIZER UTILITY RUN");

    activeTasksCount = new CountDownLatch(config.getSubfoldersCount() + ROOT_FOLDER_COUNT);

    LOGGER.info("Root directory and error folder are cleaning or creating if don't exist");

    FileUtil.createOrCleanDirectoryIfExists(config.getRootFolderPath());
    FileUtil.createOrCleanDirectoryIfExists(config.getErrorFolderPath());

    createSubfoldersInRootFolder();

    long questCountOnStart = questRepository.count(new CountAllQuestsSpecification());
    long fileCountInErrorFolderOnStart = getFileCountInErrorFolder();

    fillSubfoldersWithJsonFiles();

    try {
      activeTasksCount.await();
    } catch (InterruptedException e) {
      LOGGER.warn("Interrupted!", e);
      Thread.currentThread().interrupt();
    }

    LOGGER.info("Waiting while all files will be parsed by parser utility...");
    getUtilityWorkResultWhenAllFilesParsed(questCountOnStart, fileCountInErrorFolderOnStart);
  }

  private void createSubfoldersInRootFolder() throws IOException {
    int averageSubfoldersDepth =
        (int) Math.ceil((config.getSubfoldersCount() / (double) (SUBFOLDERS_COUNT_IN_ROOT_FOLDER)));
    FileUtil.createSubfolders(
        config.getRootFolderPath(), config.getSubfoldersCount(), averageSubfoldersDepth);

    LOGGER.info("{} subfolders were created", config.getSubfoldersCount());
  }

  private void fillSubfoldersWithJsonFiles() throws IOException {
    try (Stream<Path> paths = Files.walk(Paths.get(config.getRootFolderPath()))) {
      paths.filter(Files::isDirectory).forEach(this::initFileGeneratorTask);
    }
  }

  private void initFileGeneratorTask(Path directoryPath) {
    FileGeneratorTask fileGeneratorTask = new FileGeneratorTask(directoryPath.toString(), config);
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    ScheduledExecutorService extraWaitPreventingStopper =
        Executors.newSingleThreadScheduledExecutor();

    LOGGER.info("Thread run...");

    executor.scheduleAtFixedRate(
        fileGeneratorTask,
        WITHOUT_DELAY_ON_START,
        (long) (config.getDelayBetweenInitialisationsSeconds() * 1000),
        TimeUnit.MILLISECONDS);

    executor.schedule(
        () -> {
          LOGGER.info("Thread exit...");
          executor.shutdown();
          extraWaitPreventingStopper.shutdownNow();
          allTasksIterationCount.addAndGet(fileGeneratorTask.getIterationCount());
          activeTasksCount.countDown();
        },
        config.getTestTimeInSeconds(),
        TimeUnit.SECONDS);

    extraWaitPreventingStopper.schedule(
        () -> {
          if (!executor.isTerminated()) {
            executor.shutdownNow();
            try {
              LOGGER.info(
                  "Extra stop of initializer task work has happened. The total time of utility work is more than it was defined. You can change the time of utility work in property file");
              executor.awaitTermination(60, TimeUnit.MINUTES);
              allTasksIterationCount.addAndGet(fileGeneratorTask.getIterationCount());
              LOGGER.info("Thread was terminated...");
              activeTasksCount.countDown();
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            } finally {
              extraWaitPreventingStopper.shutdown();
            }
          }
        },
        config.getTotalUtilityWorkTimeInSeconds(),
        TimeUnit.SECONDS);
  }

  private long getFileCountInErrorFolder() {
    return new File(config.getErrorFolderPath()).list().length;
  }

  private void getUtilityWorkResultWhenAllFilesParsed(
      long questCountOnStart, long fileCountInErrorFolderOnStart) {

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    executor.scheduleWithFixedDelay(
        () -> {
          if (FileUtil.getFilesCountInDirectory(config.getRootFolderPath()) == 0) {
            executor.shutdown();
            getUtilityWorkResult(questCountOnStart, fileCountInErrorFolderOnStart);
          }
        },
        WITHOUT_DELAY_ON_START,
        ALL_FILES_WERE_PARSED_CHECKING_DELAY,
        TimeUnit.SECONDS);
  }

  private void getUtilityWorkResult(long questCountOnStart, long fileCountInErrorFolderOnStart) {
    LOGGER.info("Getting the results of test utility work...");

    long questCountOnFinish = questRepository.count(new CountAllQuestsSpecification());
    long fileCountInErrorFolderOnFinish = getFileCountInErrorFolder();

    long totalFilesCount = allTasksIterationCount.get() * config.getFilesPerFolder();
    long validFilesCount = allTasksIterationCount.get() * config.getValidFiles();
    long invalidFilesCount =
        allTasksIterationCount.get()
            * (config.getFilesWithInvalidJsonFormat()
                + config.getFilesWithInvalidFieldNames()
                + config.getFilesWithInvalidBean()
                + config.getFilesWithDbConstraintViolation());
    long questCountInValidFiles = validFilesCount * config.getQuestsPerFile();
    long fileCountAddedToErrorFolder =
        fileCountInErrorFolderOnFinish - fileCountInErrorFolderOnStart;
    long questCountAddedToDb = questCountOnFinish - questCountOnStart;

    LOGGER.info("TEST RESULT");

    if (invalidFilesCount == fileCountAddedToErrorFolder
        && questCountInValidFiles == questCountAddedToDb) {
      LOGGER.info("================================= SUCCESSFUL =================================");
    } else {
      LOGGER.info("================================= FAILED =================================");
    }

    LOGGER.info(
        "GENERATED: {} files (totalCount) with {} quests per file",
        totalFilesCount,
        config.getQuestsPerFile());
    LOGGER.info("GENERATED: {} valid files", validFilesCount);
    LOGGER.info("GENERATED: {} invalid files", invalidFilesCount);
    LOGGER.info("GENERATED: {} quests in valid files", questCountInValidFiles);
    LOGGER.info("AS RESULT: {} files were moved to error folder", fileCountAddedToErrorFolder);
    LOGGER.info("AS RESULT: {} quests were added to DB", questCountAddedToDb);
  }
}
