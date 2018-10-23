package com.epam.esm.task5.repository;

import com.epam.esm.task5.repository.searchcriteria.PaginationCriteria;
import com.epam.esm.task5.repository.specification.Specification;

import java.util.List;

public interface BasicRepository<T> {

  T save(T entity);

  void deleteById(Class<T> entityClass, Long entityId);

  T getById(Class<T> entityClass, Long entityId);

  T getOne(Specification specification);

  List<T> get(Specification specification);

  List<T> getForPage(Specification specification, PaginationCriteria pagination);

  boolean isExists(Specification specification);

  Long count(Specification specification);
}
