package com.epam.esm.task5.service.exception;

public class UserNameDuplicationException extends RuntimeException {

  private final String username;

  public UserNameDuplicationException(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
