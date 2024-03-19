package com.deepakallcode.codesnippetmanager.controllers;

import com.deepakallcode.codesnippetmanager.models.UserDTO;
import com.deepakallcode.codesnippetmanager.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/auth")
public class AuthController {
    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO user) {
        ResponseEntity<UserDTO> userResponse = null;
        try{
            UserDTO userDTO = authService.loginUser(user);
            userResponse = new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            userResponse = new ResponseEntity<>(new UserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return userResponse;
    }

    @PostMapping("register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user) {
        ResponseEntity<UserDTO> userResponse = null;
        try {
            UserDTO userDTO = authService.registerUser(user);
            userResponse = new ResponseEntity<>(userDTO, HttpStatus.OK);

        }catch (Exception e) {
            userResponse = new ResponseEntity<>(new UserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return userResponse;
    }
}
