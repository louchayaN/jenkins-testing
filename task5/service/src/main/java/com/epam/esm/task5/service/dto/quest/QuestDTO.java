package com.epam.esm.task5.service.dto.quest;

import com.epam.esm.task5.service.dto.tag.TagDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class QuestDTO {

    @Null(message = "{quest.id.null}")
    private Long id;

    private Long ownerId;

    @NotBlank(message = "{quest.name.nonblank}")
    @Length(min = 2, max = 50, message = "{quest.name.length}")
    @JsonProperty(required = true)
    private String name;

    @NotBlank(message = "{quest.description.nonblank}")
    @Length(min = 2, max = 10000, message = "{quest.description.length}")
    private String description;

    @NotBlank(message = "{quest.genre.nonblank}")
    @Length(min = 2, max = 50, message = "{quest.genre.length}")
    private String genre;

    @NotNull(message = "{quest.price.nonNull}")
    @PositiveOrZero(message = "{quest.price.positiveOrNull}")
    @Digits(integer = 50, fraction = 2, message = "{quest.price.twoDigitsPrecision}")
    private BigDecimal price;

    @NotNull(message = "{quest.duration.nonNull}")
    @Positive(message = "{quest.duration.positive}")
    @Max(value = 12 * 60, message = "{quest.duration.max}")
    private Integer duration;

    private Long dateOfCreation;

    private Long dateOfModification;

    @NotNull(message = "{quest.maxNumberOfPeople.nonNull}")
    @Positive(message = "{quest.maxNumberOfPeople.positive}")
    @Max(value = 30000, message = "{quest.maxNumberOfPeople.maxNumber}")
    private Integer maxNumberOfPeople;

    @NotNull(message = "{quest.reservation.futureDate}")
    private Long dateOfReservationStart;

    @NotNull(message = "{quest.reservation.futureDate}")
    private Long dateOfReservationEnd;

    @Valid
    private List<TagDTO> tags;

    public static class Builder {

        private Long id;
        private Long ownerId;
        private String name;
        private String description;
        private String genre;
        private BigDecimal price;
        private Integer duration;
        private Long dateOfCreation;
        private Long dateOfModification;
        private Integer maxNumberOfPeople;
        private Long dateOfReservationStart;
        private Long dateOfReservationEnd;
        private List<TagDTO> tags;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder ownerId(Long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public Builder dateOfCreation(Long dateOfCreation) {
            this.dateOfCreation = dateOfCreation;
            return this;
        }

        public Builder dateOfModification(Long dateOfModification) {
            this.dateOfModification = dateOfModification;
            return this;
        }

        public Builder maxNumberOfPeople(Integer maxNumberOfPeople) {
            this.maxNumberOfPeople = maxNumberOfPeople;
            return this;
        }

        public Builder dateOfReservationStart(Long dateOfReservationStart) {
            this.dateOfReservationStart = dateOfReservationStart;
            return this;
        }

        public Builder dateOfReservationEnd(Long dateOfReservationEnd) {
            this.dateOfReservationEnd = dateOfReservationEnd;
            return this;
        }

        public Builder tags(List<TagDTO> tags) {
            this.tags = tags;
            return this;
        }

        public QuestDTO build() {
            return new QuestDTO(this);
        }
    }

    private QuestDTO(Builder builder) {
        id = builder.id;
        ownerId = builder.ownerId;
        name = builder.name;
        description = builder.description;
        genre = builder.genre;
        price = builder.price;
        duration = builder.duration;
        dateOfCreation = builder.dateOfCreation;
        dateOfModification = builder.dateOfModification;
        maxNumberOfPeople = builder.maxNumberOfPeople;
        dateOfReservationStart = builder.dateOfReservationStart;
        dateOfReservationEnd = builder.dateOfReservationEnd;
        tags = builder.tags;
    }

    public QuestDTO() {
    }

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

    public Long getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Long dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Long getDateOfModification() {
        return dateOfModification;
    }

    public void setDateOfModification(Long dateOfModification) {
        this.dateOfModification = dateOfModification;
    }

    public Integer getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(Integer maxNumberOfPeople) {
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public Long getDateOfReservationStart() {
        return dateOfReservationStart;
    }

    public void setDateOfReservationStart(Long dateOfReservationStart) {
        this.dateOfReservationStart = dateOfReservationStart;
    }

    public Long getDateOfReservationEnd() {
        return dateOfReservationEnd;
    }

    public void setDateOfReservationEnd(Long dateOfReservationEnd) {
        this.dateOfReservationEnd = dateOfReservationEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestDTO questDTO = (QuestDTO) o;
        return Objects.equals(id, questDTO.id)
                && Objects.equals(ownerId, questDTO.ownerId)
                && Objects.equals(name, questDTO.name)
                && Objects.equals(description, questDTO.description)
                && Objects.equals(genre, questDTO.genre)
                && Objects.equals(price, questDTO.price)
                && Objects.equals(duration, questDTO.duration)
                && Objects.equals(dateOfCreation, questDTO.dateOfCreation)
                && Objects.equals(dateOfModification, questDTO.dateOfModification)
                && Objects.equals(maxNumberOfPeople, questDTO.maxNumberOfPeople)
                && Objects.equals(dateOfReservationStart, questDTO.dateOfReservationStart)
                && Objects.equals(dateOfReservationEnd, questDTO.dateOfReservationEnd)
                && Objects.equals(tags, questDTO.tags);
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
        return "QuestDTO{"
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
