package org.hillel.dto.converter;

import org.hillel.dto.dto.ClientDto;
import org.hillel.persistence.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClientMapper {

    ClientDto clientToDto(ClientEntity client);

    ClientEntity clientDtoToEntity(ClientDto client);
}
