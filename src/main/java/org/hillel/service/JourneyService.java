package org.hillel.service;


import org.hillel.dto.dto.JourneyDto;
import org.hillel.persistence.entity.*;
//import org.hillel.persistence.jpa.repository.JourneyJPARepository;
import org.hillel.persistence.jpa.repository.RouteJPARepository;
import org.hillel.persistence.jpa.repository.StationJPARepository;
import org.hillel.persistence.jpa.repository.TripJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    TripService tripService;

    @Autowired
    public JourneyService(
//            JourneyJPARepository journeyRepository,
            TripJPARepository tripJPARepository,
            StationJPARepository stationJPARepository,
            RouteJPARepository routeJPARepository) {

//        super(JourneyEntity.class, journeyRepository);
//        this.journeyRepository = journeyRepository;
        this.tripJPARepository = tripJPARepository;
        this.stationJPARepository = stationJPARepository;
        this.routeJPARepository = routeJPARepository;
    }


//    @Transactional(readOnly = true)
    List<JourneyDto> findRelevant(JourneyDto journeyDto) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate date = LocalDate.parse(journeyDto.getDeparture(), formatter);
        List<StationEntity> stations = stationJPARepository.findPairByName(journeyDto.getStationFrom(), journeyDto.getStationTo());
        if (stations.size() < 2) {
            return null;
        }
        Long id0 = stations.get(0).getId();
        Long id1 = stations.get(1).getId();
        List<RouteEntity> routes = routeJPARepository.findAllActive();
//        List<Long> suitableRoutes = routes.stream()
//                .filter((route) -> route.containsStationById(id0))
//                .filter((route) -> route.containsStationById(id1))
//                .map(AbstractEntity::getId)
//                .collect(Collectors.toList());

//        List<TripEntity> trips = tripService.getByRouteAndDate(suitableRoutes,date);

//        List<JourneyDto> result = trips.stream()
//                .map((trip)->new JourneyDto(trip, journeyDto.getStationFrom(), journeyDto.getStationTo()))
//                .collect(Collectors.toList());

        return null;
    }



//    @Override
    boolean isValid(JourneyEntity entity) {
        return entity.isValid();
    }


}
