package com.insurance.claim_system.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.insurance.claim_system.security.dto.RegisterRequest;
import com.insurance.claim_system.security.dto.RegisterResponse;
import com.insurance.claim_system.security.service.SecurityService;
import com.insurance.claim_system.user.entity.Role;
import com.insurance.claim_system.user.entity.User;
import com.insurance.claim_system.user.repository.UserRepository;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public SecurityServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse createUser(RegisterRequest registerRequest) {
        String hashedPassword = passwordEncoder.encode(registerRequest.password());
        User user = new User();
        user.setEmail(registerRequest.email());
        user.setPassword(hashedPassword);
        user.setRole(Role.ROLE_CLIENT);
        user.setEnabled(true);
        User userDB = userRepository.save(user);
        RegisterResponse userResponse = new RegisterResponse(userDB.getId(), userDB.getEmail(), userDB.getRole(),
                userDB.isEnabled());

        return userResponse;
    }

}
