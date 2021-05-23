package com.dsc.classroom.services;

import com.dsc.classroom.dtos.UserLoginDTO;

import com.dsc.classroom.exceptions.InvalidLoginException;
import com.dsc.classroom.filters.TokenFilter;
import com.dsc.classroom.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JWTService {

    private final int TOTAL_MINUTES_EXPIRATION = 10;

    @Autowired
    private UserService userService;

    public static final String TOKEN_KEY = "insomnia";

    public LoginResponse authenticate(UserLoginDTO userLoginDTO) throws InvalidLoginException {
        String errorMessage = "Invalid username or password. Login failed!";

        if (userService.validateEmailPassword(userLoginDTO)) {
            return new LoginResponse(generateToken(userLoginDTO));
        }

        throw new InvalidLoginException(errorMessage);
    }

    private String generateToken(UserLoginDTO userLoginDTO) {
        String token = Jwts.builder().setSubject(userLoginDTO.getEmail())
                .signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + TOTAL_MINUTES_EXPIRATION * 60 * 1000)).compact();

        return token;

    }

    public Optional<String> userIdRecovery(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new SecurityException("Invalid token or authorization header!");
        }

        String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);
        String subject = null;

        try {
            subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();

        } catch (Exception e) {
            throw new SecurityException("Invalid token or expired!");
        }

        return Optional.of(subject);
    }

    public User getUserId(String authorization) {
        String email = userIdRecovery(authorization)
                .orElseThrow(() -> new SecurityException("The user was not found!"));

        Optional<User> user = userService.findByEmail(email);

        return user.get();
    }
}