package com.epam.esm.task5.repository.specification.reservation;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class IsQuestReservedSpecification implements Specification {

  private static final String IS_QUEST_RESERVED =
      "SELECT COUNT(r.id) FROM Reservation r WHERE quest.id = :questId";

  private Long questId;

  public IsQuestReservedSpecification(Long questId) {
    this.questId = questId;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(IS_QUEST_RESERVED).setParameter("questId", questId);
  }
}
