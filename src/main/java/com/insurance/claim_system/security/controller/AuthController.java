package com.insurance.claim_system.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.claim_system.security.dto.RegisterRequest;
import com.insurance.claim_system.security.dto.RegisterResponse;
import com.insurance.claim_system.security.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService securityService;

    public AuthController(UserService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        return securityService.createUser(registerRequest);
    }

}
