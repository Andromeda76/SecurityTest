package com.example.securitytest.model.recorders;


import jakarta.validation.constraints.NotEmpty;

public record UserRecord(String id,
                         @NotEmpty(message = "username must be unique")
                         String username,
                         @NotEmpty(message = " Not hashed yet...!!!")
                         String password,
                         @NotEmpty(message = "Not required")
                         String email) {
}
