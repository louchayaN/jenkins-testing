package com.epam.esm.task5.repository.specification.reservation;

import com.epam.esm.task5.repository.entity.Reservation;
import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GetOwnerReservationsSpecification implements Specification {

  private static final String GET_RESERVATIONS_FOR_PAGE =
      "SELECT r FROM Reservation r WHERE quest.ownerId = :ownerId ORDER BY r.purchaseDate DESC";

  private Long ownerId;

  public GetOwnerReservationsSpecification(Long ownerId) {
    this.ownerId = ownerId;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager
        .createQuery(GET_RESERVATIONS_FOR_PAGE, Reservation.class)
        .setParameter("ownerId", ownerId);
  }
}
