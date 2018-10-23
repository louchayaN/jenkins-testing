package com.epam.esm.task5.repository.searchcriteria;

import com.epam.esm.task5.repository.searchcriteria.validation.PositiveNumber;
import com.epam.esm.task5.repository.searchcriteria.validation.PositiveOrZeroNumber;

import java.util.List;
import java.util.Objects;

public class QuestSearchCriteria extends PaginationCriteria {

  @PositiveNumber(message = "{questSearchCriteria.owner.positive}")
  private String owner;

  private String name;
  private String description;

  @PositiveOrZeroNumber(message = "{questSearchCriteria.price.positiveOrZero}")
  private String startPrice;

  @PositiveOrZeroNumber(message = "{questSearchCriteria.price.positiveOrZero}")
  private String endPrice;

  private List<String> tags;
  private List<String> sorts;

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
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

  public String getStartPrice() {
    return startPrice;
  }

  public void setStartPrice(String startPrice) {
    this.startPrice = startPrice;
  }

  public String getEndPrice() {
    return endPrice;
  }

  public void setEndPrice(String endPrice) {
    this.endPrice = endPrice;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public List<String> getSorts() {
    return sorts;
  }

  public void setSorts(List<String> sorts) {
    this.sorts = sorts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    QuestSearchCriteria that = (QuestSearchCriteria) o;
    return Objects.equals(owner, that.owner)
            && Objects.equals(name, that.name)
            && Objects.equals(description, that.description)
            && Objects.equals(startPrice, that.startPrice)
            && Objects.equals(endPrice, that.endPrice)
            && Objects.equals(tags, that.tags)
            && Objects.equals(sorts, that.sorts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
            super.hashCode(), owner, name, description, startPrice, endPrice, tags, sorts);
  }

  @Override
  public String toString() {
    return "QuestSearchCriteria{"
            + "owner='"
            + owner
            + '\''
            + ", name='"
            + name
            + '\''
            + ", description='"
            + description
            + '\''
            + ", startPrice='"
            + startPrice
            + '\''
            + ", endPrice='"
            + endPrice
            + '\''
            + ", tags="
            + tags
            + ", sorts="
            + sorts
            + '}';
  }
}
