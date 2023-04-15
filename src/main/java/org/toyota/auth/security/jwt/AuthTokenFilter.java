package org.toyota.auth.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AuthTokenFilter extends OncePerRequestFilter
{




    @Autowired
    WebClient webClient;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException
    {
        webClient = WebClient.builder().build();

        TokenValidResponse responseBody = webClient.get()
                                                  .uri("http://auth-service-1/api/auth/validateToken")
                                                  .header("Authorization", request.getHeader("Authorization"))
                                                  .retrieve().bodyToMono(TokenValidResponse.class).block();


        if (responseBody.getUsername() != null)
        {
            try
            {
                List<String> roles = responseBody.getAuthorities();
                List<GrantedAuthority> authorities = roles.stream()
                                                             .map(SimpleGrantedAuthority::new)
                                                             .collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(responseBody.getUsername(), null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e)
            {
                logger.error("Cannot set user authentication: {0}", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Cannot set user authentication");
            }
        }
        filterChain.doFilter(request, response);
    }

}
