package com.dsc.classroom.controllers;

import javax.validation.Valid;

import com.dsc.classroom.dtos.UserLoginDTO;
import com.dsc.classroom.exceptions.InvalidLoginException;
import com.dsc.classroom.services.JWTService;
import com.dsc.classroom.services.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class LoginController {

    private JWTService jwtService;

    @Autowired
    public LoginController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {

        try {
            return new ResponseEntity<LoginResponse>(jwtService.authenticate(userLoginDTO), HttpStatus.OK);
        } catch (InvalidLoginException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}