package com.runtik.greenatom_test.token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TokenRepository {
    List<Token> findAllValidTokenByUser(UUID id);

    Optional<Token> findByToken(String token);

    void save(Token storedToken);

    void saveAll(List<Token> validUserTokens);
}
