package org.hillel.persistence.jpa.repository.specification;

import org.hibernate.query.criteria.internal.OrderImpl;
import org.hillel.dto.dto.FilterOperation;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.*;

public class ClientSpecification {

    public static Specification<UserEntity> filtered(QueryParam param) {
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

    public static Specification<UserEntity> byQueryParam(QueryParam param) {
        return new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(@NonNull Root<UserEntity> root,
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




