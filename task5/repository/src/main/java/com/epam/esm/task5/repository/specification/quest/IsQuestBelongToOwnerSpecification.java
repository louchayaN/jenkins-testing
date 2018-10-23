package com.epam.esm.task5.repository.specification.quest;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class IsQuestBelongToOwnerSpecification implements Specification {

  private static final String IS_QUEST_BELONG_TO_OWNER =
      "select count(q.id) from Quest q where id= :id and ownerId=:ownerId";

  private Long questId;
  private Long ownerId;

  public IsQuestBelongToOwnerSpecification(Long questId, Long ownerId) {
    this.questId = questId;
    this.ownerId = ownerId;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager
        .createQuery(IS_QUEST_BELONG_TO_OWNER)
        .setParameter("id", questId)
        .setParameter("ownerId", ownerId);
  }
}
