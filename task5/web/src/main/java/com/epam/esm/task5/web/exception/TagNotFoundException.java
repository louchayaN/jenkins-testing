package com.epam.esm.task5.web.exception;

public class TagNotFoundException extends RuntimeException {

  private final Long tagId;

  public TagNotFoundException(Long tagId) {
    this.tagId = tagId;
  }

  public Long getTagId() {
    return tagId;
  }
}
