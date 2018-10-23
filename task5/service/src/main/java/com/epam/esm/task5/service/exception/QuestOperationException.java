package com.epam.esm.task5.service.exception;

public class QuestOperationException extends RuntimeException {

  public QuestOperationException() {
    super();
  }

  public QuestOperationException(String message) {
    super(message);
  }
}
