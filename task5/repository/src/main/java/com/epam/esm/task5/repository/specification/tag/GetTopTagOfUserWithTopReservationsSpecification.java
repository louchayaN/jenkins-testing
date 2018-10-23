package com.epam.esm.task5.repository.specification.tag;

import com.epam.esm.task5.repository.entity.Tag;
import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GetTopTagOfUserWithTopReservationsSpecification implements Specification {

  private static final String GET_TOP_TAGS =
      "SELECT id_tag, t_name "
      + "FROM "
          + "(SELECT id_tag, COUNT(id_tag) AS tagCount, MAX(COUNT(id_tag)) OVER () AS maxTagCount "
          + "FROM "
              + "(SELECT id_user, SUM(cost) AS totalSum, MAX(SUM(cost)) OVER () AS maxTotalSum "
              + "FROM reservation "
              + "GROUP BY id_user) AS totalPurchases "
          + "JOIN reservation USING (id_user) JOIN tag_history USING (id_quest) "
          + "WHERE totalSum = maxTotalSum "
          + "GROUP BY id_tag) AS tagCounts "
      + "JOIN tag USING (id_tag) "
      + "WHERE tagCount = maxTagCount;";

  @Override
  public Query toQuery(EntityManager entityManager) {
    return entityManager.createNativeQuery(GET_TOP_TAGS, Tag.class);
  }
}
