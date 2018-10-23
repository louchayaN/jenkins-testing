package com.epam.esm.task5.repository.specification.quest;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class IsQuestIdExistsSpecification implements Specification {

  private static final String IS_QUEST_ID_EXISTS = "select count(q.id) from Quest q where id= :id";

  private Long id;

  public IsQuestIdExistsSpecification(Long id) {
    this.id = id;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(IS_QUEST_ID_EXISTS).setParameter("id", id);
  }
}
