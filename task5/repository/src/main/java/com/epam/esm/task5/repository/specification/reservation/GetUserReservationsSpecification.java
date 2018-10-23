package com.epam.esm.task5.repository.specification.reservation;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GetUserReservationsSpecification implements Specification {

  private static final String GET_USER_RESERVATIONS =
      "select r from Reservation r where user.id = :userId ORDER BY r.purchaseDate DESC";

  private Long userId;

  public GetUserReservationsSpecification(Long userId) {
    this.userId = userId;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(GET_USER_RESERVATIONS).setParameter("userId", userId);
  }
}
