package com.epam.esm.task5.repository.specification.quest;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CountAllQuestsSpecification implements Specification {

  private static final String COUNT_ALL_QUESTS = "SELECT COUNT(q) FROM Quest q";

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(COUNT_ALL_QUESTS);
  }
}
