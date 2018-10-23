package com.epam.esm.task5.repository.specification.user;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GetUserByUsernameSpecification implements Specification {

  private static final String GET_USER_BY_USERNAME =
      "select u from User u where username = :username";
  private String username;

  public GetUserByUsernameSpecification(String username) {
    this.username = username;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(GET_USER_BY_USERNAME).setParameter("username", username);
  }
}
