package com.epam.esm.task5.service;

import com.epam.esm.task5.repository.searchcriteria.QuestSearchCriteria;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.quest.QuestDTO;
import com.epam.esm.task5.service.dto.reservation.TimeSlot;

import java.util.List;

public interface QuestService {

  QuestDTO addQuestWithTags(QuestDTO questDTO);

  void addQuests(List<QuestDTO> questsDTO);

  QuestDTO getQuest(Long id);

  Pagination<QuestDTO> getQuestsBySearchCriteria(QuestSearchCriteria criteria);

  List<TimeSlot> getQuestScheduleForDay(Long questId, Long date);

  boolean isQuestIdExists(Long id);

  boolean isQuestBelongToOwner(Long questId, Long ownerId);

  QuestDTO updateQuestWithTagsById(QuestDTO questDTO);

  void deleteQuestById(Long questId, Long ownerId);
}
