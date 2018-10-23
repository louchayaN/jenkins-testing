package com.epam.esm.task5.repository.impl;

import com.epam.esm.task5.repository.BasicRepositoryImpl;
import com.epam.esm.task5.repository.ReservationRepository;
import com.epam.esm.task5.repository.entity.Reservation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.List;

@Repository
public class ReservationRepositoryImpl extends BasicRepositoryImpl<Reservation>
    implements ReservationRepository {

  @PersistenceContext private EntityManager entityManager;

  private static final String GET_RESERVED_QUEST_SLOTS =
      "SELECT r.slotId FROM Reservation r WHERE quest.id = :questId AND reservedDay = :reservedDay";

  @Override
  public List<Integer> getQuestReservedSlotIdsForDay(Long questId, Instant date) {
    return entityManager
        .createQuery(GET_RESERVED_QUEST_SLOTS, Integer.class)
        .setParameter("questId", questId)
        .setParameter("reservedDay", date)
        .getResultList();
  }
}
