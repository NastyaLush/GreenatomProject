package com.runtik.greenatom_test.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static com.runtik.greenatom_test.user.Permission.MESSAGE_CREATOR;
import static com.runtik.greenatom_test.user.Permission.MESSAGE_DELETER;
import static com.runtik.greenatom_test.user.Permission.MESSAGE_UPDATER;
import static com.runtik.greenatom_test.user.Permission.MESSAGE_VIEWER;
import static com.runtik.greenatom_test.user.Permission.TOPIC_CREATOR;
import static com.runtik.greenatom_test.user.Permission.TOPIC_DELETER;
import static com.runtik.greenatom_test.user.Permission.TOPIC_UPDATER;
import static com.runtik.greenatom_test.user.Permission.TOPIC_VIEWER;


@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN(
            Set.of(
                    TOPIC_DELETER,
                    TOPIC_UPDATER,
                    TOPIC_VIEWER,
                    MESSAGE_VIEWER,
                    MESSAGE_DELETER,
                    MESSAGE_UPDATER
            )
    ),
    USER(
            Set.of(
                    TOPIC_VIEWER,
                    TOPIC_CREATOR,
                    MESSAGE_VIEWER,
                    MESSAGE_UPDATER,
                    MESSAGE_DELETER,
                    MESSAGE_CREATOR
            )
    );
    private final Set<Permission> permissions;

    private static final String PREFIX = "ROLE_";

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(PREFIX + this.name()));
        return authorities;
    }
}
