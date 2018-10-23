package com.epam.esm.task5.repository.specification.tag;

import com.epam.esm.task5.repository.entity.Tag;
import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GetAllTagsSpecification implements Specification {

  private static final String GET_TAGS_FOR_PAGE = "select t from Tag t";

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(GET_TAGS_FOR_PAGE, Tag.class);
  }
}
