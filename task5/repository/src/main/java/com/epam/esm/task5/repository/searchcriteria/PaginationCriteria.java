package com.epam.esm.task5.repository.searchcriteria;

import com.epam.esm.task5.repository.searchcriteria.validation.MaxNumber;
import com.epam.esm.task5.repository.searchcriteria.validation.PositiveNumber;

import java.util.Objects;

public class PaginationCriteria {

  @PositiveNumber(message = "{paginationCriteria.page.positive}")
  private String page;

  @PositiveNumber(message = "{paginationCriteria.rows.positive}")
  @MaxNumber(value = 100, message = "{paginationCriteria.rows.maxNumber}")
  private String rows;

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getRows() {
    return rows;
  }

  public void setRows(String rows) {
    this.rows = rows;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PaginationCriteria that = (PaginationCriteria) o;
    return Objects.equals(page, that.page) && Objects.equals(rows, that.rows);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, rows);
  }

  @Override
  public String toString() {
    return "PaginationCriteria{" + "page='" + page + '\'' + ", rows='" + rows + '\'' + '}';
  }
}
