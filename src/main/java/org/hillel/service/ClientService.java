package org.hillel.service;

import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.ClientEntity;
import org.hillel.persistence.jpa.repository.ClientJPARepository;
import org.hillel.persistence.jpa.repository.specification.ClientSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service("ClientService")
public class ClientService extends EntityServiceImplementation<ClientEntity, Long> {

    private final ClientJPARepository clientRepository;

    @Autowired
    public ClientService(ClientJPARepository clientRepository) {
        super(ClientEntity.class, clientRepository);
        this.clientRepository = clientRepository;
    }

    @Override
    boolean isValid(ClientEntity client) {
        return client.isValid();
    }

    @Transactional(readOnly = true)
    public List<ClientEntity> getFiltered(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        checkInput(param);
        return clientRepository.findAll(ClientSpecification.byQueryParam(param));
    }

    @Transactional(readOnly = true)
    public Page<ClientEntity> getFilteredPaged(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        param = checkInput(param);
        PageRequest  page = PageRequest.of(param.getPageNumber(), param.getPageSize());
        return clientRepository.findAll(ClientSpecification.byQueryParam(param), page);
    }

    private QueryParam checkInput(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        Field[] allFields = ClientEntity.class.getDeclaredFields();
        String column = param.getSortColumn();

        boolean checked = Arrays.stream(allFields).anyMatch(field ->
                column.equals(field.getName()));
        if (column.equals("id"))
            checked = true;
        if (!checked)
            throw new IllegalArgumentException("Insufficient column name = \""+ column + "\" for sorting of Client");
        return param;
    }
}
