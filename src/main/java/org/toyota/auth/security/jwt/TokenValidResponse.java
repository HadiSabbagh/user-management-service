package org.toyota.auth.security.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class TokenValidResponse
{
    private String username;
    private List<String> authorities;

    public TokenValidResponse()
    {
    }

    public TokenValidResponse(String username, List<String> authorities)
    {
        this.username = username;
        this.authorities = authorities;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public List<String> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(List<String> authorities)
    {
        this.authorities = authorities;
    }
}
