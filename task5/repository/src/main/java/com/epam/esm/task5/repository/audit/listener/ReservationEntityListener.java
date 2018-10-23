package com.epam.esm.task5.repository.audit.listener;

import com.epam.esm.task5.repository.audit.Action;
import com.epam.esm.task5.repository.audit.entity.ReservationAuditLog;
import com.epam.esm.task5.repository.entity.Reservation;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class ReservationEntityListener extends EntityListener {

  @PostPersist
  public void postPersist(Reservation reservation) {
    perform(reservation, Action.INSERTED);
  }

  @PreUpdate
  public void preUpdate(Reservation reservation) {
    perform(reservation, Action.UPDATED);
  }

  @PreRemove
  public void preRemove(Reservation reservation) {
    perform(reservation, Action.DELETED);
  }

  private void perform(Reservation reservation, Action action) {
    logRepository.save(new ReservationAuditLog(reservation, action));
  }
}
