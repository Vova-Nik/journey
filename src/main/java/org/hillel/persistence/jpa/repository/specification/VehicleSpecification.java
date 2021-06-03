package org.hillel.persistence.jpa.repository.specification;

import org.hibernate.query.criteria.internal.OrderImpl;
import org.hillel.dto.dto.FilterOperation;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.ClientEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.entity.VehicleEntity_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class VehicleSpecification {

    public static Specification<VehicleEntity> byName(final String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(VehicleEntity_.NAME), criteriaBuilder.literal(name));
    }

    public static Specification<VehicleEntity> onlyActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get(VehicleEntity_.ACTIVE));
    }

    public static Specification<VehicleEntity> ordered(final String column) {
        return (root, query, criteriaBuilder) -> {
            final OrderImpl order = new OrderImpl(root.get(column), true);
            query.orderBy(order);
            return criteriaBuilder.and(root.get(VehicleEntity_.ID).isNotNull());
        };
    }

    public static Specification<VehicleEntity> getAllll() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(root.get(VehicleEntity_.NAME).isNotNull());
    }


    public static Specification<VehicleEntity> filtered(QueryParam param) {
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

    public static Specification<VehicleEntity> byQueryParam(QueryParam param) {
        return new Specification<VehicleEntity>() {
            @Override
            public Predicate toPredicate(@NonNull Root<VehicleEntity> root,
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
