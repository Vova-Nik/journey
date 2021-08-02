package org.hillel.service;

import org.hillel.exceptions.SynonimAPIException;
import org.hillel.persistence.entity.SynonimEntity;
import org.hillel.persistence.jpa.repository.SynonimJPARepozitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SynonimService {

    private final SynonimJPARepozitory synonimJPARepozitory;
    private Map<String, String> map;

    @Autowired
    public SynonimService(SynonimJPARepozitory synonimJPARepozitory) {
        this.synonimJPARepozitory = synonimJPARepozitory;
        map = new HashMap<String, String>();
        List<SynonimEntity> synonimEntities = synonimJPARepozitory.findAllActive();
        synonimEntities.forEach(entity ->
                map.put(entity.getName(), entity.getTrueName()));
        //        map.forEach((k,v) -> System.out.println(k + " - " + v));
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

}
