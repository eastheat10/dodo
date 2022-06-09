package com.nhnacademy.accountapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Users(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = Status.JOIN;
    }

    public void deleteUser() {
        this.status = Status.DELETED;
    }

    public void makeDormant() {
        this.status = Status.DORMANT;
    }
}
