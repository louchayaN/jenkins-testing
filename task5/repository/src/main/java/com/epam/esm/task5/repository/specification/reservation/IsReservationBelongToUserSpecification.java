package com.epam.esm.task5.repository.specification.reservation;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class IsReservationBelongToUserSpecification implements Specification {

  private static final String IS_RESERVATION_BELONG_TO_USER =
      "SELECT COUNT(r.id) FROM Reservation r WHERE reservationId=:reservationId "
          + "AND user.id = :userId";

  private Long reservationId;
  private Long userId;

  public IsReservationBelongToUserSpecification(Long reservationId, Long userId) {
    this.reservationId = reservationId;
    this.userId = userId;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager
        .createQuery(IS_RESERVATION_BELONG_TO_USER)
        .setParameter("reservationId", reservationId)
        .setParameter("userId", userId);
  }
}
