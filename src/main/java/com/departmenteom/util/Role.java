package com.departmenteom.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN(Set.of(Permission.DEPARTMENT_WRITE, Permission.DEPARTMENT_READ), 1),  // 1-2
    MODERATOR(Set.of(Permission.DEPARTMENT_READ, Permission.DEPARTMENT_WRITE), 3), //3-4
    STUDENT(Set.of(Permission.DEPARTMENT_READ), 5), // 5
    TEACHER(Set.of(Permission.DEPARTMENT_READ), 6),  // 6
    USER(Set.of(Permission.DEPARTMENT_READ),7); // 7

    private final Set<Permission> permissions;
    private final int accessLevel;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority((permission.getPermission())))
                .collect(Collectors.toSet());
    }
}

