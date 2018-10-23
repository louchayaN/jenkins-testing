package com.epam.esm.task5.service.util.mapper;

import com.epam.esm.task5.repository.entity.Quest;
import com.epam.esm.task5.repository.entity.Tag;
import com.epam.esm.task5.service.dto.quest.QuestDTO;
import com.epam.esm.task5.service.dto.quest.ReservedQuestDTO;
import com.epam.esm.task5.service.dto.tag.TagDTO;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestModelMapper {

  private static final ModelMapper MAPPER;

  static {
    MAPPER = new ModelMapper();
    MAPPER.createTypeMap(Long.class, Instant.class);
    MAPPER.addConverter(new LongToInstantConverter());
    MAPPER.getTypeMap(Long.class, Instant.class).setProvider(new InstantProvider());
  }

  private QuestModelMapper() {
    throw new AssertionError("Class contains static methods only");
  }

  public static Quest mapToQuest(QuestDTO questDTO) {
    if (questDTO == null) {
      return null;
    }

    ModelMapper mapper = new ModelMapper();
    mapper.createTypeMap(Long.class, Instant.class);
    mapper.addConverter(new LongToInstantConverter());
    mapper.getTypeMap(Long.class, Instant.class).setProvider(new InstantProvider());

    Quest quest = mapper.map(questDTO, Quest.class);
    if (questDTO.getTags() != null) {
      Set<Tag> tags =
          questDTO
              .getTags()
              .stream()
              .map(tagDTO -> mapper.map(tagDTO, Tag.class))
              .collect(Collectors.toSet());
      quest.setTags(tags);
    }

    return quest;
  }

  public static QuestDTO mapToQuestDTO(Quest quest) {
    if (quest == null) {
      return null;
    }

    ModelMapper mapper = new ModelMapper();
    mapper.addConverter(new InstantToLongConverter());

    QuestDTO questDTO = mapper.map(quest, QuestDTO.class);
    if (quest.getTags() != null) {
      List<TagDTO> tagsDTO =
          questDTO
              .getTags()
              .stream()
              .map(tag -> mapper.map(tag, TagDTO.class))
              .collect(Collectors.toList());
      questDTO.setTags(tagsDTO);
    }
    return questDTO;
  }

  public static ReservedQuestDTO mapToReservedQuestDTO(Quest quest) {
    return new ModelMapper().map(quest, ReservedQuestDTO.class);
  }

  public static List<QuestDTO> mapQuestsToQuestsDTO(List<Quest> quests) {
    if (quests == null) {
      return Collections.emptyList();
    }

    return quests.stream().map(QuestModelMapper::mapToQuestDTO).collect(Collectors.toList());
  }

  public static List<Quest> mapQuestsDTOToQuests(List<QuestDTO> questsDTO) {
    if (questsDTO == null) {
      return Collections.emptyList();
    }

    List<Quest> quests = new ArrayList<>();
    for (QuestDTO questDTO : questsDTO) {
      Quest quest = MAPPER.map(questDTO, Quest.class);
      if (questDTO.getTags() != null) {
        Set<Tag> tags =
            questDTO
                .getTags()
                .stream()
                .map(tagDTO -> MAPPER.map(tagDTO, Tag.class))
                .collect(Collectors.toSet());
        quest.setTags(tags);
      }

      quests.add(quest);
    }

    return quests;
  }
}
