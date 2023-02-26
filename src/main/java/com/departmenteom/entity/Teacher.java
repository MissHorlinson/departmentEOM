package com.departmenteom.entity;

import com.departmenteom.entity.dictionary.Department;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "teacher")
public class Teacher {

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

    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Column(name = "email")
    private String email;

    @Column(name = "passport")
    private String passport;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "department_id")
    private Department departmentInfo;

//    public Department getDepartment() {
//        departmentInfo.setTeacherSet(null);
//        return departmentInfo;
//    }

    @Override
    public String toString() {
        return "Teacher(id = " + id +
                ", firstName = " + firstName +
                ", secondName = " + secondName +
                ", lastName = " + lastName +
                ", birthday = " + birthday +
                ", email = " + email +
                ", passport = " + passport +
                ", phone = " + phone +
                ", departmentInfo = " + departmentInfo +
                '}';
    }
}
