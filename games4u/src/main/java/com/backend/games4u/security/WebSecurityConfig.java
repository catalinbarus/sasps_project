package com.backend.games4u.security;

import com.backend.games4u.security.jwt.AuthEntryPointJwt;
import com.backend.games4u.security.jwt.AuthTokenFilter;
import com.backend.games4u.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/users/**").permitAll()
                .requestMatchers("/users_paginated/**").permitAll()
                .requestMatchers("/publishers/**").permitAll()
                .requestMatchers("/publishers_paginated/**").permitAll()
                .requestMatchers("/games/**").permitAll()
                .requestMatchers("/games_light/**").permitAll()
                .requestMatchers("/genres/**").permitAll()
                .requestMatchers("/genres_paginated/**").permitAll()
                .requestMatchers("/developers/**").permitAll()
                .requestMatchers("/developers_paginated/**").permitAll()
                .requestMatchers("/upload/**").permitAll()
                .requestMatchers("/upload_boxart/**").permitAll()
                .requestMatchers("/files/**").permitAll()
                .requestMatchers("/upload_profile_picture/**").permitAll()
                .requestMatchers("/user_images/**").permitAll()
                .requestMatchers("/scores/**").permitAll()
                .requestMatchers("/reviews/**").permitAll()
                .requestMatchers("/game_list_statuses/**").permitAll()
                .requestMatchers("/game_list_entries/**").permitAll()
                .requestMatchers("/game_list_configs/**").permitAll()
                .requestMatchers("/trailers/**").permitAll()
                .requestMatchers("/requests/**").permitAll()
                .requestMatchers("/api/test/all").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .anyRequest().authenticated();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
