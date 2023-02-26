package com.departmenteom.security;

import com.departmenteom.entity.UsersCred;
import com.departmenteom.util.Status;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorityList;
    private final boolean isActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }


    public static UserDetails fromUser(UsersCred usersCred) {
        return new User(usersCred.getUsername(), usersCred.getPassword(),
                usersCred.getStatus().equals(Status.ACTIVE),
                usersCred.getStatus().equals(Status.ACTIVE),
                usersCred.getStatus().equals(Status.ACTIVE),
                usersCred.getStatus().equals(Status.ACTIVE),
                usersCred.getRole().getAuthorities());
    }
}

