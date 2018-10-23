package com.epam.esm.task5.service.dto.reservation;

import com.epam.esm.task5.service.dto.quest.ReservedQuestDTO;
import com.epam.esm.task5.service.dto.user.UserInfoDTO;
import com.epam.esm.task5.service.dto.validation.FutureDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationDTO {

  @Null(message = "{reservation.reservationId.null}")
  private Long reservationId;

  private UserInfoDTO user;

  @NotNull @Valid private ReservedQuestDTO quest;

  @Null(message = "{reservation.cost.null}")
  private BigDecimal cost;

  @NotNull(message = "{reservation.startDate.notnull}")
  @Positive(message = "{reservation.startDate.futureDate}")
  @FutureDate(message = "{reservation.startDate.futureDate}")
  private Long reservedDay;

  @NotNull(message = "{reservation.slotId.notnull}")
  @Positive(message = "{reservation.slotId.positive}")
  private Integer slotId;

  private LocalTime timeStart;

  @Null(message = "{reservation.purchasedDate.null}")
  private Long purchaseDate;

  public Long getReservationId() {
    return reservationId;
  }

  public void setReservationId(Long reservationId) {
    this.reservationId = reservationId;
  }

  public UserInfoDTO getUser() {
    return user;
  }

  public void setUser(UserInfoDTO user) {
    this.user = user;
  }

  public ReservedQuestDTO getQuest() {
    return quest;
  }

  public void setQuest(ReservedQuestDTO quest) {
    this.quest = quest;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public Long getReservedDay() {
    return reservedDay;
  }

  public void setReservedDay(Long reservedDay) {
    this.reservedDay = reservedDay;
  }

  public Integer getSlotId() {
    return slotId;
  }

  public void setSlotId(Integer slotId) {
    this.slotId = slotId;
  }

  public LocalTime getTimeStart() {
    return timeStart;
  }

  public void setTimeStart(LocalTime timeStart) {
    this.timeStart = timeStart;
  }

  public Long getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Long purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ReservationDTO that = (ReservationDTO) o;
    return Objects.equals(reservationId, that.reservationId)
        && Objects.equals(user, that.user)
        && Objects.equals(quest, that.quest)
        && Objects.equals(cost, that.cost)
        && Objects.equals(reservedDay, that.reservedDay)
        && Objects.equals(slotId, that.slotId)
        && Objects.equals(timeStart, that.timeStart)
        && Objects.equals(purchaseDate, that.purchaseDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        reservationId, user, quest, cost, reservedDay, slotId, timeStart, purchaseDate);
  }

  @Override
  public String toString() {
    return "ReservationDTO{"
        + "reservationId="
        + reservationId
        + ", user="
        + user
        + ", quest="
        + quest
        + ", cost="
        + cost
        + ", reservedDay="
        + reservedDay
        + ", slotId="
        + slotId
        + ", timeStart="
        + timeStart
        + ", purchaseDate="
        + purchaseDate
        + '}';
  }
}
