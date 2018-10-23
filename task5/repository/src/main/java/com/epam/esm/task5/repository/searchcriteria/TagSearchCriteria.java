package com.epam.esm.task5.repository.searchcriteria;

import java.util.Objects;

public class TagSearchCriteria extends PaginationCriteria {

  private String top;

  public String getTop() {
    return top;
  }

  public void setTop(String top) {
    this.top = top;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    TagSearchCriteria that = (TagSearchCriteria) o;
    return Objects.equals(top, that.top);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), top);
  }

  @Override
  public String toString() {
    return "TagSearchCriteria{" + "top='" + top + '\'' + '}';
  }
}
