package com.epam.esm.task5.initializer.dto;

public class InitializerPropertiesDTO {

  private String rootFolderPath;

  private String errorFolderPath;

  private int subfoldersCount;

  private int filesPerFolder;
  private int validFiles;
  private int filesWithInvalidJsonFormat;
  private int filesWithInvalidFieldNames;
  private int filesWithInvalidBean;
  private int filesWithDbConstraintViolation;

  private int questsPerFile;

  private double delayBetweenInitialisationsSeconds;

  private int testTimeInSeconds;
  private int totalUtilityWorkTimeInSeconds;

  public String getRootFolderPath() {
    return rootFolderPath;
  }

  public void setRootFolderPath(String rootFolderPath) {
    this.rootFolderPath = rootFolderPath;
  }

  public String getErrorFolderPath() {
    return errorFolderPath;
  }

  public void setErrorFolderPath(String errorFolderPath) {
    this.errorFolderPath = errorFolderPath;
  }

  public int getSubfoldersCount() {
    return subfoldersCount;
  }

  public void setSubfoldersCount(int subfoldersCount) {
    this.subfoldersCount = subfoldersCount;
  }

  public int getFilesPerFolder() {
    return filesPerFolder;
  }

  public void setFilesPerFolder(int filesPerFolder) {
    this.filesPerFolder = filesPerFolder;
  }

  public int getValidFiles() {
    return validFiles;
  }

  public void setValidFiles(int validFiles) {
    this.validFiles = validFiles;
  }

  public int getFilesWithInvalidJsonFormat() {
    return filesWithInvalidJsonFormat;
  }

  public void setFilesWithInvalidJsonFormat(int filesWithInvalidJsonFormat) {
    this.filesWithInvalidJsonFormat = filesWithInvalidJsonFormat;
  }

  public int getFilesWithInvalidFieldNames() {
    return filesWithInvalidFieldNames;
  }

  public void setFilesWithInvalidFieldNames(int filesWithInvalidFieldNames) {
    this.filesWithInvalidFieldNames = filesWithInvalidFieldNames;
  }

  public int getFilesWithInvalidBean() {
    return filesWithInvalidBean;
  }

  public void setFilesWithInvalidBean(int filesWithInvalidBean) {
    this.filesWithInvalidBean = filesWithInvalidBean;
  }

  public int getFilesWithDbConstraintViolation() {
    return filesWithDbConstraintViolation;
  }

  public void setFilesWithDbConstraintViolation(int filesWithDbConstraintViolation) {
    this.filesWithDbConstraintViolation = filesWithDbConstraintViolation;
  }

  public int getQuestsPerFile() {
    return questsPerFile;
  }

  public void setQuestsPerFile(int questsPerFile) {
    this.questsPerFile = questsPerFile;
  }

  public double getDelayBetweenInitialisationsSeconds() {
    return delayBetweenInitialisationsSeconds;
  }

  public void setDelayBetweenInitialisationsSeconds(double delayBetweenInitialisationsSeconds) {
    this.delayBetweenInitialisationsSeconds = delayBetweenInitialisationsSeconds;
  }

  public int getTestTimeInSeconds() {
    return testTimeInSeconds;
  }

  public void setTestTimeInSeconds(int testTimeInSeconds) {
    this.testTimeInSeconds = testTimeInSeconds;
  }

  public int getTotalUtilityWorkTimeInSeconds() {
    return totalUtilityWorkTimeInSeconds;
  }

  public void setTotalUtilityWorkTimeInSeconds(int totalUtilityWorkTimeInSeconds) {
    this.totalUtilityWorkTimeInSeconds = totalUtilityWorkTimeInSeconds;
  }
}
