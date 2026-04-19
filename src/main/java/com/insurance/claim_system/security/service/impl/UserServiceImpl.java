package com.insurance.claim_system.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.insurance.claim_system.security.dto.RegisterRequest;
import com.insurance.claim_system.security.dto.RegisterResponse;
import com.insurance.claim_system.security.service.UserService;
import com.insurance.claim_system.user.entity.Role;
import com.insurance.claim_system.user.entity.User;
import com.insurance.claim_system.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public RegisterResponse createUser(RegisterRequest registerRequest) {
        String hashedPassword = passwordEncoder.encode(registerRequest.password());
        User user = User.builder()
                .email(registerRequest.email())
                .password(hashedPassword)
                .role(Role.ROLE_CLIENT)
                .enabled(true)
                .build();
        User userDB = userRepository.save(user);
        RegisterResponse userResponse = new RegisterResponse(userDB.getId(), userDB.getEmail(), userDB.getRole(),
                userDB.isEnabled());

        return userResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();

    }

}
