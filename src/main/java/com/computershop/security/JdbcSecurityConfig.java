package com.computershop.security;

//import com.computershop.computershop.entity.User;
import com.computershop.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class JdbcSecurityConfig {
    private final UserRepository userRepository;

    public JdbcSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public InitializingBean initializingBean(DataSource dataSource) {
//        return () -> {
//            User admin = User.builder()
//                    .firstName("Admin")
//                    .lastName("Admin")
//                    .login("admin")
//                    .password(new BCryptPasswordEncoder().encode("password"))
//                    .email("admin@example.com")
//                    .address("Admin Address")
//                    .role("ADMIN,USER")
//                    .build();
//            userRepository.save(admin);
//
//            User user = User.builder()
//                    .firstName("User")
//                    .lastName("User")
//                    .login("user")
//                    .password(new BCryptPasswordEncoder().encode("password"))
//                    .email("user@example.com")
//                    .address("User Address")
//                    .role("USER")
//                    .build();
//            userRepository.save(user);
//        };
//    }
//        @Bean
//    public InitializingBean initializingBean(DataSource dataSource) {
//        return () -> {
//            UserDetails admin = User
//                    .withUsername("admin")
//                    .password(new BCryptPasswordEncoder().encode("password"))
//                            .roles("ADMIN")
//                                    .build();
//
//            userDetailsManager(dataSource).createUser(admin);
//
//            UserDetails user = User
//                    .withUsername("user")
//                    .password(new BCryptPasswordEncoder().encode("password"))
//                    .roles("USER")
//                    .build();
//
//            userDetailsManager(dataSource).createUser(user);
//        };
//    }

}
