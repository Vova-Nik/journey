package org.hillel.dto.converter;

import org.hillel.dto.dto.ClientDto;
import org.hillel.persistence.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {

    ClientDto clientToDto(ClientEntity client);

    ClientEntity clientDtoToEntity(ClientDto client);
}
