package com.epam.esm.task5.service.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException() {}

  public UserNotFoundException(String message) {
    super(message);
  }
}
