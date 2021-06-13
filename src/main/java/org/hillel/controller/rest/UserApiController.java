package org.hillel.controller.rest;

import org.hillel.dto.converter.UserMapper;
import org.hillel.dto.dto.QueryParam;
import org.hillel.dto.dto.UserDto;
import org.hillel.persistence.entity.UserEntity;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController

@RequestMapping("/user")
public class UserApiController {

    private final TicketClient ticketClient;
    private final UserMapper mapper;
    private final int TABLE_SIZE = 4;
    QueryParam queryParam;

    @Autowired
    public UserApiController(TicketClient ticketClient, UserMapper mapper) {
        this.ticketClient = ticketClient;
        this.mapper = mapper;
        queryParam = new QueryParam();
    }

    /* *************************************** get all *********************************************/
    @GetMapping(
            path = "/users",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )

    @ResponseBody
    public ResponseEntity<List<UserDto>> users() {
        if (ticketClient.countUsers() > 1000) {
            //todo redirection to paged user service
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).build();
        }
        List<UserEntity> users = ticketClient.getAllUsers();
        List<UserDto> dtos = users.stream()
                .map(mapper::userToDto)
                .collect(Collectors.toList());
        System.out.println(dtos.get(0));
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /* ********************************* get one user ***************************************************/

    @CrossOrigin
    @GetMapping(
            path = "/user",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseBody

    public ResponseEntity<UserDto> getUser(HttpServletRequest request, @RequestParam("id") Long id) {
        UserDto userDto = null;
        if (id == null || id < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDto);
        }
        UserEntity userEntity = ticketClient.getUserById(id);
        userDto = mapper.userToDto(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    /* *************************************** create *********************************************/
    @PostMapping(
            path = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )

    @ResponseBody
    public ResponseEntity<UserDto> persistClient(@RequestBody UserDto userDto) {
        UserEntity userEntity = mapper.userDtoToEntity(userDto);
        System.out.println(userDto);
        if (userEntity.isValid()) {
            userEntity.resetId();
            UserEntity createdUserEntity = ticketClient.createUser(userEntity);
                if (createdUserEntity.equals(userEntity)) {
                    UserDto createdUserDto = mapper.userToDto(createdUserEntity);
                    return ResponseEntity.status(HttpStatus.OK).body(createdUserDto);
                }
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).header("Unable to create user. User with such email already exists").body(userDto);
    }

    /* *************************************** update *********************************************/

    @PostMapping(
            path = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseBody
    public ResponseEntity<UserDto> updateClient(@RequestBody UserDto userDto) {
        UserEntity userEntity = mapper.userDtoToEntity(userDto);
        if (userEntity.isValid()) {
            UserEntity updatedUserEntity = ticketClient.updateUser(userEntity);
            if (updatedUserEntity.equals(userEntity)) {
                UserDto updatedUserDto = mapper.userToDto(updatedUserEntity);
                return ResponseEntity.status(HttpStatus.OK).body(updatedUserDto);
            }
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).header("Unable to delete incorrect user passed").build();
    }
    /* *************************************** delete *********************************************/

    @DeleteMapping(
            path = "/delete",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )

    @Secured("ROLE_ADMIN")
    @ResponseBody
    public ResponseEntity<UserDto> deleteClient(@RequestBody UserDto userDto) {
        UserEntity userEntity = mapper.userDtoToEntity(userDto);
        if (userEntity.isValid()) {
            UserEntity deletedUserEntity = ticketClient.deleteUser(userEntity);
            if (deletedUserEntity.equals(userEntity)) {
                UserDto deletedUserDto = mapper.userToDto(deletedUserEntity);
                return ResponseEntity.status(HttpStatus.OK).body(deletedUserDto);
            }
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).header("Unable to delete incorrect user passed").build();
    }
}


