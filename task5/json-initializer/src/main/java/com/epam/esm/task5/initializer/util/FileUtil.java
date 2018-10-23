package com.epam.esm.task5.initializer.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtil {

  private FileUtil() {
    throw new AssertionError("Class contains static methods only");
  }

  public static void createOrCleanDirectoryIfExists(String filePath) throws IOException {
    File file = new File(filePath);
    if (file.exists() && file.isDirectory()) {
      FileUtil.cleanDirectory(filePath);
    } else {
      FileUtil.createDirectory(filePath);
    }
  }

  public static void cleanDirectory(String pathToDirectory) throws IOException {
    FileUtils.cleanDirectory(new File(pathToDirectory));
  }

  public static void createDirectory(String complexPathToDirectory) throws IOException {
    Files.createDirectories(Paths.get(complexPathToDirectory));
  }

  public static void createSubfolders(
      String rootFolderPath, int subfoldersCount, int averageSubfoldersDepth) throws IOException {
    int depth = averageSubfoldersDepth;
    StringBuilder builder = new StringBuilder(rootFolderPath);
    for (int i = 1; i <= subfoldersCount; i++) {
      if (depth == 0) {
        FileUtil.createDirectory(builder.toString());
        depth = averageSubfoldersDepth;
        cleanBuilder(builder);
        builder.append(rootFolderPath);
      }

      builder.append(File.separator).append(i);
      depth--;
    }

    if (isBuilderNotEmpty(builder)) {
      FileUtil.createDirectory(builder.toString());
    }
  }

  private static void cleanBuilder(StringBuilder builder) {
    builder.setLength(0);
  }

  private static boolean isBuilderNotEmpty(StringBuilder builder) {
    return !builder.toString().isEmpty();
  }

  public static long getFilesCountInDirectory(String directoryPath) {
    try (Stream<Path> files = Files.walk(Paths.get(directoryPath))) {
      return files.filter(Files::isRegularFile).count();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
