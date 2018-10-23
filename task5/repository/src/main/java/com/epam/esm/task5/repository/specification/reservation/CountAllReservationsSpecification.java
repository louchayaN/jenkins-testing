package com.epam.esm.task5.repository.specification.reservation;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CountAllReservationsSpecification implements Specification {

  private static final String COUNT_ALL_RESERVATIONS = "SELECT COUNT(r) FROM Reservation r";

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(COUNT_ALL_RESERVATIONS);
  }
}
