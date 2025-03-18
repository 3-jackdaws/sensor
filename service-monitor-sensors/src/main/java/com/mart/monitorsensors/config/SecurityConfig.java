package com.mart.monitorsensors.config;

import com.mart.monitorsensors.constant.RoleConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${security.users.admin.username}")
    private String adminUsername;

    @Value("${security.users.admin.password}")
    private String adminPassword;

    @Value("${security.users.viewer.username}")
    private String viewerUsername;

    @Value("${security.users.viewer.password}")
    private String viewerPassword;

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        var admin = User.withUsername(adminUsername)
                .password(passwordEncoder().encode(adminPassword))
                .roles(RoleConstants.ADMINISTRATOR)
                .build();

        var viewer = User.withUsername(viewerUsername)
                .password(passwordEncoder().encode(viewerPassword))
                .roles(RoleConstants.VIEWER)
                .build();

        return new InMemoryUserDetailsManager(admin, viewer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/sensors/**")
                        .hasAnyRole(RoleConstants.VIEWER, RoleConstants.ADMINISTRATOR)
                        .requestMatchers("/sensors/**").hasRole(RoleConstants.ADMINISTRATOR)
                )
                .httpBasic(httpBasic -> {
                });

        return http.build();
    }
}