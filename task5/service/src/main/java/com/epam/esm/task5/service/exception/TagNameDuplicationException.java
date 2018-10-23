package com.epam.esm.task5.service.exception;

public class TagNameDuplicationException extends RuntimeException {

  private static final long serialVersionUID = -5598598870712885107L;

  private final String tagName;

  public TagNameDuplicationException(String tagName) {
    this.tagName = tagName;
  }

  public String getTagName() {
    return tagName;
  }
}
