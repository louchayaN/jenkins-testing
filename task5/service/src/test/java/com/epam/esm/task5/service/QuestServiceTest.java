package com.epam.esm.task5.service;

import com.epam.esm.task5.repository.QuestRepository;
import com.epam.esm.task5.repository.entity.Quest;
import com.epam.esm.task5.repository.searchcriteria.PaginationCriteria;
import com.epam.esm.task5.repository.searchcriteria.QuestSearchCriteria;
import com.epam.esm.task5.repository.specification.quest.CountQuestsbySearchCriteriaSpecification;
import com.epam.esm.task5.repository.specification.quest.GetQuestsBySearchCriteriaSpecification;
import com.epam.esm.task5.repository.specification.quest.IsQuestIdExistsSpecification;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.quest.QuestDTO;
import com.epam.esm.task5.service.dto.tag.TagDTO;
import com.epam.esm.task5.service.exception.QuestReservationsDateException;
import com.epam.esm.task5.service.impl.QuestServiceImpl;
import com.epam.esm.task5.service.util.mapper.QuestModelMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class QuestServiceTest {

  @Mock private QuestRepository questRepository;
  @Mock private TagService tagService;

  @InjectMocks private QuestServiceImpl questService;

  private static final Long ID = 1L;

  private QuestDTO questDTO;
  private List<QuestDTO> questsDTO;

  private Quest quest;
  private List<Quest> quests;

  private List<TagDTO> tags;

  @Before
  public void doSetup() {
    MockitoAnnotations.initMocks(this);

    questDTO = new QuestDTO();
    questDTO.setId(1L);
    questDTO.setName("sport_team");
    questDTO.setDescription("quest for companies");
    questDTO.setGenre("sport");
    questDTO.setPrice(new BigDecimal("15"));
    questDTO.setDuration(45);
    questDTO.setDateOfReservationStart(1600000000L);
    questDTO.setDateOfReservationEnd(1650000000L);
    questDTO.setMaxNumberOfPeople(15);

    tags = new ArrayList<>();
    tags.add(new TagDTO(1L, "sport"));

    questDTO.setTags(tags);

    questsDTO = new ArrayList<>();
    questsDTO.add(questDTO);

    quest = QuestModelMapper.mapToQuest(questDTO);
    quests = new ArrayList<>();
    quests.add(quest);
  }

  @Ignore
  @Test(expected = QuestReservationsDateException.class)
  public void addQuestWithTags_ThrowException_IfReservationNotFutureDate() {
    QuestService spy = spy(questService);
    Long dateInThePast = 1500000000L;
    questDTO.setDateOfReservationStart(dateInThePast);

    spy.addQuestWithTags(questDTO);
  }

  @Test(expected = QuestReservationsDateException.class)
  public void addQuestWithTags_ThrowException_IfStartReservationDateAfterItsEnd() {
    QuestService spy = spy(questService);
    questDTO.setDateOfReservationStart(1650000000L);
    questDTO.setDateOfReservationEnd(1600000000L);

    spy.addQuestWithTags(questDTO);
  }

  @Test
  public void getQuestWithTagsById_MethodCalled() {
    questService.getQuest(ID);

    verify(questRepository, times(1)).getById(Quest.class, ID);
  }

  @Test
  public void getQuestsWithTagsBySearchCriteria_ReturnPaginationWithEmptyList_IfZeroQuestCount() {
    QuestSearchCriteria criteria = new QuestSearchCriteria();
    Long totalQuestCount = 0L;
    when(questRepository.count(any(CountQuestsbySearchCriteriaSpecification.class))).thenReturn(totalQuestCount);
    Pagination<QuestDTO> expectedPagination =
        new Pagination<>(Collections.emptyList(), totalQuestCount);

    Pagination<QuestDTO> actualPagination =
        questService.getQuestsBySearchCriteria(criteria);

    assertEquals(expectedPagination, actualPagination);
  }

  @Test
  public void getQuestsWithTagsBySearchCriteria_ReturnPaginationWithList_IfNotZeroQuestCount() {
    QuestSearchCriteria criteria = new QuestSearchCriteria();
    Long totalQuestCount = 1L;
    when(questRepository.count(any(CountQuestsbySearchCriteriaSpecification.class))).thenReturn(totalQuestCount);
    when(questRepository.getForPage(
            any(GetQuestsBySearchCriteriaSpecification.class), any(PaginationCriteria.class)))
        .thenReturn(quests);
    Pagination<QuestDTO> expectedPagination = new Pagination<>(questsDTO, totalQuestCount);

    Pagination<QuestDTO> actualPagination =
        questService.getQuestsBySearchCriteria(criteria);

    assertEquals(expectedPagination, actualPagination);
  }


  @Test
  public void isQuestIdExists_MethodCalled() {
    questService.isQuestIdExists(ID);

    verify(questRepository, times(1)).isExists(any(IsQuestIdExistsSpecification.class));
  }


  @Ignore
  @Test
  public void deleteQuestById_MethodCalled() {
    questService.deleteQuestById(ID, ID);

    verify(questRepository, times(1)).deleteById(Quest.class, ID);
  }
}
