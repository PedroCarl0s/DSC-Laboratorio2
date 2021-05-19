package com.dsc.classroom.services;

import org.springframework.stereotype.Service;

@Service
public class LoginResponse {
    public String token;

    public LoginResponse() {
        
    }

    public LoginResponse(String token) {
        this.token = token;
    }
    
}