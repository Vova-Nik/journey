package org.hillel.controller.rest;

import io.jsonwebtoken.lang.Assert;
import lombok.AllArgsConstructor;
import org.hillel.dto.dto.AuthenticationRequestDto;
import org.hillel.dto.dto.AuthenticationResponseDto;
import org.hillel.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
/*
@Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
*/
    @PostMapping(
            path = "/auth",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )

    public AuthenticationResponseDto auth(@RequestBody final AuthenticationRequestDto dto) {
        Assert.notNull(dto, "Authentication Request should be set");
        String email = dto.getEmail();
        String password = dto.getPassword();
        final Authentication authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        String token = jwtUtil.generateToken(authenticate.getName());
        return  AuthenticationResponseDto.builder().token(token).build();
    }
}
/*
http://localhost:8080/api/swagger-ui.html
    {
      "email": "admin@ttt.ua",
      "password": "123"
     }
        */
