package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
//import java.time.Duration;
//import java.time.Instant;
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
    private int staying;

    @Column(name = "departure")
    private LocalTime departure;

    @Column(name = "description")
    private String description;

    @Column(name = "dayoffset")
    private Integer dayOffset;

    @Column(name = "secoffset")
    private Integer secOffset;

    public StopEntity() {
    }

    public StopEntity(RouteEntity route, StationEntity station, LocalTime arrival, int staying) {
        this.name = station.getName();
        this.route = route;
        this.station = station;
        this.arrival = arrival;
        this.staying = staying;
        this.departure = arrival.plusSeconds(staying);
        this.description = "Sadis, ne zevaj";
        this.secOffset = arrival.getHour()*3600 + arrival.getMinute()*60;
        this.dayOffset = 0;
    }

    public StopEntity(RouteEntity route, StationEntity station, LocalTime arrival, int staying, int offset) {
        this( route,  station,  arrival,  staying);
        this.dayOffset = offset;
        this.secOffset = this.secOffset + offset * 3600 * 24;
    }

//    public int getDuration(){
//        int dep = departure.getHour()*3600 + departure.getMinute()*60 + departure.getSecond();
//        int arr = arrival.getHour()*3600 + arrival.getMinute()*60 + arrival.getSecond();
//        return dep - arr;
//    }

    @Override
    public String toString() {
        return "StopEntity{" +
                "name='" + name + '\'' +
                ", station=" + station +
                ", route=" + route +
                ", arrival=" + arrival +
                ", departure=" + departure +
                ", description='" + description + '\'' +
                ", offset=" + dayOffset +
                ", secOffset=" + secOffset +
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
        if (!this.getRoute().equals(o.getRoute())) return 0;
        return this.secOffset.compareTo(o.secOffset);
    }
}
