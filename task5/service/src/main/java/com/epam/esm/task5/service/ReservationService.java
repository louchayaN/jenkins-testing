package com.epam.esm.task5.service;

import com.epam.esm.task5.repository.entity.Reservation;
import com.epam.esm.task5.repository.searchcriteria.ReservationSearchCriteria;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.reservation.ReservationDTO;

import java.util.List;

public interface ReservationService {

  ReservationDTO addReservation(ReservationDTO reservationDTO);

  ReservationDTO getReservation(Long reservationId);

  Pagination<ReservationDTO> getAllReservations(ReservationSearchCriteria criteria);

  Pagination<ReservationDTO> getUserReservations(ReservationSearchCriteria criteria, Long userId);

  Pagination<ReservationDTO> getOwnerReservations(ReservationSearchCriteria criteria, Long ownerId);

  List<Integer> getQuestReservedSlotIdsForDay(Long questId, Long date);

  boolean isQuestReserved(Long questId);

  boolean isQuestReservedOnThisTime(Reservation reservation);

  boolean isReservationBelongToUser(Long reservationId, Long userId);

  boolean isReservationBelongToOwner(Long reservationId, Long userId);

  boolean isReservationExists(Long reservationId);

  void deleteReservationById(Long reservationId, Long userId);

}
