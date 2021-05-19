package com.dsc.classroom.services;

import java.util.Optional;

import com.dsc.classroom.dtos.UserDTO;
import com.dsc.classroom.dtos.UserLoginDTO;
import com.dsc.classroom.exceptions.InvalidUserException;
import com.dsc.classroom.models.User;
import com.dsc.classroom.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

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

    public boolean validateEmailPassword(UserLoginDTO userLoginDTO){

        Optional<User> userOptinal = userRepository.findByEmail(userLoginDTO.getEmail());
        return userOptinal.isPresent() && userOptinal.get().getPassword().equals(userLoginDTO.getPassword());
    }

    public UserDTO deleteUser(String email, String authorizationHeader) throws InvalidUserException {
        Optional<String> userId = jwtService.userIdRecovery(authorizationHeader);

        User user = validateUser(userId, email);

        userRepository.delete(user);
        return new UserDTO(user.getEmail(), user.getName());
    }

    private User validateUser(Optional<String> id, String email) throws InvalidUserException {
        if(!id.isPresent()){
            throw new InvalidUserException("User not exists");
        }
        Optional<User> user = userRepository.findByEmail(id.get());

        if(!user.isPresent() || user.isPresent() && !user.get().getEmail().equals(email)){
            throw new InvalidUserException("Invalid token");
        }
        return user.get();
    }
}