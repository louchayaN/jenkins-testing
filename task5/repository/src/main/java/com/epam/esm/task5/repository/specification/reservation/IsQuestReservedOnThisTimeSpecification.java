package com.epam.esm.task5.repository.specification.reservation;

import com.epam.esm.task5.repository.entity.Reservation;
import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class IsQuestReservedOnThisTimeSpecification implements Specification {

  private static final String IS_QUEST_RESERVED_THIS_TIME =
      "SELECT COUNT(r.id) FROM Reservation r WHERE quest.id = :questId AND slotId = :slotId AND reservedDay = :reservedDay";

  private Reservation reservation;

  public IsQuestReservedOnThisTimeSpecification(Reservation reservation) {
    this.reservation = reservation;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager
        .createQuery(IS_QUEST_RESERVED_THIS_TIME)
        .setParameter("questId", reservation.getQuest().getId())
        .setParameter("slotId", reservation.getSlotId())
        .setParameter("reservedDay", reservation.getReservedDay());
  }
}
