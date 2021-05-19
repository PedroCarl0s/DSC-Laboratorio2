package com.dsc.classroom.controllers;

import javax.validation.Valid;

import com.dsc.classroom.dtos.UserDTO;
import com.dsc.classroom.exceptions.InvalidUserException;
import com.dsc.classroom.models.User;
import com.dsc.classroom.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {
    
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody User user) {
        try {
            return new ResponseEntity<UserDTO>(this.userService.addUser(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<UserDTO>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("email") String email, @RequestHeader("Authorization") String token){
        try{
            return new ResponseEntity<>(userService.deleteUser(email, token), HttpStatus.OK);
        } catch (InvalidUserException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}