package com.desafiopicpaybackend.repositories;

import com.desafiopicpaybackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
