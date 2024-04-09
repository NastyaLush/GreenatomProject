package com.runtik.greenatom_test.token;

import com.runtik.greenatom_test.user.User;
import com.runtik.greenatom_test.user.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.runtik.greenatom_test.util.MessageContent.NO_USER_MESSAGE;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final UserRepository userRepository;
    private final Map<String, Token> tokenMap = new HashMap<>();

    @Override
    public List<Token> findAllValidTokenByUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new IllegalArgumentException(NO_USER_MESSAGE);
        return user.get().getTokens();
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return tokenMap.containsKey(token) ? Optional.of(tokenMap.get(token)) : Optional.empty();
    }

    @Override
    public void save(Token storedToken) {
        if (tokenMap.containsKey(storedToken.getTokenValue())) {
            throw new IllegalArgumentException();
        }
        tokenMap.put(storedToken.getTokenValue(), storedToken);
    }

    @Override
    public void saveAll(List<Token> validUserTokens) {
        validUserTokens.forEach(this::save);
    }
}
