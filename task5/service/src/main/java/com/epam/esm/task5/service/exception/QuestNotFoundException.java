package com.epam.esm.task5.service.exception;

public class QuestNotFoundException extends RuntimeException {

  private final Long questId;

  public QuestNotFoundException(Long questId) {
    this.questId = questId;
  }

  public Long getQuestId() {
    return questId;
  }
}
