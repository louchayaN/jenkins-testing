package com.epam.esm.task5.service.dto.quest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class ReservedQuestDTO {

  @NotNull(message = "{reservation.questId.notnull}")
  @Positive(message = "{reservation.questId.positive}")
  private Long questId;

  private String name;

  private String description;

  private String genre;

  private Integer maxNumberOfPeople;

  private Integer duration;

  public Long getQuestId() {
    return questId;
  }

  public void setQuestId(Long questId) {
    this.questId = questId;
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

  public Integer getMaxNumberOfPeople() {
    return maxNumberOfPeople;
  }

  public void setMaxNumberOfPeople(Integer maxNumberOfPeople) {
    this.maxNumberOfPeople = maxNumberOfPeople;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ReservedQuestDTO that = (ReservedQuestDTO) o;
    return Objects.equals(questId, that.questId)
        && Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(genre, that.genre)
        && Objects.equals(maxNumberOfPeople, that.maxNumberOfPeople)
        && Objects.equals(duration, that.duration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(questId, name, description, genre, maxNumberOfPeople, duration);
  }

  @Override
  public String toString() {
    return "ReservedQuestDTO{"
        + "questId="
        + questId
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", genre='"
        + genre
        + '\''
        + ", maxNumberOfPeople="
        + maxNumberOfPeople
        + ", duration="
        + duration
        + '}';
  }
}
