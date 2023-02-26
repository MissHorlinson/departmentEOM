package com.departmenteom.entity;

import com.departmenteom.util.Role;
import com.departmenteom.util.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class UsersCred {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public UsersCred(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UsersCred() {}
}
