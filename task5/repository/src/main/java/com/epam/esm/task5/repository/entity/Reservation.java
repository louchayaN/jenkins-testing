package com.epam.esm.task5.repository.entity;

import com.epam.esm.task5.repository.audit.listener.ReservationEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@EntityListeners(ReservationEntityListener.class)
public class Reservation implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_reserv")
  private Long reservationId;

  @ManyToOne
  @JoinColumn(name = "id_user")
  private User user;

  @ManyToOne
  @JoinColumn(name = "id_quest")
  private Quest quest;

  private BigDecimal cost;
  private Integer slotId;
  private Instant reservedDay;
  private Instant purchaseDate;

  public Long getReservationId() {
    return reservationId;
  }

  public void setReservationId(Long reservationId) {
    this.reservationId = reservationId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Quest getQuest() {
    return quest;
  }

  public void setQuest(Quest quest) {
    this.quest = quest;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public Integer getSlotId() {
    return slotId;
  }

  public void setSlotId(Integer slotId) {
    this.slotId = slotId;
  }

  public Instant getReservedDay() {
    return reservedDay;
  }

  public void setReservedDay(Instant reservedDay) {
    this.reservedDay = reservedDay;
  }

  public Instant getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Instant purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Reservation that = (Reservation) o;
    return Objects.equals(reservationId, that.reservationId)
        && Objects.equals(user, that.user)
        && Objects.equals(quest, that.quest)
        && Objects.equals(cost, that.cost)
        && Objects.equals(slotId, that.slotId)
        && Objects.equals(reservedDay, that.reservedDay)
        && Objects.equals(purchaseDate, that.purchaseDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reservationId, user, quest, cost, slotId, reservedDay, purchaseDate);
  }

  @Override
  public String toString() {
    return "Reservation{"
        + "reservationId="
        + reservationId
        + ", user="
        + user
        + ", quest="
        + quest
        + ", cost="
        + cost
        + ", slotId="
        + slotId
        + ", reservedDay="
        + reservedDay
        + ", purchaseDate="
        + purchaseDate
        + '}';
  }
}
