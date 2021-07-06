package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "stop",
        uniqueConstraints = @UniqueConstraint(columnNames = {"route_id", "station_id"}
        ))

public class StopEntity extends AbstractEntity<Long> implements Comparable<StopEntity> {

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private StationEntity station;

    @ManyToOne(cascade = CascadeType.ALL)
    private RouteEntity route;

    @Column(name = "arrival")
    private LocalTime arrival;

    @Column(name = "staying")
    private int duration;

    @Column(name = "departure")
    private LocalTime departure;

    @Column(name = "description")
    private String description;

    @Column(name = "dayoffset")
    private Integer dayOffset;

    public StopEntity() {
    }

    public StopEntity(RouteEntity route, StationEntity station, LocalTime arrival, int duration) {
        this.name = station.getName();
        this.route = route;
        this.station = station;
        this.arrival = arrival;
        this.duration = duration;
        this.departure = arrival.plusSeconds(duration);
        this.description = "Sadis, ne zevaj";
        this.dayOffset = 0;
    }

    public StopEntity(RouteEntity route, StationEntity station, LocalTime arrival, int duration, int offset) {
        this( route,  station,  arrival,  duration);
        this.dayOffset = offset;
    }

    @Override
    public String toString() {
        return "StopEntity{" +
                "name='" + name + '\'' +
                ", station=" + station +
                ", route=" + route +
                ", arrival=" + arrival +
                ", duration=" + duration +
                ", departure=" + departure +
                ", description='" + description + '\'' +
                ", offset=" + dayOffset +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopEntity that = (StopEntity) o;
        return name.equals(that.name) &&
                Objects.equals(departure, that.departure) &&
                Objects.equals(dayOffset, that.dayOffset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, departure, dayOffset);
    }

    @Override
    public int compareTo(@NonNull StopEntity o) {
        if (this.equals(o)) return 0;
        if (this.dayOffset > o.dayOffset) return 1;
        if (this.dayOffset < o.dayOffset) return -1;
        return this.departure.compareTo(o.departure);
    }
}
