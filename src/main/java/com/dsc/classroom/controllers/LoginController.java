package com.dsc.classroom.controllers;

import javax.validation.Valid;

import com.dsc.classroom.dtos.UserDTO;
import com.dsc.classroom.services.JWTService;
import com.dsc.classroom.services.LoginResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class LoginController {
    
    private JWTService jwtService;

    public LoginController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserDTO userDTO) {
        return null;
    }

}