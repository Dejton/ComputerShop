package com.computershop.computershop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain appSecurity(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/**")
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry.requestMatchers("/css/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/add-order").permitAll()
                        .requestMatchers("/emptyOrder").permitAll()
                        .requestMatchers("/add-to-cart").permitAll()
                        .requestMatchers("/order").permitAll()
                        .requestMatchers("/products/{category}").permitAll()
                        .requestMatchers("/realize-order").hasRole("USER")
                        .requestMatchers("/add-product").hasRole("ADMIN")
                        .requestMatchers("/add-category").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );
        return http.build();

    }
}
