package com.epam.esm.task5.initializer;

import com.epam.esm.task5.initializer.dto.InitializerPropertiesDTO;
import com.epam.esm.task5.service.dto.quest.QuestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileGeneratorTask implements Runnable {

  private static final String FILE_FORMAT = ".json";
  private static final String SEPARATION_SYMBOL = "_";

  private static final String FILE_NAME_WITH_INVALID_JSON = "InvalidJson";
  private static final String FILE_NAME_WITH_INVALID_FIELD_NAME = "InvalidFieldName";
  private static final String FILE_NAME_WITH_INVALID_BEAN = "InvalidBean";
  private static final String FILE_NAME_WITH_DB_CONSTRAINT = "DbConstraint";

  private static final String VALID_FIELD_NAME = "name";
  private static final String INVALID_FIELD_NAME = "nam";
  private static final String INVALID_FIELD_VALUE = "invalidFieldName";

  private final QuestGenerator questGenerator = new QuestGenerator();
  private final ObjectMapper mapper = new ObjectMapper();

  private int iterationCount;

  private final String directoryPath;
  private final InitializerPropertiesDTO config;

  FileGeneratorTask(String directoryPath, InitializerPropertiesDTO config) {
    this.directoryPath = directoryPath;
    this.config = config;
  }

  @Override
  public void run() {
    iterationCount++;
    generateFilesForIteration();
  }

  private void generateFilesForIteration() {
    long currentDate = System.currentTimeMillis();
    String uniqueString =
        Thread.currentThread().getName()
            + SEPARATION_SYMBOL
            + iterationCount
            + SEPARATION_SYMBOL
            + currentDate;

    generateValidFiles(uniqueString);
    generateFilesWithInvalidJsonFormat(uniqueString);
    generateFilesWithInvalidFieldNames(uniqueString);
    generateFilesWithInvalidBean(uniqueString);
    generateFilesWithDbConstraintViolation(uniqueString);
  }

  private void generateValidFiles(String uniqueString) {
    for (int i = 0; i < config.getValidFiles(); i++) {
      List<QuestDTO> quests =
          questGenerator.generateQuestsWithUniqueNames(
              config.getQuestsPerFile(), uniqueString + SEPARATION_SYMBOL + i);
      try {
        mapper.writeValue(
            new File(
                directoryPath
                    + File.separator
                    + uniqueString
                    + SEPARATION_SYMBOL
                    + i
                    + FILE_FORMAT),
            quests);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
  }

  private void generateFilesWithInvalidJsonFormat(String uniqueString) {
    for (int i = 0; i < config.getFilesWithInvalidJsonFormat(); i++) {
      List<QuestDTO> quests =
          questGenerator.generateQuestsWithUniqueNames(
              config.getQuestsPerFile(), uniqueString + SEPARATION_SYMBOL + i);
      try {
        String questJsonAsString = mapper.writeValueAsString(quests);
        String invalidJson = questJsonAsString.substring(0, questJsonAsString.length() - 1);
        FileUtils.writeStringToFile(
            new File(
                directoryPath
                    + File.separator
                    + FILE_NAME_WITH_INVALID_JSON
                    + SEPARATION_SYMBOL
                    + uniqueString
                    + SEPARATION_SYMBOL
                    + i
                    + FILE_FORMAT),
            invalidJson,
            StandardCharsets.UTF_8);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
  }

  private void generateFilesWithInvalidFieldNames(String uniqueString) {
    for (int i = 0; i < config.getFilesWithInvalidFieldNames(); i++) {
      List<QuestDTO> quests =
          questGenerator.generateQuestsWithUniqueNames(
              config.getQuestsPerFile(), uniqueString + SEPARATION_SYMBOL + i);
      JsonNode jsonNodes = mapper.valueToTree(quests);
      JsonNode node = jsonNodes.get(1);
      ObjectNode questAsMap = (ObjectNode) node;
      questAsMap.remove(VALID_FIELD_NAME);
      questAsMap.put(INVALID_FIELD_NAME, INVALID_FIELD_VALUE);
      try {
        mapper.writeValue(
            new File(
                directoryPath
                    + File.separator
                    + FILE_NAME_WITH_INVALID_FIELD_NAME
                    + SEPARATION_SYMBOL
                    + uniqueString
                    + SEPARATION_SYMBOL
                    + i
                    + FILE_FORMAT),
            jsonNodes);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
  }

  private void generateFilesWithInvalidBean(String uniqueString) {
    for (int i = 0; i < config.getFilesWithInvalidBean(); i++) {
      List<QuestDTO> quests =
          questGenerator.generateQuestsWithUniqueNames(
              config.getQuestsPerFile(), uniqueString + SEPARATION_SYMBOL + i);
      try {
        QuestDTO questDTO = quests.get(1);
        questDTO.setName(null);
        mapper.writeValue(
            new File(
                directoryPath
                    + File.separator
                    + FILE_NAME_WITH_INVALID_BEAN
                    + SEPARATION_SYMBOL
                    + uniqueString
                    + SEPARATION_SYMBOL
                    + i
                    + FILE_FORMAT),
            quests);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
  }

  private void generateFilesWithDbConstraintViolation(String uniqueString) {
    for (int i = 0; i < config.getFilesWithDbConstraintViolation(); i++) {
      List<QuestDTO> quests =
          questGenerator.generateQuestsWithUniqueNames(
              config.getQuestsPerFile(), uniqueString + SEPARATION_SYMBOL + i);
      try {
        QuestDTO questDTO = quests.get(1);
        questDTO.setName(quests.get(0).getName());
        mapper.writeValue(
            new File(
                directoryPath
                    + File.separator
                    + FILE_NAME_WITH_DB_CONSTRAINT
                    + SEPARATION_SYMBOL
                    + uniqueString
                    + SEPARATION_SYMBOL
                    + i
                    + FILE_FORMAT),
            quests);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
  }

  int getIterationCount() {
    return iterationCount;
  }
}
