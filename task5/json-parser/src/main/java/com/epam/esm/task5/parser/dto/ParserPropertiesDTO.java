package com.epam.esm.task5.parser.dto;

public class ParserPropertiesDTO {

  private String rootFolderPath;

  private String errorFolderPath;

  private double scanDelay;

  private int threadPoolSize;

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

  public double getScanDelay() {
    return scanDelay;
  }

  public void setScanDelay(double scanDelay) {
    this.scanDelay = scanDelay;
  }

  public int getThreadPoolSize() {
    return threadPoolSize;
  }

  public void setThreadPoolSize(int threadPoolSize) {
    this.threadPoolSize = threadPoolSize;
  }
}
