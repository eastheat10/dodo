package com.nhnacademy.accountapi.repository;

import static com.nhnacademy.accountapi.entity.UserStatus.*;
import static org.assertj.core.api.Assertions.*;

import com.nhnacademy.accountapi.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자 등록")
    void testUserRegister() {

        User user = User.builder()
                        .username("username")
                        .password("password")
                        .email("emaill@e.mail")
                        .build();

        User save = userRepository.save(user);

        System.out.println(save.getStatus());

        assertThat(save).isEqualTo(user);
    }

    @Test
    @DisplayName("회원 조회")
    void testReadUser() {

        User user = User.builder()
                        .username("username")
                        .password("password")
                        .email("emaill@e.mail")
                        .build();

        User save = userRepository.save(user);

        User findUser = userRepository.findById(save.getId())
                                      .orElseThrow(IllegalArgumentException::new);

        assertThat(findUser).isEqualTo(save);
    }

    @Test
    @DisplayName("회원 삭제")
    void testDeleteUser() {

        User user = User.builder()
                        .username("username")
                        .password("password")
                        .email("emaill@e.mail")
                        .build();

        User save = userRepository.save(user);

        save.deleteUser();
        userRepository.flush();

        User findUser = userRepository.findById(save.getId())
                                      .orElseThrow(IllegalArgumentException::new);

        assertThat(findUser.getStatus()).isEqualTo(DELETED);
    }

    @Test
    @DisplayName("회원 휴면 처리")
    void testUserStatusDormant() {

        User user = User.builder()
                        .username("username")
                        .password("password")
                        .email("emaill@e.mail")
                        .build();

        User save = userRepository.save(user);

        save.makeDormant();
        userRepository.flush();

        User findUser = userRepository.findById(save.getId()).get();

        assertThat(findUser.getStatus()).isEqualTo(DORMANT);
    }

    @Test
    @DisplayName("Email로 회원 찾기")
    void testFindUserByEmail() {
        User user = User.builder()
                        .username("username")
                        .password("password")
                        .email("emaill@e.mail")
                        .build();

        User save = userRepository.save(user);

        User emailUser = userRepository.findByEmail(save.getEmail())
                                       .orElseThrow(IllegalArgumentException::new);

        assertThat(emailUser).isEqualTo(save);
    }

    @Test
    @DisplayName("로그인")
    void testUserLogin() {

        User user = User.builder()
                        .username("username")
                        .password("password")
                        .email("emaill@e.mail")
                        .build();

        User save = userRepository.save(user);

        User loginUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())
                                       .orElseThrow(IllegalArgumentException::new);

        assertThat(loginUser).isEqualTo(save);
    }

    @Test
    @DisplayName("아이디 중복")
    void testUsernameOverlap() {

        User user = User.builder()
                        .username("username")
                        .password("password")
                        .email("emaill@e.mail")
                        .build();

        userRepository.save(user);

        boolean result = userRepository.existsByUsername(user.getUsername());

        assertThat(result).isTrue();
    }
}