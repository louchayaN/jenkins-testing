package com.epam.esm.task5.parser;

import com.epam.esm.task5.parser.dto.ParserPropertiesDTO;
import com.epam.esm.task5.service.QuestService;
import com.epam.esm.task5.service.dto.quest.QuestDTO;
import com.epam.esm.task5.service.exception.DBConstraintException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class JsonFileParserTask implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileParserTask.class);

  private final ObjectMapper mapper = new ObjectMapper();

  private QuestService questService;
  private ParserPropertiesDTO config;
  private Path pathToFile;

  JsonFileParserTask(QuestService questService, ParserPropertiesDTO config, Path pathToFile) {
    this.questService = questService;
    this.config = config;
    this.pathToFile = pathToFile;
  }

  @Override
  public void run() {
    LOGGER.debug("Parser {} started", Thread.currentThread().getName());

    try {
      List<QuestDTO> quests = parseFile();
      for (QuestDTO quest : quests) {
        boolean isValid = QuestValidator.isValidQuest(quest);
        if (!isValid) {
          moveFileToErrorFolder();
          return;
        }
      }
      saveQuestsToDB(quests);
    } catch (UnrecognizedPropertyException | JsonParseException | DBConstraintException e) {
      moveFileToErrorFolder();
    } catch (IOException e2) {
      throw new UncheckedIOException(e2);
    }

    deleteFile();

    LOGGER.debug("Parser {} finished", Thread.currentThread().getName());
  }

  private List<QuestDTO> parseFile() throws IOException {
    return mapper.readValue(pathToFile.toFile(), new TypeReference<List<QuestDTO>>() {});
  }

  private void saveQuestsToDB(List<QuestDTO> quests) {
    questService.addQuests(quests);
  }

  private void deleteFile() {
    FileUtils.deleteQuietly(pathToFile.toFile());
  }

  private void moveFileToErrorFolder() {
    try {
      Files.move(
          pathToFile,
          Paths.get(config.getErrorFolderPath()).resolve(pathToFile.getFileName()),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
