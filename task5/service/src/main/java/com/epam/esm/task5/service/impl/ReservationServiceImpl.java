package com.epam.esm.task5.service.impl;

import com.epam.esm.task5.repository.QuestRepository;
import com.epam.esm.task5.repository.ReservationRepository;
import com.epam.esm.task5.repository.entity.Quest;
import com.epam.esm.task5.repository.entity.Reservation;
import com.epam.esm.task5.repository.searchcriteria.PaginationCriteria;
import com.epam.esm.task5.repository.searchcriteria.ReservationSearchCriteria;
import com.epam.esm.task5.repository.specification.Specification;
import com.epam.esm.task5.repository.specification.reservation.*;
import com.epam.esm.task5.service.ReservationService;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.reservation.ReservationDTO;
import com.epam.esm.task5.service.exception.*;
import com.epam.esm.task5.service.util.PaginationUtil;
import com.epam.esm.task5.service.util.TimeSlotUtil;
import com.epam.esm.task5.service.util.mapper.ReservationModelMapper;
import com.epam.esm.task5.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;

    private QuestRepository questRepository;

    @Autowired
    public ReservationServiceImpl(
            ReservationRepository reservationRepository, QuestRepository questRepository) {
        this.reservationRepository = reservationRepository;
        this.questRepository = questRepository;
    }

    @Override
    public ReservationDTO addReservation(ReservationDTO reservationDTO) {
        Reservation reservation = ReservationModelMapper.mapToReservation(reservationDTO);
        Quest quest = questRepository.getById(Quest.class, reservation.getQuest().getId());
        if (quest == null) {
            throw new QuestNotFoundException(reservation.getQuest().getId());
        }

        if (!isQuestAvailableThisDay(quest, reservation.getReservedDay())) {
            throw new QuestNotAvailableThisDateException("Quest reservation disabled this date");
        }

        if (!TimeSlotUtil.isTimeSlotExistsForQuest(quest.getDuration(), reservation.getSlotId())) {
            throw new ReservationSlotNotExistsException(reservation.getSlotId());
        }

        if (isQuestReservedOnThisTime(reservation)) {
            throw new QuestReservedThisTimeException();
        }

        reservation.setCost(quest.getPrice());
        reservation.setPurchaseDate(Instant.now());

        return ReservationModelMapper.mapToReservationDTO(reservationRepository.save(reservation));
    }

    private boolean isQuestAvailableThisDay(Quest quest, Instant wishedReservationDate) {
        Instant questAvailableFrom = quest.getDateOfReservationStart();
        Instant questAvailableTill = quest.getDateOfReservationEnd();

        if (wishedReservationDate.isBefore(questAvailableFrom)) {
            return false;
        }

        return wishedReservationDate.isBefore(questAvailableTill) || wishedReservationDate.equals(questAvailableTill);
    }

    @Override
    public ReservationDTO getReservation(Long reservationId) {
        ReservationDTO reservationDTO =
                ReservationModelMapper.mapToReservationDTO(reservationRepository.getById(Reservation.class, reservationId));
        LocalTime reservationTimeStart = TimeSlotUtil.getReservationTimeStart(
                reservationDTO.getQuest().getDuration(), reservationDTO.getSlotId());
        reservationDTO.setTimeStart(reservationTimeStart);
        return reservationDTO;
    }

    @Override
    public Pagination<ReservationDTO> getAllReservations(ReservationSearchCriteria criteria) {
        return getReservations(
                new CountAllReservationsSpecification(), new GetAllReservationsSpecification(), criteria);
    }

    @Override
    public Pagination<ReservationDTO> getUserReservations(
            ReservationSearchCriteria criteria, Long userId) {
        return getReservations(
                new CountUserReservationsSpecification(userId), new GetUserReservationsSpecification(userId), criteria);
    }

    @Override
    public Pagination<ReservationDTO> getOwnerReservations(
            ReservationSearchCriteria criteria, Long ownerId) {
        return getReservations(
                new CountOwnerReservationsSpecification(ownerId), new GetOwnerReservationsSpecification(ownerId), criteria);
    }

    private Pagination<ReservationDTO> getReservations(
            Specification countSpecification, Specification getForPageSpecification, ReservationSearchCriteria criteria) {

        Long totalUserReservations =
                reservationRepository.count(countSpecification);
        if (totalUserReservations == 0L) {
            return new Pagination<>(Collections.emptyList(), totalUserReservations);
        }

        PaginationCriteria pagination = PaginationUtil.setDefaultPagesIfNotValid(criteria);

        List<Reservation> reservationsForPage =
                reservationRepository.getForPage(getForPageSpecification, pagination);

        List<ReservationDTO> reservationsDTOForPage =
                ReservationModelMapper.mapToReservationsDTO(reservationsForPage);
        reservationsDTOForPage.forEach(
                reservationDTO ->
                        reservationDTO.setTimeStart(
                                TimeSlotUtil.getReservationTimeStart(
                                        reservationDTO.getQuest().getDuration(), reservationDTO.getSlotId())));

        return new Pagination<>(reservationsDTOForPage, totalUserReservations);
    }

    @Override
    public List<Integer> getQuestReservedSlotIdsForDay(Long questId, Long date) {
        return reservationRepository.getQuestReservedSlotIdsForDay(
                questId, Instant.ofEpochSecond(date));
    }

    @Override
    public boolean isQuestReserved(Long questId) {
        return reservationRepository.isExists(new IsQuestReservedSpecification(questId));
    }

    @Override
    public boolean isQuestReservedOnThisTime(Reservation reservation) {
        return reservationRepository.isExists(new IsQuestReservedOnThisTimeSpecification(reservation));
    }

    @Override
    public boolean isReservationBelongToUser(Long reservationId, Long userId) {
        return reservationRepository.isExists(
                new IsReservationBelongToUserSpecification(reservationId, userId));
    }

    @Override
    public boolean isReservationBelongToOwner(Long reservationId, Long userId) {
        return reservationRepository.isExists(
                new IsReservationBelongToOwnerSpecification(reservationId, userId));
    }

    @Override
    public boolean isReservationExists(Long reservationId) {
        return reservationRepository.isExists(new IsReservationExistsSpecification(reservationId));
    }

    @Override
    public void deleteReservationById(Long reservationId, Long userId) {
        if (!isReservationExists(reservationId)) {
            return;
        }

        if (!isReservationBelongToUser(reservationId, userId)
                && !isReservationBelongToOwner(reservationId, userId)) {
            throw new ReservationOperationException();
        }

        ReservationDTO reservation = getReservation(reservationId);
        long reservedDay = reservation.getReservedDay();
        long reservedDate = reservedDay + reservation.getTimeStart().toSecondOfDay();
        long currentDate = Instant.now().getEpochSecond();
        if (currentDate > reservedDate) {
            throw new ReservationInThePastDeletionException();
        }

        reservationRepository.deleteById(Reservation.class, reservationId);
    }
}
