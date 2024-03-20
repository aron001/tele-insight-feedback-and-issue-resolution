package com.gebeya.authenticationservice.model;

import com.gebeya.authenticationservice.enums.Authority;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Users extends BaseModel implements UserDetails {
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    private Integer roleId;
    private Boolean is_active;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authority.name()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.is_active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.is_active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.is_active;
    }

    @Override
    public boolean isEnabled() {
        return this.is_active;
    }
}
