package com.insurance.claim_system.security.service;

import com.insurance.claim_system.security.dto.RegisterRequest;
import com.insurance.claim_system.security.dto.RegisterResponse;

public interface SecurityService {
    RegisterResponse createUser(RegisterRequest user);
}
