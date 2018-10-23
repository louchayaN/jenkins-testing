package com.epam.esm.task5.parser;

import com.epam.esm.task5.service.dto.quest.QuestDTO;

public class QuestValidator {

  private QuestValidator() {
    throw new AssertionError("Class contains static methods only");
  }

  public static boolean isValidQuest(QuestDTO questDTO) {
    String name = questDTO.getName();
    if (name == null) {
      return false;
    }

    String description = questDTO.getDescription();
    if (description == null) {
      return false;
    }
    if (description.length() < 2 || description.length() > 10000) {
      return false;
    }

    String genre = questDTO.getGenre();
    if (genre == null) {
      return false;
    }
    if (genre.length() < 2 || genre.length() > 50) {
      return false;
    }

    Integer duration = questDTO.getDuration();
    if (duration == null) {
      return false;
    }
    if (duration <= 0 || duration > 12 * 60) {
      return false;
    }

    Integer maxNumberOfPeople = questDTO.getMaxNumberOfPeople();
    if (maxNumberOfPeople == null) {
      return false;
    }
    if (maxNumberOfPeople <= 0 || maxNumberOfPeople > 30000) {
      return false;
    }

    if (questDTO.getDateOfReservationStart() == null) {
      return false;
    }

    return questDTO.getDateOfReservationEnd() != null;
  }
}
