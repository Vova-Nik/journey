package org.hillel.dto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String pwd;
}
