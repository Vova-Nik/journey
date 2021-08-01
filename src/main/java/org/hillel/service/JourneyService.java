package org.hillel.service;


import org.hillel.dto.dto.JourneyDto;
import org.hillel.exceptions.JourneyAPIException;
import org.hillel.persistence.entity.*;
//import org.hillel.persistence.jpa.repository.JourneyJPARepository;
import org.hillel.persistence.jpa.repository.RouteJPARepository;
import org.hillel.persistence.jpa.repository.StationJPARepository;
import org.hillel.persistence.jpa.repository.StopJPARepository;
import org.hillel.persistence.jpa.repository.TripJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "journeyService")
//public class JourneyService extends EntityServiceImplementation<JourneyEntity, Long> {
public class JourneyService {

    //    private JourneyJPARepository journeyRepository;
    private TripJPARepository tripJPARepository;
    private StationJPARepository stationJPARepository;
    private RouteJPARepository routeJPARepository;
    private StopJPARepository stopJPARepository;

    @Autowired
    TripService tripService;

    @Autowired
    public JourneyService(
//            JourneyJPARepository journeyRepository,
            TripJPARepository tripJPARepository,
            StationJPARepository stationJPARepository,
            StopJPARepository stopJPARepository,
            RouteJPARepository routeJPARepository) {

//        super(JourneyEntity.class, journeyRepository);
//        this.journeyRepository = journeyRepository;
        this.tripJPARepository = tripJPARepository;
        this.stationJPARepository = stationJPARepository;
        this.stopJPARepository = stopJPARepository;
        this.routeJPARepository = routeJPARepository;
    }


    @Transactional(readOnly = true)
    List<JourneyDto> findJourneys(final JourneyDto journeyDto) {
        LocalDate checkDate = null;
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            checkDate = LocalDate.parse(journeyDto.getDepartureDate(), formatter);
        } catch (DateTimeParseException e) {
            throw new JourneyAPIException("Can not parse Date parameter");
        }
        String stationFromName = journeyDto.getStationFrom();
        StationEntity stationFrom = stationJPARepository.findOneByName(stationFromName);
        if (stationFrom == null) {
            throw new JourneyAPIException("Unable to find Station from");
        }
        String stationToName = journeyDto.getStationTo();
        StationEntity stationTo = stationJPARepository.findOneByName(stationToName);
        if (stationTo == null) {
            throw new JourneyAPIException("Unable to find Station To");
        }
        final LocalDate date = checkDate;
        List<JourneyDto> results = new ArrayList<>();
        try {
            List<StopEntity> stops = stopJPARepository.findStopsFromByJourney(journeyDto.getStationFrom(), journeyDto.getStationTo());
            stops.forEach(stop -> {
                        JourneyDto result = new JourneyDto();
                        result.setName(journeyDto.getStationFrom() + " -> " + journeyDto.getStationTo());
                        result.setStationFrom(stationFrom.getName());
                        result.setStationTo(stationTo.getName());
                        result.setDepartureTime(stop.getDeparture().toString());

                        List<StopEntity> destStops  = stopJPARepository.findByRouteAndStation(stop.getRoute(), stationTo);
                        StopEntity destStop = destStops.get(0);
                        result.setArrivalTime(destStop.getArrival().toString());
                        StopEntity destination = stopJPARepository.findByRouteAndStation(stop.getRoute(), stationTo).get(0);
                        result.setArrivalTime(destination.getArrival().toString());
                        result.setRouteName(stop.getRoute().getName());
                        result.setRouteDescript(stop.getRoute().getDescription());
                        LocalDate routeDepartureDate = date.minusDays(stop.getDayOffset());
                        List<TripEntity> trips = tripJPARepository.findByRouteAndDate(stop.getRoute().getId(), date);
                        if (trips.size() > 0) {
                            TripEntity trip = trips.get(0);
                            LocalDate rawDate = trip.getDepartureDate();
                            result.setDepartureDate(rawDate.plusDays(stop.getDayOffset()).toString());
                            result.setArrivalDate(rawDate.plusDays(destStop.getDayOffset()).toString());
                            result.setVehicleType(trip.getVehicle().getVehicleType().toString());
                            result.setVehicleName(trip.getVehicle().getName());
                            results.add(result);
                        }
                    }
            );
        }catch(Exception e){
            throw new JourneyAPIException(e.getMessage());
        }

        return results;
    }


    //    @Override
    boolean isValid(JourneyEntity entity) {
        return entity.isValid();
    }


}
