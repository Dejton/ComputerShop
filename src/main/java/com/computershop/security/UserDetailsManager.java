package com.computershop.security;

import com.computershop.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

public class UserDetailsManager extends JdbcUserDetailsManager {
    private final UserRepository userRepository;

    public UserDetailsManager(DataSource dataSource, UserRepository userRepository) {
        super(dataSource);
        this.userRepository = userRepository;
    }

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        return userRepository.findByLogin(username)
                .map(entity -> {
                    var user = User.builder()
                            .username(entity.getLogin())
                            .password(entity.getPassword())
                            .disabled(false)
                            .accountExpired(false)
                            .credentialsExpired(false)
                            .accountLocked(false)
                            .authorities(NO_AUTHORITIES)
                            .build();

                    return List.of(user);
                })
                .orElseGet(Collections::emptyList);
    }

    @Override
    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        return userRepository.findByLogin(username)
                .map(entity -> List.<GrantedAuthority>of(new SimpleGrantedAuthority(entity.getRole())))
                .orElseGet(Collections::emptyList);
    }
}
