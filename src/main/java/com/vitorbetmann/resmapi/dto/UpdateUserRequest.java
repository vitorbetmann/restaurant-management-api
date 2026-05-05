package com.vitorbetmann.resmapi.dto;

import jakarta.validation.constraints.Email;

public record UpdateUserRequest(
        String name,
        @Email String email,
        String login,
        String address) {
}
