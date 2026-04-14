package com.insurance.claim_system.security.dto;

import com.insurance.claim_system.user.entity.Role;

public record RegisterResponse(Long id, String email, Role role, boolean enabled) {

}
