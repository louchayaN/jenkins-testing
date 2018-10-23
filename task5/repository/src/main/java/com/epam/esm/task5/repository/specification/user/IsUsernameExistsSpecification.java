package com.epam.esm.task5.repository.specification.user;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class IsUsernameExistsSpecification implements Specification {

  private static final String IS_USERNAME_EXISTS =
      "select count(u.id) from User u where username = :username";

  private String username;

  public IsUsernameExistsSpecification(String username) {
    this.username = username;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(IS_USERNAME_EXISTS).setParameter("username", this.username);
  }
}
