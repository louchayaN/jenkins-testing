package com.epam.esm.task5.repository.specification.reservation;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CountUserReservationsSpecification implements Specification {

  private static final String COUNT_USER_RESERVATIONS =
      "SELECT COUNT(r) FROM Reservation r WHERE user.id = :userId";

  private Long userId;

  public CountUserReservationsSpecification(Long userId) {
    this.userId = userId;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(COUNT_USER_RESERVATIONS).setParameter("userId", userId);
  }
}
