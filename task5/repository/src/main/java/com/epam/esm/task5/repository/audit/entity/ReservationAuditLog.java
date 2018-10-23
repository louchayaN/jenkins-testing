package com.epam.esm.task5.repository.audit.entity;

import com.epam.esm.task5.repository.audit.Action;
import com.epam.esm.task5.repository.entity.Reservation;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class ReservationAuditLog extends AuditLog implements Serializable {

  private Long idReservation;

  private String reservationContent;

  public ReservationAuditLog() {}

  public ReservationAuditLog(Reservation reservation, Action action) {
    super(action);
    this.idReservation = reservation.getReservationId();
    this.reservationContent = reservation.toString();
  }

  public Long getIdReservation() {
    return idReservation;
  }

  public void setIdReservation(Long idReservation) {
    this.idReservation = idReservation;
  }

  public String getReservationContent() {
    return reservationContent;
  }

  public void setReservationContent(String reservationContent) {
    this.reservationContent = reservationContent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ReservationAuditLog that = (ReservationAuditLog) o;
    return Objects.equals(idReservation, that.idReservation)
        && Objects.equals(reservationContent, that.reservationContent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idReservation, reservationContent);
  }

  @Override
  public String toString() {
    return "ReservationAuditLog{"
        + "idReservation="
        + idReservation
        + ", reservationContent='"
        + reservationContent
        + '\''
        + '}';
  }
}
