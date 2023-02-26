package com.departmenteom.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "passport")
    private String passport;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "record_book")
    private String recordBook;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    private GroupInfo groupInfo;

    public GroupInfo getGroupInfo() {
        groupInfo.setStudentSet(null);
        return groupInfo;
    }

    @Override
    public String toString() {
        return "Student(id = " + id +
                ", firstName=" + firstName +
                ", secondName = " + secondName +
                ", lastName = " + lastName +
                ", birthday = " + birthday +
                ", email = " + email +
                ", passport = " + passport +
                ", phone = " + phone +
                ", recordBook = " + recordBook +
                ", groupInfo = " + groupInfo +
                ')';
    }
}
