package com.gebeya.ticketingservice.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class RoleHeaderAuthenticationToken extends AbstractAuthenticationToken {


    private final String headerRole;
    private final Integer roleId;

    public RoleHeaderAuthenticationToken(String headerRole, String roleId) {
        super(null);
        this.headerRole = headerRole;
        this.roleId= Integer.valueOf(roleId);
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return roleId;
    }

    @Override
    public Object getPrincipal() {
        return headerRole;
    }
}
