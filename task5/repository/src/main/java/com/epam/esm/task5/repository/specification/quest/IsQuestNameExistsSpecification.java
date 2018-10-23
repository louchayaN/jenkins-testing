package com.epam.esm.task5.repository.specification.quest;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class IsQuestNameExistsSpecification implements Specification {

  private static final String IS_QUEST_NAME_EXISTS =
      "select count(q.id) from Quest q where q_name = :name";

  private String name;

  public IsQuestNameExistsSpecification(String name) {
    this.name = name;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(IS_QUEST_NAME_EXISTS).setParameter("name", this.name);
  }
}
