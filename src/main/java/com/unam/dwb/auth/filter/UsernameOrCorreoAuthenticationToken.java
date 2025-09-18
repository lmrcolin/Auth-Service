package com.unam.dwb.auth.filter;

import java.util.Map;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UsernameOrCorreoAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 8008995706968351846L;
	private final Object principal;
    private final String credentials;
    
    public UsernameOrCorreoAuthenticationToken(Map<String, String> principal, String credentials) {
        super(null);
        this.principal = principal.get("username") != null ? principal.get("username") : principal.get("correo");
        this.credentials = credentials;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}
