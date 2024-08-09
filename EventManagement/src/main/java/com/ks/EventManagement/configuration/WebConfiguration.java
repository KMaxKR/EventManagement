package com.ks.EventManagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.ks.EventManagement.entity.authority.Role.*;

@Configuration
public class WebConfiguration {

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
                .formLogin(f -> f
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    private final static String[] WHITE_LIST_AREA = {
            "/", "/user/save", "/user/login"
    };
    private final static String[] RESTRICTED_LIST_AREA = {
            "/info"
    };
    private final static String[] SPECIAL_LIST_AREA = {
            "/root", "/search/info/{username}"
    };
}
