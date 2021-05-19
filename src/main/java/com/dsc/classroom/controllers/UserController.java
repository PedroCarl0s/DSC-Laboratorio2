package com.dsc.classroom.controllers;

import javax.validation.Valid;

import com.dsc.classroom.dtos.UserDTO;
import com.dsc.classroom.models.User;
import com.dsc.classroom.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class UserController {
    
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody User user) {
        try {
            return new ResponseEntity<UserDTO>(this.userService.addUser(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<UserDTO>(HttpStatus.CONFLICT);
        }
    }

}