package com.nhnacademy.accountapi.repository;

import static com.nhnacademy.accountapi.entity.Status.*;
import static org.assertj.core.api.Assertions.*;

import com.nhnacademy.accountapi.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @DisplayName("사용자 등록")
    void testUserRegister() {

        Users users = Users.builder()
                           .username("username")
                           .password("password")
                           .email("emaill@e.mail")
                           .build();

        Users save = usersRepository.save(users);

        System.out.println(save.getStatus());

        assertThat(save).isEqualTo(users);
    }

    @Test
    @DisplayName("회원 조회")
    void testReadUser() {

        Users users = Users.builder()
                           .username("username")
                           .password("password")
                           .email("emaill@e.mail")
                           .build();

        Users save = usersRepository.save(users);

        Users findUser = usersRepository.findById(save.getId())
                                        .orElseThrow(IllegalArgumentException::new);

        assertThat(findUser).isEqualTo(save);
    }

    @Test
    @DisplayName("회원 삭제")
    void testDeleteUser() {

        Users users = Users.builder()
                           .username("username")
                           .password("password")
                           .email("emaill@e.mail")
                           .build();

        Users save = usersRepository.save(users);

        save.deleteUser();
        usersRepository.flush();

        Users findUser = usersRepository.findById(save.getId())
                                        .orElseThrow(IllegalArgumentException::new);

        assertThat(findUser.getStatus()).isEqualTo(DELETED);
    }

    @Test
    @DisplayName("회원 휴면 처리")
    void testUserStatusDormant() {

        Users users = Users.builder()
                           .username("username")
                           .password("password")
                           .email("emaill@e.mail")
                           .build();

        Users save = usersRepository.save(users);

        save.makeDormant();
        usersRepository.flush();

        Users findUser = usersRepository.findById(save.getId()).get();

        assertThat(findUser.getStatus()).isEqualTo(DORMANT);
    }

    @Test
    @DisplayName("Email로 회원 찾기")
    void testFindUserByEmail() {
        Users users = Users.builder()
                           .username("username")
                           .password("password")
                           .email("emaill@e.mail")
                           .build();

        Users save = usersRepository.save(users);

        Users emailUser = usersRepository.findByEmail(save.getEmail())
                                         .orElseThrow(IllegalArgumentException::new);

        assertThat(emailUser).isEqualTo(save);
    }

    @Test
    @DisplayName("로그인")
    void testUserLogin() {

        Users users = Users.builder()
                           .username("username")
                           .password("password")
                           .email("emaill@e.mail")
                           .build();

        Users save = usersRepository.save(users);

        Users loginUser = usersRepository.findByUsernameAndPassword(users.getUsername(), users.getPassword())
                                         .orElseThrow(IllegalArgumentException::new);

        assertThat(loginUser).isEqualTo(save);
    }

    @Test
    @DisplayName("아이디 중복")
    void testUsernameOverlap() {

        Users users = Users.builder()
                           .username("username")
                           .password("password")
                           .email("emaill@e.mail")
                           .build();

        usersRepository.save(users);

        boolean result = usersRepository.existsByUsername(users.getUsername());

        assertThat(result).isTrue();
    }
}