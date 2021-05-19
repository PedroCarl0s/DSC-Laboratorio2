package com.dsc.classroom.services;

import com.dsc.classroom.dtos.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    
    private final int TOTAL_MINUTES_EXPIRATION = 10;

    private UserService userService;
    public static final String TOKEN_KEY = "insomnia";

    @Autowired
    public JWTService(UserService studentService) {
        this.userService = studentService;
    }

}