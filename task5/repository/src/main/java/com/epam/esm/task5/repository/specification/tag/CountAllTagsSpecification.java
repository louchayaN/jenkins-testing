package com.epam.esm.task5.repository.specification.tag;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CountAllTagsSpecification implements Specification {

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createNamedQuery("Tag.totalCount");
  }
}
