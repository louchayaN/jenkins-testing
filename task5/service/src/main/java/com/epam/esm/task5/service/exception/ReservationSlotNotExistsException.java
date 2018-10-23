package com.epam.esm.task5.service.exception;

public class ReservationSlotNotExistsException extends RuntimeException {

  private final Integer slotId;

  public ReservationSlotNotExistsException(Integer slotId) {
    this.slotId = slotId;
  }

  public Integer getSlotId() {
    return slotId;
  }
}
