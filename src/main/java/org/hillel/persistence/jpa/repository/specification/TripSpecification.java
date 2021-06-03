package org.hillel.persistence.jpa.repository.specification;

import org.hibernate.query.criteria.internal.OrderImpl;
import org.hillel.dto.dto.FilterOperation;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.*;
import java.time.LocalDate;

public class TripSpecification {

    public static Specification<TripEntity> findByRoute(Long routeId) {
        return (root, query, criteriaBuilder) -> {
            root.join(TripEntity_.route);
            return criteriaBuilder.equal(root.get(TripEntity_.route), routeId);
        };
    }

    public static Specification<TripEntity> findByDate(final LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TripEntity_.DEPARTURE_DATE), date);
    }

    public static Specification<TripEntity> findByName(final String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TripEntity_.NAME), name);
    }


    public static Specification<TripEntity> filtered(QueryParam param) {
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

    public static Specification<TripEntity> byQueryParam(QueryParam param) {
        return new Specification<TripEntity>() {
            @Override
            public Predicate toPredicate(@NonNull Root<TripEntity> root,
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
