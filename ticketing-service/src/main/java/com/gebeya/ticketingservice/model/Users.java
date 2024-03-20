package com.gebeya.ticketingservice.model;

import com.gebeya.ticketingservice.enums.Authority;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Users  extends BaseModel implements UserDetails {
    private String userName;
    private String password;
    private Boolean is_active;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    private Integer roleId;


    public static class UsersBuilder {
        private boolean is_active;

        public UsersBuilder isActive(boolean isActive) {
            this.is_active = isActive;
            return this;
        }

        public Users build() {
            Users users = new Users();
            users.setIs_active(this.is_active);
            users.setUserName(this.userName);
            users.setPassword(this.password);
            users.setAuthority(this.authority);
            users.setRoleId(this.roleId);
            return users;
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.getIs_active();
    }
}
