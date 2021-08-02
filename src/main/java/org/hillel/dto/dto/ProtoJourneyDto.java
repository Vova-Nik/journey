package org.hillel.dto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProtoJourneyDto {

    private String stationFrom;
    private String stationTo;
    private String departureDate;

    @Override
    public String toString() {
        return "ProtoJourneyDto{" +
                "stationFrom='" + stationFrom + '\'' +
                ", stationTo='" + stationTo + '\'' +
                ", departureDate='" + departureDate + '\'' +
                '}';
    }
}
