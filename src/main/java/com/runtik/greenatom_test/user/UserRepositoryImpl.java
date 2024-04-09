package com.runtik.greenatom_test.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

import static com.runtik.greenatom_test.util.MessageContent.USER_EXIST_MESSAGE;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<UUID, User> usersById = new HashMap<>();
    private final Map<String, User> userByUserName = new HashMap<>();

    @Override
    public Optional<User> findByUsername(String email) {
        return userByUserName.containsKey(email) ? Optional.of(userByUserName.get(email)) : Optional.empty();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return usersById.containsKey(id) ? Optional.of(usersById.get(id)) : Optional.empty();
    }

    @Override
    public User save(User user) {
        if (userByUserName.containsKey(user.getUsername())) {
            throw new IllegalArgumentException(USER_EXIST_MESSAGE);
        }
        usersById.put(user.getId(), user);
        userByUserName.put(user.getUsername(), user);
        return user;
    }
}
