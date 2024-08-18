package com.ks.EventManagement.configuration.filter;

import com.ks.EventManagement.service.UserService;
import com.ks.EventManagement.utility.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JWT jwt;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie el : cookies) {
                if (Objects.equals(el.getName(), "AUTHORIZATION")) {
                    response.addCookie(new Cookie("AUTHORIZATION", el.getValue()));
                    bearerToken = URLDecoder.decode(el.getValue(), StandardCharsets.UTF_8);
                    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                        bearerToken = bearerToken.substring(7);
                    }
                }
            }
        }
        //todo isValid to work properly
        if (StringUtils.hasText(bearerToken) && jwt.isValid(bearerToken)) {
            String username = jwt.getUsernameFromToken(bearerToken);

            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
