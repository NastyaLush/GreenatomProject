package com.runtik.greenatom_test.auth.dto;

import com.runtik.greenatom_test.user.Role;

public record RegisterRequest (
        String username,
        String password,
        Role role
) {
}