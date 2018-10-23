package com.epam.esm.task5.repository.specification.quest;

import com.epam.esm.task5.repository.entity.Quest;
import com.epam.esm.task5.repository.entity.Tag;
import com.epam.esm.task5.repository.searchcriteria.QuestSearchCriteria;
import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GetQuestsBySearchCriteriaSpecification implements Specification {

  private String ownerId;
  private String partOfName;
  private String partOfDescription;
  private String startPrice;
  private String endPrice;
  private List<String> tagNames;
  private List<String> sortTypes;

  public GetQuestsBySearchCriteriaSpecification(QuestSearchCriteria searchCriteria) {
    this.ownerId = searchCriteria.getOwner();
    this.partOfName = searchCriteria.getName();
    this.partOfDescription = searchCriteria.getDescription();
    this.startPrice = searchCriteria.getStartPrice();
    this.endPrice = searchCriteria.getEndPrice();
    this.tagNames = searchCriteria.getTags();
    this.sortTypes = searchCriteria.getSorts();
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    CriteriaQuery<Quest> query = formQuery(entityManager);
    TypedQuery<Quest> typedQuery = entityManager.createQuery(query);
    setParameters(typedQuery);
    return typedQuery;
  }

  private CriteriaQuery<Quest> formQuery(EntityManager entityManager) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Quest> query = criteriaBuilder.createQuery(Quest.class);
    Root<Quest> root = query.from(Quest.class);
    query.select(root);
    query.distinct(true);

    query.where(getPredicates(criteriaBuilder, root));
    addOrderBy(criteriaBuilder, query, root);
    addGroupBy(criteriaBuilder, query, root);

    return query;
  }

  Predicate[] getPredicates(CriteriaBuilder criteriaBuilder, Root<Quest> root) {
    List<Predicate> predicates = new ArrayList<>();
    if (ownerId != null) {
      ParameterExpression<Long> p = criteriaBuilder.parameter(Long.class, "ownerId");
      predicates.add(criteriaBuilder.equal(root.get("ownerId"), p));
    }
    if (partOfName != null) {
      ParameterExpression<String> p = criteriaBuilder.parameter(String.class, "name");
      predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), p));
    }
    if (partOfDescription != null) {
      ParameterExpression<String> p = criteriaBuilder.parameter(String.class, "description");
      predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), p));
    }

    if (startPrice != null) {
      ParameterExpression<BigDecimal> p = criteriaBuilder.parameter(BigDecimal.class, "startPrice");
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), p));
    }

    if (endPrice != null) {
      ParameterExpression<BigDecimal> p = criteriaBuilder.parameter(BigDecimal.class, "endPrice");
      predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), p));
    }

    if (tagNames != null && !tagNames.isEmpty()) {
      Join<Quest, Tag> tagJoin = root.join("tags");
      Predicate predicate =
              criteriaBuilder.equal(tagJoin.get("name"), tagNames.get(0).toLowerCase());

      for (int i = 1; i < tagNames.size(); i++) {
        predicate =
                criteriaBuilder.or(
                        predicate,
                        criteriaBuilder.equal(tagJoin.get("name"), tagNames.get(i).toLowerCase()));
      }
      predicates.add(predicate);
    }

    return predicates.toArray(new Predicate[0]);
  }

  private void addOrderBy(CriteriaBuilder builder, CriteriaQuery<Quest> query, Root<Quest> root) {
    if (sortTypes == null) {
      addDefaultOrderBy(builder, query, root);
      return;
    }

    List<Order> orders = new ArrayList<>();
    for (String sortType : sortTypes) {
      switch (sortType) {
        case "name":
          orders.add(builder.asc(root.get("name")));
          break;
        case "-name":
          orders.add(builder.desc(root.get("name")));
          break;
        case "created":
          orders.add(builder.asc(root.get("dateOfCreation")));
          break;
        case "-created":
          orders.add(builder.desc(root.get("dateOfCreation")));
          break;
      }
    }

    if (orders.isEmpty()) {
      addDefaultOrderBy(builder, query, root);
    } else {
      query.orderBy(orders);
    }
  }

  private void addDefaultOrderBy(
          CriteriaBuilder builder, CriteriaQuery<Quest> query, Root<Quest> root) {
    query.orderBy(builder.asc(root.get("name")));
  }

  void addGroupBy(CriteriaBuilder builder, AbstractQuery<Quest> query, Root<Quest> root) {
    if (tagNames != null && !tagNames.isEmpty()) {
      query.groupBy(root.get("id")).having(builder.equal(builder.count(root), tagNames.size()));
    }
  }

  void setParameters(Query query) {
    if (ownerId != null) {
      query.setParameter("ownerId", Long.valueOf(ownerId));
    }
    if (partOfName != null) {
      query.setParameter("name", '%' + partOfName.toLowerCase() + '%');
    }
    if (partOfDescription != null) {
      query.setParameter("description", '%' + partOfDescription.toLowerCase() + '%');
    }
    if (startPrice != null) {
      query.setParameter("startPrice", new BigDecimal(startPrice));
    }

    if (endPrice != null) {
      query.setParameter("endPrice", new BigDecimal(endPrice));
    }
  }
}
