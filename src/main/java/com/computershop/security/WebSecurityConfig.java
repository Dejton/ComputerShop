package com.computershop.security;

import com.computershop.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain appSecurity(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/**")
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/",
                                "/login",
                                "/css/**",
                                "/img/**",
                                "/products/{category}",
                                "/register",
                                "/error",
                                "/api/users").permitAll()
                        .anyRequest().authenticated()
                ).logout(logout -> logout.logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource, UserRepository userRepository) {
        return new UserDetailsManager(dataSource, userRepository);
    }
}
