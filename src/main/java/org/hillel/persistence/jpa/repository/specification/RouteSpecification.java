package org.hillel.persistence.jpa.repository.specification;

import org.hibernate.query.criteria.internal.OrderImpl;
import org.hillel.dto.dto.FilterOperation;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.RouteEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RouteSpecification {

    public static Specification<RouteEntity> filtered(QueryParam param) {
        return (root, query, criteriaBuilder) -> {
            if (param.getFilterValue().equals("*")) {
                return criteriaBuilder.greaterThan(root.get("name"), criteriaBuilder.literal(" "));
            }
            if (param.getFilterOperation().equals(FilterOperation.GREATER))
                return criteriaBuilder.greaterThan(root.get(param.getFilterField()), criteriaBuilder.literal(param.getFilterValue()));
            if (param.getFilterOperation().equals(FilterOperation.LESS))
                return criteriaBuilder.lessThan(root.get(param.getFilterField()), criteriaBuilder.literal(param.getFilterValue()));
            return criteriaBuilder.equal(root.get(param.getFilterField()), criteriaBuilder.literal(param.getFilterValue()));
        };
    }

    public static Specification<RouteEntity> byQueryParam(QueryParam param) {
        return new Specification<RouteEntity>() {
            @Override
            public Predicate toPredicate(@NonNull Root<RouteEntity> root,
                                         @NonNull CriteriaQuery<?> query,
                                         @NonNull CriteriaBuilder criteriaBuilder) {

                boolean sortDir = true;
                if (param.getSortDirection().equals("desc"))
                    sortDir = false;
                final OrderImpl order = new OrderImpl(root.get(param.getSortColumn()), sortDir);
                query.orderBy(order);

                if (param.getFilterValue().equals("*")) {
                    return criteriaBuilder.greaterThan(root.get("name"), criteriaBuilder.literal(" "));
                }
                if (param.getFilterOperation().equals(FilterOperation.GREATER))
                    return criteriaBuilder.greaterThan(root.get(param.getFilterField()), criteriaBuilder.literal(param.getFilterValue()));
                if (param.getFilterOperation().equals(FilterOperation.LESS))
                    return criteriaBuilder.lessThan(root.get(param.getFilterField()), criteriaBuilder.literal(param.getFilterValue()));
                return criteriaBuilder.equal(root.get(param.getFilterField()), criteriaBuilder.literal(param.getFilterValue()));
            }
        };
    }
}
