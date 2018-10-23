package com.epam.esm.task5.repository.specification.reservation;

import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class IsReservationBelongToOwnerSpecification implements Specification {

  private static final String IS_RESERVATION_BELONG_TO_OWNER =
      "SELECT COUNT(r.id) FROM Reservation r WHERE reservationId=:reservationId "
          + "AND quest.ownerId = :ownerId";

  private Long reservationId;
  private Long ownerId;

  public IsReservationBelongToOwnerSpecification(Long reservationId, Long ownerId) {
    this.reservationId = reservationId;
    this.ownerId = ownerId;
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager
        .createQuery(IS_RESERVATION_BELONG_TO_OWNER)
        .setParameter("reservationId", reservationId)
        .setParameter("ownerId", ownerId);
  }
}
