package com.epam.esm.task5.repository.entity;

import com.epam.esm.task5.repository.audit.listener.QuestEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@EntityListeners(QuestEntityListener.class)
public class Quest implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_quest")
  private Long id;

  @Column(name = "id_owner")
  private Long ownerId;

  @Column(name = "q_name")
  private String name;

  private String description;
  private String genre;
  private BigDecimal price;
  private Integer duration;
  private Instant dateOfCreation;
  private Instant dateOfModification;
  private Integer maxNumberOfPeople;
  private Instant dateOfReservationStart;
  private Instant dateOfReservationEnd;

  @ManyToMany(cascade = {CascadeType.MERGE})
  @JoinTable(
      name = "tag_history",
      joinColumns = {@JoinColumn(name = "id_quest")},
      inverseJoinColumns = {@JoinColumn(name = "id_tag")})
  private Set<Tag> tags = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public Instant getDateOfCreation() {
    return dateOfCreation;
  }

  public void setDateOfCreation(Instant dateOfCreation) {
    this.dateOfCreation = dateOfCreation;
  }

  public Instant getDateOfModification() {
    return dateOfModification;
  }

  public void setDateOfModification(Instant dateOfModification) {
    this.dateOfModification = dateOfModification;
  }

  public Integer getMaxNumberOfPeople() {
    return maxNumberOfPeople;
  }

  public void setMaxNumberOfPeople(Integer maxNumberOfPeople) {
    this.maxNumberOfPeople = maxNumberOfPeople;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  public Instant getDateOfReservationStart() {
    return dateOfReservationStart;
  }

  public void setDateOfReservationStart(Instant dateOfReservationStart) {
    this.dateOfReservationStart = dateOfReservationStart;
  }

  public Instant getDateOfReservationEnd() {
    return dateOfReservationEnd;
  }

  public void setDateOfReservationEnd(Instant dateOfReservationEnd) {
    this.dateOfReservationEnd = dateOfReservationEnd;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Quest quest = (Quest) o;
    return Objects.equals(id, quest.id)
        && Objects.equals(ownerId, quest.ownerId)
        && Objects.equals(name, quest.name)
        && Objects.equals(description, quest.description)
        && Objects.equals(genre, quest.genre)
        && Objects.equals(price, quest.price)
        && Objects.equals(duration, quest.duration)
        && Objects.equals(dateOfCreation, quest.dateOfCreation)
        && Objects.equals(dateOfModification, quest.dateOfModification)
        && Objects.equals(maxNumberOfPeople, quest.maxNumberOfPeople)
        && Objects.equals(dateOfReservationStart, quest.dateOfReservationStart)
        && Objects.equals(dateOfReservationEnd, quest.dateOfReservationEnd)
        && Objects.equals(tags, quest.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        ownerId,
        name,
        description,
        genre,
        price,
        duration,
        dateOfCreation,
        dateOfModification,
        maxNumberOfPeople,
        dateOfReservationStart,
        dateOfReservationEnd,
        tags);
  }

  @Override
  public String toString() {
    return "Quest{"
        + "id="
        + id
        + ", ownerId="
        + ownerId
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", genre='"
        + genre
        + '\''
        + ", price="
        + price
        + ", duration="
        + duration
        + ", dateOfCreation="
        + dateOfCreation
        + ", dateOfModification="
        + dateOfModification
        + ", maxNumberOfPeople="
        + maxNumberOfPeople
        + ", dateOfReservationStart="
        + dateOfReservationStart
        + ", dateOfReservationEnd="
        + dateOfReservationEnd
        + ", tags="
        + tags
        + '}';
  }
}
