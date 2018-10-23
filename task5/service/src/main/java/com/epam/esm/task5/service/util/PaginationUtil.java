package com.epam.esm.task5.service.util;

import com.epam.esm.task5.repository.searchcriteria.PaginationCriteria;

public class PaginationUtil {

  private static final String DEFAULT_PAGE = "1";
  private static final String DEFAULT_ENTITIES_PER_PAGE = "10";

  private PaginationUtil() {
    throw new AssertionError("Class contains static methods only");
  }

  public static PaginationCriteria setDefaultPagesIfNotValid(
      PaginationCriteria paginationCriteria) {

    String currentPage = paginationCriteria.getPage();
    String entitiesPerPage = paginationCriteria.getRows();

    if (currentPage == null) {
      currentPage = DEFAULT_PAGE;
      paginationCriteria.setPage(currentPage);
    }
    if (entitiesPerPage == null) {
      entitiesPerPage = DEFAULT_ENTITIES_PER_PAGE;
      paginationCriteria.setRows(entitiesPerPage);
    }

    return paginationCriteria;
  }
}
