package com.dsc.classroom.services;

import java.util.Optional;

import com.dsc.classroom.dtos.UserDTO;
import com.dsc.classroom.models.User;
import com.dsc.classroom.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public UserDTO addUser(User user) throws Exception {
        Optional<User> existedUser = this.userRepository.findByEmail(user.getEmail());

        if (existedUser.isPresent()) {
            throw new Exception("O usuário já existe!");
        }
        this.userRepository.save(user);

        return new UserDTO(user.getEmail(), user.getName());
    }

}