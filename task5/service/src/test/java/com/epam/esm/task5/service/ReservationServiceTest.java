package com.epam.esm.task5.service;

import com.epam.esm.task5.repository.QuestRepository;
import com.epam.esm.task5.repository.ReservationRepository;
import com.epam.esm.task5.repository.entity.Quest;
import com.epam.esm.task5.repository.entity.Reservation;
import com.epam.esm.task5.repository.searchcriteria.PaginationCriteria;
import com.epam.esm.task5.repository.searchcriteria.ReservationSearchCriteria;
import com.epam.esm.task5.repository.specification.quest.CountQuestsbySearchCriteriaSpecification;
import com.epam.esm.task5.repository.specification.quest.GetQuestsBySearchCriteriaSpecification;
import com.epam.esm.task5.repository.specification.reservation.IsQuestReservedOnThisTimeSpecification;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.reservation.ReservationDTO;
import com.epam.esm.task5.service.dto.quest.ReservedQuestDTO;
import com.epam.esm.task5.service.exception.QuestNotAvailableThisDateException;
import com.epam.esm.task5.service.exception.QuestNotFoundException;
import com.epam.esm.task5.service.exception.QuestReservedThisTimeException;
import com.epam.esm.task5.service.impl.ReservationServiceImpl;
import com.epam.esm.task5.service.util.mapper.ReservationModelMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

  @Mock private ReservationRepository reservationRepository;
  @Mock private QuestRepository questRepository;

  @InjectMocks private ReservationServiceImpl reservationService;

  private ReservationDTO reservationDTO;
  private List<ReservationDTO> reservationsDTO;

  private Reservation reservation;
  private List<Reservation> reservations;

  @Before
  public void doSetup() {
    MockitoAnnotations.initMocks(this);

    reservationDTO = new ReservationDTO();
    ReservedQuestDTO questDTO = new ReservedQuestDTO();
    questDTO.setQuestId(1L);
    reservationDTO.setQuest(questDTO);

    reservationsDTO = new ArrayList<>();
    reservationsDTO.add(reservationDTO);

    reservation = ReservationModelMapper.mapToReservation(reservationDTO);
    reservations = new ArrayList<>();
    reservations.add(reservation);
  }

  @Test(expected = QuestNotFoundException.class)
  public void addReservation_ThrowException_IfQuestByIdNotFound() {
    when(questRepository.getById(Quest.class, reservation.getQuest().getId())).thenReturn(null);

    reservationService.addReservation(reservationDTO);
  }

  @Ignore
  @Test(expected = QuestNotAvailableThisDateException.class)
  public void addReservation_ThrowException_IfQuestNotAvailableForReservationThisDate() {
    Quest quest = new Quest();
    quest.setDuration(45);
    quest.setPrice(new BigDecimal(15.45));
    quest.setDateOfReservationStart(Instant.ofEpochSecond(1700000000L));
    quest.setDateOfReservationEnd(Instant.ofEpochSecond(1800000000L));
    when(questRepository.getById(Quest.class, reservation.getQuest().getId())).thenReturn(quest);

    reservationService.addReservation(reservationDTO);
  }


  @Ignore
  @Test(expected = QuestReservedThisTimeException.class)
  public void addReservation_ThrowException_IfQuestReservedOnThisTime() {
    Quest quest = new Quest();
    quest.setDuration(45);
    quest.setPrice(new BigDecimal(15.45));
    quest.setDateOfReservationStart(Instant.ofEpochSecond(1500000000L));
    quest.setDateOfReservationEnd(Instant.ofEpochSecond(1800000000L));
    when(questRepository.getById(Quest.class, reservation.getQuest().getId())).thenReturn(quest);
    ReservationService spy = spy(reservationService);
    doReturn(true).when(spy).isQuestReservedOnThisTime(any(Reservation.class));

    spy.addReservation(reservationDTO);
  }

  @Test
  @Ignore
  public void getUserReservations_ReturnPaginationWithEmptyList_IfZeroReservationCount() {
    ReservationSearchCriteria criteria = new ReservationSearchCriteria();
    Long totalReservationCount = 0L;
    when(reservationRepository.count(any(CountQuestsbySearchCriteriaSpecification.class)))
        .thenReturn(totalReservationCount);
    Pagination<ReservationDTO> expectedPagination =
        new Pagination<>(Collections.emptyList(), totalReservationCount);

    Pagination<ReservationDTO> actualPagination =
        reservationService.getUserReservations(criteria, 1L);

    assertEquals(expectedPagination, actualPagination);
  }

  @Test
  @Ignore
  public void getUserReservations_ReturnPaginationWithList_IfNotZeroReservationCount() {
    ReservationSearchCriteria criteria = new ReservationSearchCriteria();
    Long totalReservationCount = 1L;
    when(reservationRepository.count(any(CountQuestsbySearchCriteriaSpecification.class)))
        .thenReturn(totalReservationCount);
    when(reservationRepository.getForPage(
            any(GetQuestsBySearchCriteriaSpecification.class), any(PaginationCriteria.class)))
        .thenReturn(reservations);
    Pagination<ReservationDTO> expectedPagination =
        new Pagination<>(reservationsDTO, totalReservationCount);

    Pagination<ReservationDTO> actualPagination =
        reservationService.getUserReservations(criteria, 1L);

    assertEquals(expectedPagination, actualPagination);
  }

    @Test
    public void isQuestReservedOnThisTime_MethodCalled() {
        reservationService.isQuestReservedOnThisTime(reservation);

        verify(reservationRepository, times(1)).isExists(any(IsQuestReservedOnThisTimeSpecification.class));
    }
}
