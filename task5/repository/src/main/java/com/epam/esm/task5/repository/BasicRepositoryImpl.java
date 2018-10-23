package com.epam.esm.task5.repository;

import com.epam.esm.task5.repository.searchcriteria.PaginationCriteria;
import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

public class BasicRepositoryImpl<T> implements BasicRepository<T> {

  @PersistenceContext private EntityManager entityManager;

  @Override
  @Transactional
  public T save(T entity) {
    return entityManager.merge(entity);
  }

  @Override
  @Transactional
  public void deleteById(Class<T> entityClass, Long entityId) {
    T entity = entityManager.find(entityClass, entityId);
    if (entity != null) {
      entityManager.remove(entity);
    }
  }

  @Override
  public T getById(Class<T> entityClass, Long entityId) {
    return entityManager.find(entityClass, entityId);
  }

  @Override
  public T getOne(Specification specification) {
    List<T> foundEntities = get(specification);
    return foundEntities.isEmpty() ? null : foundEntities.get(0);
  }

  @Override
  public List<T> get(Specification specification) {
    return specification.toQuery(entityManager).getResultList();
  }

  @Override
  public List<T> getForPage(Specification specification, PaginationCriteria pagination) {
    Integer currentPage = Integer.valueOf(pagination.getPage());
    Integer entitiesPerPage = Integer.valueOf(pagination.getRows());

    Query query = specification.toQuery(entityManager);
    query.setFirstResult((currentPage - 1) * entitiesPerPage).setMaxResults(entitiesPerPage);
    return query.getResultList();
  }

  @Override
  public boolean isExists(Specification specification) {
    return 0L != count(specification);
  }

  @Override
  public Long count(Specification specification) {
    return (Long) specification.toQuery(entityManager).getSingleResult();
  }
}
