package com.runtik.greenatom_test.auth.dto;

public record AuthenticationRequest(
        String username,
        String password
) {
}