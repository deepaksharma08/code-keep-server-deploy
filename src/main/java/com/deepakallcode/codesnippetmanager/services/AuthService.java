package com.deepakallcode.codesnippetmanager.services;

import com.deepakallcode.codesnippetmanager.entities.User;
import com.deepakallcode.codesnippetmanager.enums.Role;
import com.deepakallcode.codesnippetmanager.models.UserDTO;
import com.deepakallcode.codesnippetmanager.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public UserDTO loginUser(UserDTO user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            User fetchedUser = userRepository.findByEmail(user.getEmail());
            user.setToken(jwtService.generateJwtToken(fetchedUser));
            user.setId(fetchedUser.getId());
        } catch (Exception e) {
            user.setStatus("FAILED");
        }
        return user;
    }

    public UserDTO registerUser(UserDTO user) {
        User userToSave = new User();
        userToSave.setEmail(user.getEmail());
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        userToSave.setRole(Role.User);
        try {
            userRepository.save(userToSave);
            user.setStatus("SUCCESS");
        } catch (Exception e) {
            user.setStatus("FAILED");
        }
        return user;
    }
}
