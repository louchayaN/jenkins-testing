package com.epam.esm.task5.repository.specification.tag;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GetTagByNameSpecification implements Specification {

  private static final String GET_TAG_BY_NAME = "select t from Tag t where t_name = :name";

  private String name;

  public GetTagByNameSpecification(String name) {
    this.name = name;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(GET_TAG_BY_NAME).setParameter("name", name);
  }
}
