package com.epam.esm.task5.service.dto.reservation;

import java.time.LocalTime;
import java.util.Objects;

public class TimeSlot {

  private Integer slotId;
  private Boolean isReserved;
  private LocalTime timeStart;
  private LocalTime timeEnd;

  public Integer getSlotId() {
    return slotId;
  }

  public void setSlotId(Integer slotId) {
    this.slotId = slotId;
  }

  public Boolean getIsReserved() {
    return isReserved;
  }

  public void setIsReserved(Boolean isReserved) {
    this.isReserved = isReserved;
  }

  public LocalTime getTimeStart() {
    return timeStart;
  }

  public void setTimeStart(LocalTime timeStart) {
    this.timeStart = timeStart;
  }

  public LocalTime getTimeEnd() {
    return timeEnd;
  }

  public void setTimeEnd(LocalTime timeEnd) {
    this.timeEnd = timeEnd;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TimeSlot timeSlot = (TimeSlot) o;
    return Objects.equals(slotId, timeSlot.slotId)
        && Objects.equals(isReserved, timeSlot.isReserved)
        && Objects.equals(timeStart, timeSlot.timeStart)
        && Objects.equals(timeEnd, timeSlot.timeEnd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(slotId, isReserved, timeStart, timeEnd);
  }

  @Override
  public String toString() {
    return "TimeSlot{"
        + "slotId="
        + slotId
        + ", isReserved="
        + isReserved
        + ", timeStart="
        + timeStart
        + ", timeEnd="
        + timeEnd
        + '}';
  }
}
