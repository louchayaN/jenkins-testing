package com.epam.esm.task5.repository.specification.quest;

import com.epam.esm.task5.repository.entity.Quest;
import com.epam.esm.task5.repository.searchcriteria.QuestSearchCriteria;
import com.epam.esm.task5.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

public class CountQuestsbySearchCriteriaSpecification extends GetQuestsBySearchCriteriaSpecification implements Specification {

  public CountQuestsbySearchCriteriaSpecification(QuestSearchCriteria searchCriteria) {
    super(searchCriteria);
  }

  @Override
  public Query toQuery(EntityManager entityManager) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
    Root<Quest> countRoot = query.from(Quest.class);
    query.select(criteriaBuilder.count(countRoot));
    query.distinct(true);

    Subquery<Quest> subquery = query.subquery(Quest.class);
    Root<Quest> root = subquery.from(Quest.class);
    subquery.select(root);

    subquery.where(getPredicates(criteriaBuilder, root));
    addGroupBy(criteriaBuilder, subquery, root);

    query.where(countRoot.get("id").in(subquery));

    TypedQuery<Long> typedQuery = entityManager.createQuery(query);
    setParameters(typedQuery);

    return typedQuery;
  }
}
