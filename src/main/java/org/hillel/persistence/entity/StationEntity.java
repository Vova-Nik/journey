package org.hillel.persistence.entity;

import lombok.*;
import org.hillel.persistence.entity.enums.StationType;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "station")

public class StationEntity extends AbstractEntity<Long> {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "longitude", nullable = false)
    private Double longitude;
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    @Column(name = "description")
    private String description;
    @Column(name = "foundation")
    private Instant foundation;
    @Column(name = "station_type", nullable = false, length = 12)
    @Enumerated(EnumType.STRING)
    private StationType stationType;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RouteEntity> routes;

    public StationEntity() {
        routes = new HashSet<>();
    }

    public StationEntity(final String name) {
        if (StringUtils.isEmpty(name)) throw new IllegalArgumentException("StationEntity.constructor bad name");
        routes = new HashSet<>();
        this.name = name;
        longitude = 29.0D;
        latitude = 50.5D;
        description = "Just other Station on ones way";
        int delay = (int) (Math.random() * 1000000000);
        foundation = Instant.parse("1970-12-03T10:15:30.00Z").minusSeconds(delay * delay);
        stationType = StationType.LINEAR;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && StringUtils.hasText(getName()) && longitude != null && latitude != null && StringUtils.hasText(description) && foundation != null;
    }

    public void addRoute(final RouteEntity route) {
        if (Objects.isNull(route)) return;
        if (!route.isValid()) return;
        if (routes.contains(route)) return;
        this.routes.add(route);
    }

    public void removeRoute(final RouteEntity route) {
        if (Objects.isNull(route)) return;
        if (!route.isValid()) return;
        this.routes.remove(route);
    }

    public Set<RouteEntity> getConnectedRoutes() {
        return new HashSet<>(routes);
    }

    public Set<Long> getConnectedRoutesIds() {
        Set<Long> ids =
                routes.stream()
                        .map(AbstractEntity::getId)
                        .collect(Collectors.toSet());
        return ids;
    }

    public boolean containsRoute(final Long routeId) {
        for (RouteEntity route : routes) {
            assert route.getId() != null;
            if (route.getId().equals(routeId))
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationEntity that = (StationEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        String first = "StationEntity:" + "'\n" +
                "name='" + name + "'\n" +
                "longitude=" + longitude + "'\n" +
                "latitude=" + latitude + "'\n" +
                "description='" + description + "'\n" +
                "foundation=" + foundation + "'\n" +
                "stationType=" + stationType + "'\n";
        StringBuilder second = new StringBuilder();
        routes.forEach((route) -> {
            second
                    .append(route.getName()).append('\t')
                    .append(route.getStationFrom()).append(" -> ")
                    .append(route.getStationTo())
                    .append('\n');
        });
        return first + second.toString();
    }
}
