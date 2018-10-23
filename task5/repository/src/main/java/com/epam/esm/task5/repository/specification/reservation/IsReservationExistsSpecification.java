package com.epam.esm.task5.repository.specification.reservation;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class IsReservationExistsSpecification implements Specification {

  private static final String IS_RESERVATION_EXISTS =
      "SELECT COUNT(r.id) FROM Reservation r WHERE reservationId=:reservationId";

  private Long reservationId;

  public IsReservationExistsSpecification(Long reservationId) {
    this.reservationId = reservationId;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager
        .createQuery(IS_RESERVATION_EXISTS)
        .setParameter("reservationId", reservationId);
  }
}
