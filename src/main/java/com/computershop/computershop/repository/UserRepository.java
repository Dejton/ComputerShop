package com.computershop.computershop.repository;

import com.computershop.computershop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
