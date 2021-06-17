package org.hillel.controller.rest;

import io.jsonwebtoken.lang.Assert;
import lombok.AllArgsConstructor;
import org.hillel.dto.dto.AuthenticationRequestDto;
import org.hillel.dto.dto.AuthenticationResponseDto;
import org.hillel.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/auth")
    public AuthenticationResponseDto auth(@RequestBody final AuthenticationRequestDto dto) {
        Assert.notNull(dto, "Authentication Request should be set");
        final Authentication authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        return  AuthenticationResponseDto.builder().token(jwtUtil.generateToken(authenticate.getName())).build();
    }
}
