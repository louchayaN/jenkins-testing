package com.epam.esm.task5.repository.specification.reservation;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CountOwnerReservationsSpecification implements Specification {

  private static final String COUNT_ALL_RESERVATIONS =
      "SELECT COUNT(r) FROM Reservation r WHERE quest.ownerId = :ownerId";

  private Long ownerId;

  public CountOwnerReservationsSpecification(Long ownerId) {
    this.ownerId = ownerId;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createQuery(COUNT_ALL_RESERVATIONS).setParameter("ownerId", ownerId);
  }
}
