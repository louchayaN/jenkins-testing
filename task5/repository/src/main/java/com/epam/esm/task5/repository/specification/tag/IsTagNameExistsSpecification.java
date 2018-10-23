package com.epam.esm.task5.repository.specification.tag;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class IsTagNameExistsSpecification implements Specification {

  private static final String IS_TAG_NAME_EXISTS =
      "select count(t.id) from Tag t where t_name = :name";

  private String name;

  public IsTagNameExistsSpecification(String name) {
    this.name = name;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(IS_TAG_NAME_EXISTS).setParameter("name", name);
  }
}
