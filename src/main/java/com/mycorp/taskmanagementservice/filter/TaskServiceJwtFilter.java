package com.mycorp.taskmanagementservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class TaskServiceJwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //grab the jwt token from the httprequest header, parse the token and grab the user roles
        var jwtTokenBeginIndex = 7;
        String jwtToken = request.getHeader("Authentication").substring(jwtTokenBeginIndex);
        Claims claims = Jwts.parser().setSigningKey("signing key").parseClaimsJwt(jwtToken).getBody();
        String username = claims.getSubject();
        List<String> roles = claims.get("roles", List.class);
        List<GrantedAuthority> authorities = null;
        for(String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //moving on to controller
        filterChain.doFilter(request, response);
    }
}
