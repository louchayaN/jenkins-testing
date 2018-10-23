package com.epam.esm.task5.service.exception;

public class QuestNotAvailableThisDateException extends RuntimeException {

  public QuestNotAvailableThisDateException() {
    super();
  }

  public QuestNotAvailableThisDateException(String message) {
    super(message);
  }
}
