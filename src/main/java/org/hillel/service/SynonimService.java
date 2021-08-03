package org.hillel.service;

import org.hillel.exceptions.SynonimAPIException;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.SynonimEntity;
import org.hillel.persistence.jpa.repository.StationJPARepository;
import org.hillel.persistence.jpa.repository.SynonimJPARepozitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SynonimService {

    @Autowired
    private StationJPARepository stationJPARepository;
    private final SynonimJPARepozitory synonimJPARepozitory;
    private Map<String, String> map;

    @Autowired
    public SynonimService(SynonimJPARepozitory synonimJPARepozitory) {
        this.synonimJPARepozitory = synonimJPARepozitory;
        map = new HashMap<String, String>();
        List<SynonimEntity> synonimEntities = synonimJPARepozitory.findAllActive();
        synonimEntities.forEach(entity ->
                map.put(entity.getName(), entity.getTrueName()));
    }

    public void updateTable() {
        map = new HashMap<String, String>();
        List<SynonimEntity> synonimEntities = synonimJPARepozitory.findAllActive();
    }

    public String getTrueName(String word) {
        word = word.toLowerCase();
        String rezult = map.get(word);
        if (rezult == null) {
            throw new SynonimAPIException("Can not find \"" + word + "\" in data");
        }
        rezult = rezult.substring(0, 1).toUpperCase() + rezult.substring(1);
        return rezult;
    }

    public List<SynonimEntity> getStationAbreviations() {
        List<StationEntity> stations = stationJPARepository.findAll();
        List<SynonimEntity> synonims = new ArrayList<>();
        stations.forEach(station -> synonims.addAll(synonimJPARepozitory.findSynonimsByStationName(station.getName())));
        List<SynonimEntity> result = synonims.stream()
                .filter(synonimEntity -> synonimEntity.getName().length() < 4)
                .collect(Collectors.toList());
        return result;
    }

    public Map<String, String> getStationSynonimList() {
        List<StationEntity> stations = stationJPARepository.findAll();
        Map<String, String> rezult = new TreeMap<>();
        stations.forEach(station -> rezult.put(station.getName(), ""));
        final StringBuilder sb = new StringBuilder();

        rezult.forEach((k, v) -> {
            List<SynonimEntity> ss = synonimJPARepozitory.findSynonimsByStationName(k);
            sb.setLength(0);
            ss.forEach(syne -> sb.append(syne.getName()).append(", "));
            rezult.replace(k,sb.toString());
        });
        return rezult;
    }

}
