package com.epam.esm.task5.repository;

import com.epam.esm.task5.repository.entity.Reservation;

import java.time.Instant;
import java.util.List;

public interface ReservationRepository extends BasicRepository<Reservation> {

    List<Integer> getQuestReservedSlotIdsForDay(Long questId, Instant date);
}
