package com.epam.esm.task5.repository.specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public interface Specification {

  Query toQuery(EntityManager entityManager);
}
