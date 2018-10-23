package com.epam.esm.task5.service.dto;

import java.util.List;
import java.util.Objects;

public class Pagination<T> {

  private List<T> items;
  private Long totalItemsQuantity;

  public Pagination() {}

  public Pagination(List<T> items, Long totalItemsQuantity) {
    this.items = items;
    this.totalItemsQuantity = totalItemsQuantity;
  }

  public List<T> getItems() {
    return items;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }

  public Long getTotalItemsQuantity() {
    return totalItemsQuantity;
  }

  public void setTotalItemsQuantity(Long totalItemsQuantity) {
    this.totalItemsQuantity = totalItemsQuantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pagination<?> that = (Pagination<?>) o;
    return Objects.equals(items, that.items)
        && Objects.equals(totalItemsQuantity, that.totalItemsQuantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, totalItemsQuantity);
  }

  @Override
  public String toString() {
    return "PaginationCriteria{" + "items=" + items + ", totalItemsQuantity=" + totalItemsQuantity + '}';
  }
}
