package com.animalplatform.platform.security.dto;

import com.animalplatform.platform.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthenticationUserDetails implements UserDetails {

    private static final long serialVersionUID = 6_334_552_294_281_662_805L;

    private SessionResponse session;

    @JsonIgnore
    private String userPw;

    public AuthenticationUserDetails(User user) {
        this.session = user.toSessionResponse();
        this.userPw = user.getUserPw();
    }

    public SessionResponse getSession() {
        return session;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of("ROLE_USER")
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.session.getUserId();
    }

    @Override
    public String getPassword() {
        return this.userPw;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
