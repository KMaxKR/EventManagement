package com.ks.EventManagement.configuration;

import com.ks.EventManagement.configuration.filter.JwtAuthFilter;
import com.ks.EventManagement.configuration.filter.JwtEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ks.EventManagement.entity.authority.Role.*;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebConfiguration {
    private final JwtEntryPoint jwtEntryPoint;
    private final JwtAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers(WHITE_LIST_AREA).permitAll()
                        .requestMatchers(RESTRICTED_LIST_AREA).authenticated()
                        .requestMatchers(SPECIAL_LIST_AREA).hasAnyRole(String.valueOf(ROOT) , String.valueOf(ANYKEY))
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtEntryPoint));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    private final static String[] WHITE_LIST_AREA = {
            "/", "/user/save", "/user/login",
            "/user/verification/{to}"
    };
    private final static String[] RESTRICTED_LIST_AREA = {
            "/info", "/status"
    };
    private final static String[] SPECIAL_LIST_AREA = {
            "/root", "/search/info/{username}"
    };
}
