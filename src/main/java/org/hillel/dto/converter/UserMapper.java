package org.hillel.dto.converter;


import org.hillel.dto.dto.UserDto;
import org.hillel.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto userToDto(UserEntity user);

    UserEntity userDtoToEntity(UserDto user);
}
