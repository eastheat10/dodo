package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);
}
