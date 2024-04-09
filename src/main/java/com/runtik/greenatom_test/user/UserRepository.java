package com.runtik.greenatom_test.user;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByUsername(String email);

    Optional<User> findById(UUID id);

    User save(User user);
}
