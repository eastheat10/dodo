package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);
}
