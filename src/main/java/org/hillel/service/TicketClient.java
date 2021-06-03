package org.hillel.service;

import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.*;
import org.hillel.persistence.entity.enums.VehicleType;
import org.hillel.persistence.jpa.repository.specification.TripSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class TicketClient {

    private ClientService clientService;
    private JourneyService journeyService;
    private RouteService routeService;
    private StationService stationService;
    private TripService tripService;
    private VehicleService vehicleService;

    public TicketClient() {
    }

    public List<VehicleEntity> findAllVehicles() {
        return vehicleService.findAll();
    }

    public List<JourneyEntity> find(final String stationFrom, final String stationTo, final LocalDate date) {

        List<JourneyEntity> resultList = new ArrayList<>();
        StationEntity from;
        StationEntity to;

        Set<Long> fromRoutes = stationService.getConnectedRoutesIds(stationFrom);
        Set<Long> toRoutes = stationService.getConnectedRoutesIds(stationTo);


        Set<Long> intersection = new HashSet<>(fromRoutes);
        if (intersection.retainAll(toRoutes)) {
            try {
                from = stationService.findOneByName(stationFrom);
                to = stationService.findOneByName(stationTo);
            } catch (IncorrectResultSizeDataAccessException e) {
                throw new IllegalArgumentException("Station is double. Message system administrator");
            }
            if (Objects.isNull(from)) {
                throw new IllegalArgumentException("there is no station with name " + stationFrom);
            }
            if (Objects.isNull(to)) {
                throw new IllegalArgumentException("there is no station with name " + stationTo);
            }

            Long[] routeIds = (Long[]) intersection.toArray();
            List<RouteEntity> routes = routeService.findByIds(routeIds);
            for (RouteEntity route : routes
            ) {
                resultList.add(new JourneyEntity(route, from, to, date));
            }
        }
        //to do
        //find or if it necessary create correspondent Trip with received date
        return resultList;

    }


    /** client */
    @Autowired
    public void setClientService(ClientService service){
        if(Objects.isNull(service)) throw new IllegalArgumentException("Can not create ticket client bean, client Service is empty");
        this.clientService = service;
    }
    public void createClient(ClientEntity entity){
        clientService.save(entity);
    }
    public ClientEntity getClientById(Long id){
        return clientService.findById(id);
    }
    public List<ClientEntity> getAllClients(){
        return clientService.findAllCtive();
    }

    public Page<ClientEntity> getFilteredPagedClients(QueryParam param){
        return clientService.getFilteredPaged(param);
    }
    public Long countClients(){
        return clientService.count();
    }


    /** Journey */
    @Autowired
    public void setJourneyService(JourneyService service){
        if(Objects.isNull(service)) throw new IllegalArgumentException("Can not create ticket client bean,  journey Service is empty");
        this.journeyService = service;
    }
    public void createJourney(JourneyEntity entity){
        journeyService.save(entity);
    }
    public JourneyEntity getJourneyById(Long id){
        return journeyService.findById(id);
    }

    public List<JourneyEntity> getAllJourneys(){
        return journeyService.findAllCtive();
    }


    /** Route */
    @Autowired
    public void setRouteService(RouteService service){
        if(Objects.isNull(service)) throw new IllegalArgumentException("Can not create ticket client bean,  Route  Service is empty");
        this.routeService = service;
    }
    public void createRoute(RouteEntity entity){
        routeService.save(entity);
    }
    public RouteEntity getRouteById(Long id){
        return routeService.findById(id);
    }
    public List<RouteEntity> getAllRoutes(){
        return routeService.findAllCtive();
    }
    public List<StationEntity> getRouteStations(Long id){
        return routeService.getStationsOnRoute(id);
    }
    public List<RouteEntity> getAllRoutesByPage(int page, int size, String sort){
        return routeService.findActiveSortedByPage(page, size, sort);
    }
    public Long countRoutes(){
        return routeService.count();
    }
    public Page<RouteEntity> getFilteredPagedRoutes(QueryParam param){
        return routeService.getFilteredPaged(param);
    }

    /** Station */
    @Autowired
    public void setStationService(StationService service){
        if(Objects.isNull(service)) throw new IllegalArgumentException("Can not create ticket client bean,  Station  Service is empty");
        this.stationService = service;
    }
    public void createStation(StationEntity entity){
        stationService.save(entity);
    }
    public StationEntity getStationById(Long id){
        return stationService.findById(id);
    }
    public List<StationEntity> getAllStations(){
        return stationService.findAllCtive();
    }
    public Set<RouteEntity> getConnectedRoutes(Long id){
        return stationService.getConnectedRoutes(id);
    }
    public List<StationEntity> getAllStationsByPage(int page, int size, String sort){
        return stationService.findActiveSortedByPage(page, size, sort);
    }
    public Long countStations(){
        return stationService.count();
    }
    public Page<StationEntity> getFilteredPagedStations(QueryParam param){
        return stationService.getFilteredPaged(param);
    }

    /** Trip */
    @Autowired
    public void setTripService(TripService service){
        if(Objects.isNull(service)) throw new IllegalArgumentException("Can not create ticket client bean,  Trip  Service is empty");
        this.tripService = service;
    }
    public void createTrip(TripEntity entity){
        tripService.save(entity);
    }
    public TripEntity getTripById(Long id){
        return tripService.findById(id);
    }
    public List<TripEntity> getAllTrips(){
        return tripService.findAllCtive();
    }
    public List<TripEntity> getAllTripsByPage(int page, int size, String sort){
        return tripService.findActiveSortedByPage(page, size, sort);
    }
    public Long countTrips(){
        return tripService.count();
    }
    public Page<TripEntity> getFilteredPagedTrips(QueryParam param){
        return tripService.getFilteredPaged(param);
    }

    /** Vehicle */
    @Autowired
    public void setVehicleService(VehicleService service){
        if(Objects.isNull(service)) throw new IllegalArgumentException("Can not create ticket client bean,  vehicle Service is empty");
        this.vehicleService = service;
    }
    public VehicleEntity getVehicleById(Long id){
        return vehicleService.findById(id);
    }
    public List<VehicleEntity> getAllVehicles(){
        return vehicleService.findAllCtive();
    }
    public void  createVehicle(final VehicleEntity entity){
        vehicleService.save(entity);
    }
    public List<VehicleEntity> getAllVehiclesByPage(int page, int size, String sort){
        return vehicleService.findActiveSortedByPage(page, size, sort);
    }
    public Long countVehicles(){
        return vehicleService.count();
    }

    public Page<VehicleEntity> getFilteredPagedVehicles(QueryParam param){
        return vehicleService.getFilteredPaged(param);
    }
}


