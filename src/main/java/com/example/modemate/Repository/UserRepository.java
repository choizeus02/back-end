package com.example.modemate.Repository;

import com.example.modemate.domain.User;
import org.apache.kafka.common.security.auth.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);
}
