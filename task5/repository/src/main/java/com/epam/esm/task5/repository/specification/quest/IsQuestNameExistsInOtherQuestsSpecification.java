package com.epam.esm.task5.repository.specification.quest;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class IsQuestNameExistsInOtherQuestsSpecification implements Specification {

  private static final String IS_QUEST_NAME_EXISTS_IN_OTHER_QUESTS =
      "select count(q.id) from Quest q where q_name = :name and id_quest != :id";

  private String name;
  private Long currentQuestId;

  public IsQuestNameExistsInOtherQuestsSpecification(String name, Long currentQuestId) {
    this.name = name;
    this.currentQuestId = currentQuestId;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager
        .createQuery(IS_QUEST_NAME_EXISTS_IN_OTHER_QUESTS)
        .setParameter("name", name)
        .setParameter("id", currentQuestId);
  }
}
